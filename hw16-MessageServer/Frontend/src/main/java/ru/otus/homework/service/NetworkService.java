package ru.otus.homework.service;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Connection;
import ru.otus.homework.model.MessageToDB;
import ru.otus.homework.model.User;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.TimeoutException;

@Service
public class NetworkService {


    private Gson gson = new Gson();
    @Value("${source.port}")
    private int sourcePort;
    @Value("${source.type}")
    private Long sourceType;
    @Value("${response.break.count}")
    private int responseBreakCount;
    @Value("${message.system.port}")
    private int messageSystemPort;
    @Value("${response.break.seconds}")
    private long responseBreakSeconds;
    @Value("${destination.port}")
    private int destinationPort;
    @Value("${destination.type}")
    private Long destinationType;
    private Connection connection;

    private static void sleep(Long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String receiveMessage() throws IOException {
        String message = connection.read();
        System.out.println("front " + sourcePort + " received: " + message);
        return message;
    }

    public String sendAndReceiveMessage(User value) throws IOException {
        String message = gson.toJson(
                new MessageToDB(
                        sourcePort,
                        sourceType,
                        destinationPort,
                        destinationType,
                        value
                )
        );
        connection.write(message);
        System.out.println("front: " + sourcePort + " send:" + message);
        return receiveMessage();
    }

    public void initiateDataExchange() throws IOException {
        this.connection = new Connection(
                new Socket("localhost", sourcePort)
        );
    }

    private String request(SocketChannel socketChannel, String request) throws IOException, TimeoutException {
        send(socketChannel, request);
        return receive(socketChannel);
    }

    private void send(SocketChannel socketChannel, String request) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1000);
        buffer.put(request.getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        System.out.println("front" + sourcePort + " send: " + request);
    }

    public String receive(SocketChannel socketChannel) throws TimeoutException, IOException {
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        for (int i = 0; i < responseBreakCount; i++) {
            try {
                if (selector.select() > 0) {
                    String response = processServerResponse(selector);
                    if (response != null) {
                        return response;
                    }
                    sleep(responseBreakSeconds);
                }
            } catch (Exception e) {
                e.printStackTrace();
                sleep(responseBreakSeconds);
            }
        }
        throw new TimeoutException();
    }

    private String processServerResponse(Selector selector) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1000);
        Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
        while (selectedKeys.hasNext()) {
            SelectionKey key = selectedKeys.next();
            if (key.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                int count = socketChannel.read(buffer);
                if (count > 0) {
                    buffer.flip();
                    String response = StandardCharsets.UTF_8.decode(buffer).toString();
                    System.out.println("front" + sourcePort + " received: " + response);
                    buffer.clear();
                    buffer.flip();
                    return response;
                }
            }
            selectedKeys.remove();
        }
        return null;
    }

    public void connectToMessageSystem() throws IOException, TimeoutException {
        String response = request(
                createInitialSocket(),
                sourceType + ":" + sourcePort
        );
        while (!response.equals("200")) {
            response = request(
                    createInitialSocket(),
                    sourceType + ":" + sourcePort
            );
        }
        System.out.println("front: " + sourcePort + " in a band!");
    }


    private SocketChannel createInitialSocket() throws IOException {
        return createSocketChanel(messageSystemPort);
    }

    private SocketChannel createSocketChanel(int port) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", port));
        for (int i = 0; i < responseBreakCount; i++) {
            try {
                while (!socketChannel.finishConnect()) {
                    System.out.println("connection established");
                    sleep(responseBreakSeconds);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                sleep(responseBreakSeconds);
            }
        }
        return socketChannel;
    }
}
