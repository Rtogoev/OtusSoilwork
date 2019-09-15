package ru.otus.homework.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

@Service
public class ServerSocketChannelService {
    public void receive(ServerSocketChannel serverSocketChannel, ResponseProcessor processor) throws IOException, InterruptedException {

        try (Selector selector = Selector.open()) {

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (!Thread.currentThread().isInterrupted()) {
                if (selector.select() > 0) {
                    Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                    System.out.println("server ready to receive");
                    while (keys.hasNext()) {
                        System.out.println("server attention to receive");
                        SelectionKey key = keys.next();
                        if (key.isAcceptable()) {
                            acceptConnection(key, selector);
                        } else if (key.isReadable()) {
                            System.out.println("server gonna to receive");
                            readWriteClient(key, processor);
                        }
                        keys.remove();
                    }
                }
            }
        }
    }

    public void onlySend(ServerSocketChannel serverSocketChannel, String message) throws IOException {
        System.out.println("onlySend");
        try (Selector selector = Selector.open()) {
            System.out.println("onlySend. selector open");
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
//            while (!Thread.currentThread().isInterrupted()) {
            if (selector.select() > 0) {
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                System.out.println("server ready to onlySend");
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    if (key.isAcceptable()) {
                        acceptConnection(key, selector);
                    } else if (key.isReadable()) {
                        System.out.println("server gonna to onlySend");
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(5);
                        for (byte b : message.getBytes()) {
                            buffer.put(b);
                            if (buffer.position() == buffer.limit()) {
                                buffer.flip();
                                socketChannel.write(buffer);
                                buffer.flip();
                                buffer.clear();
                            }
                        }
                        if (buffer.hasRemaining()) {
                            buffer.flip();
                            System.out.println("сервер отправил: " + message);
                            socketChannel.write(buffer);
                        }
                        socketChannel.close();
                    }
                    keys.remove();
                }
            }
//            }
        }
    }

    public ServerSocketChannel createServerSocketChannel(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));

        return serverSocketChannel;
    }

    public void acceptConnection(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept(); //The socket channel for the new connection

        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void readWriteClient(SelectionKey selectionKey, ResponseProcessor processor) throws IOException, InterruptedException {
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
        String response = processor.process(requestFromClient);
        for (byte b : response.getBytes()) {
            buffer.put(b);
            if (buffer.position() == buffer.limit()) {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.flip();
                buffer.clear();
            }
        }
        if (buffer.hasRemaining()) {
            buffer.flip();
            System.out.println("сервер отправил: " + response);
            socketChannel.write(buffer);
        }
        socketChannel.close();
    }

}
