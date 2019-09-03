package ru.otus.homework.service;


import org.springframework.stereotype.Service;
import ru.otus.homework.model.MyMessage;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
public class MessageServiceImpl implements MessageService {

    private Map<Long, LinkedBlockingQueue<MyMessage>> queuesMap = new HashMap<>();
    private Map<Long, MessageProcessor> processorMap = new HashMap<>();
    private long dbAddress;
    private long frontAddress;

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


    private void go(String request) {
        try {
            try(SocketChannel socketChannel = SocketChannel.open()) {
                socketChannel.configureBlocking(false);

                ;
                socketChannel.connect(new InetSocketAddress("localhost", 8081));
                while (!socketChannel.finishConnect()) {
                    System.out.println("connection established");
                }
                send(socketChannel, request);
                sleep();
                send(socketChannel, "stop\n");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void send(SocketChannel socketChannel, String request) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1000);
        buffer.put(request.getBytes());
        buffer.flip();
        socketChannel.write(buffer);

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        while (true) {
            if (selector.select() > 0) { //This method performs a blocking
                if (processServerResponse(selector)) {
                    return;
                }
            }
        }
    }

    private boolean processServerResponse(Selector selector) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1000);
        Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
        while (selectedKeys.hasNext()) {
            SelectionKey key = selectedKeys.next();
            if (key.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                int count = socketChannel.read(buffer);
                if (count > 0) {
                    buffer.flip();
                    String response = Charset.forName("UTF-8").decode(buffer).toString();
                    System.out.println("back received: " + response);
                    buffer.clear();
                    buffer.flip();
                    return true;
                }
            }
            selectedKeys.remove();
        }
        return false;
    }


    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(10));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @PostConstruct
    void init() {
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
        go("Backend here");
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
