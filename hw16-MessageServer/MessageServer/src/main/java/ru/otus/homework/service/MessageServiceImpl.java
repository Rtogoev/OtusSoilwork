package ru.otus.homework.service;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.MyMessage;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class MessageServiceImpl implements MessageService {

    private Map<Long, LinkedBlockingQueue<MyMessage>> queuesMap = new HashMap<>();
    private Map<Long, MessageProcessor> processorMap = new HashMap<>();
    private List<String> dbAddressList = new ArrayList<>();
    private List<String> frontAddressList = new ArrayList<>();
    private Gson gson = new Gson();

    @Value("${message.system.port}")
    private String messageSystemPort;

    @Override
    public void addMessageToQueue(long queueOwnerAddress, MyMessage message) {
        if (queuesMap.get(queueOwnerAddress) == null) {
            LinkedBlockingQueue<MyMessage> queue = new LinkedBlockingQueue<>();
            queue.add(message);
            queuesMap.put(queueOwnerAddress, queue);
            return;
        }
        queuesMap.get(queueOwnerAddress).add(message);
    }

    private void go() throws IOException {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.configureBlocking(false);

            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocket.bind(new InetSocketAddress(Integer.parseInt(messageSystemPort)));

            try (Selector selector = Selector.open()) {
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

                while (!Thread.currentThread().isInterrupted()) {
                    if (selector.select() > 0) { //This method performs a blocking
                        performIO(selector);
                    }
                }
            }
        }
    }

    private void performIO(Selector selector) throws IOException {
        Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

        while (keys.hasNext()) {
            SelectionKey key = keys.next();
            if (key.isAcceptable()) {
                acceptConnection(key, selector);
            } else if (key.isReadable()) {
                readWriteClient(key);
            }

            keys.remove();
        }
    }

    private void acceptConnection(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept(); //The socket channel for the new connection

        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void readWriteClient(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

        ByteBuffer buffer = ByteBuffer.allocate(5);
        StringBuilder inputBuffer = new StringBuilder(100);

        while (socketChannel.read(buffer) > 0) {
            buffer.flip();
            String input = StandardCharsets.UTF_8.decode(buffer).toString();

            buffer.flip();
            buffer.clear();
            inputBuffer.append(input);
        }

        String requestFromClient = inputBuffer.toString();

        System.out.println("Server received: " + requestFromClient);
        String request = processClientRequest(requestFromClient);
        if (request != null) {
            System.out.println(request);
            return;
        }
        System.out.println(gson.fromJson(requestFromClient, MyMessage.class));
//        for (byte b : response) {
//            buffer.put(b);
//            if (buffer.position() == buffer.limit()) {
//                buffer.flip();
//                socketChannel.write(buffer);
//                buffer.flip();
//                buffer.clear();
//            }
//        }
//        if (buffer.hasRemaining()) {
//            buffer.flip();
//            socketChannel.write(buffer);
//        }
    }

    private String processClientRequest(String input) {
        String[] split = input.split("=");
        if (split.length == 2) {
            if (split[1].equals("?")) {
                if (split[0].equals("front")) {
                    return getFrontAddress();
                }
                if (split[0].equals("back")) {
                    return getDbAddress();
                }
            }

            if (split[0].equals("front")) {
                frontAddressList.add(split[1]);
                return "ok";
            }
            if (split[0].equals("back")) {
                dbAddressList.add(split[1]);
                return "ok";
            }
        }
        return null;
    }

    @PostConstruct
    void init() throws IOException {
        new Thread(
                () -> {
                    while (true) {
                        for (Map.Entry<Long, LinkedBlockingQueue<MyMessage>> entry : queuesMap.entrySet()) {
                            MessageProcessor processor = processorMap.get(entry.getKey());
                            LinkedBlockingQueue<MyMessage> queue = entry.getValue();
                            MyMessage myMessage = queue.poll();
                            while (myMessage != null) {
                                processor.process(myMessage);
                                myMessage = queue.poll();
                            }
                        }
                    }
                }
        ).start();
        go();
    }

    @Override
    public void addMessageProcessor(long address, MessageProcessor processor) {
        processorMap.put(address, processor);
    }

    @Override
    public String getDbAddress() {
        return dbAddressList.get(getRandomIndex(dbAddressList.size()));
    }


    private int getRandomIndex(int size) {
        if (size < 2) {
            return 0;
        }
        return (int) (Math.random() * size - 1);
    }

    @Override
    public String getFrontAddress() {
        return frontAddressList.get(getRandomIndex(frontAddressList.size()));
    }
}
