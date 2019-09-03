package ru.otus.homework.service;


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
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class MessageServiceImpl implements MessageService {

    private Map<Long, LinkedBlockingQueue<MyMessage>> queuesMap = new HashMap<>();
    private Map<Long, MessageProcessor> processorMap = new HashMap<>();
    private long dbAddress;
    private long frontAddress;

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
        try(ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
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
        System.out.println("readWriteClient");
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

        ByteBuffer buffer = ByteBuffer.allocate(5);
        StringBuilder inputBuffer = new StringBuilder(100);

        while (socketChannel.read(buffer) > 0) {
            buffer.flip();
            String input = Charset.forName("UTF-8").decode(buffer).toString();

            buffer.flip();
            buffer.clear();
            inputBuffer.append(input);
        }

        String requestFromClient = inputBuffer.toString();

        System.out.println("Server received: " + requestFromClient);
        byte[] response = processClientRequest(requestFromClient).getBytes();
        for (byte b : response) {
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
            socketChannel.write(buffer);
        }

        if ("stop\n".equals(requestFromClient)) {
            socketChannel.close();
        }
    }

    private String processClientRequest(String input) {
        return "echo:" + input;
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
    public long getDbAddress() {
        return dbAddress;
    }

    @Override
    public void setDbAddress(long dbAddress) {
        this.dbAddress = dbAddress;
    }

    @Override
    public long getFrontAddress() {
        return frontAddress;
    }

    @Override
    public void setFrontAddress(long frontAddress) {
        this.frontAddress = frontAddress;
    }
}
