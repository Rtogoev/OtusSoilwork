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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;


// запустились, фронты и бэки на разных портах, значит каждому свой сокет, вот
// тогда должен быть управляютй порт, который знают все участники сети. Вот через него они и добавляют пару тип:порт.
// в сообщении указан тип назначения и порт отправки, следовательно при обработке сообщения, создается ответное и
// указывается тип получаетля, а затем из очереди по данному типу выбирается конкретный клиент.
// Если указан порт назначения, то отправлять по порту!
// На управляюй порт сообщения только приходят. Тогда, как только сообщение пришло, вместе с очередью создается и сокет

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageProcessor processor;
    private Map<Long, LinkedBlockingQueue<MyMessage>> queuesMap = new ConcurrentHashMap<>();
    private Map<Long, SocketChannel> longSocketChannelMap = new ConcurrentHashMap<>();
    private Map<Long, Set<Long>> addressMap = new ConcurrentHashMap<>();
    private Gson gson = new Gson();
    @Value("${message.system.port}")
    private String messageSystemPort;

    @Value("${response.break.count}")
    private int responseBreakCount;
    @Value("${response.break.seconds}")
    private long responseBreakSeconds;

    public MessageServiceImpl(MessageProcessor processor) {
        this.processor = processor;
    }

    private static void sleep(Long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @PostConstruct
    void init() {
        new Thread(
                () -> {
                    while (true) {
                        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
                            serverSocketChannel.configureBlocking(false);

                            ServerSocket serverSocket = serverSocketChannel.socket();
                            serverSocket.bind(new InetSocketAddress(Integer.parseInt(messageSystemPort)));

                            try (Selector selector = Selector.open()) {
                                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

                                while (!Thread.currentThread().isInterrupted()) {
                                    if (selector.select() > 0) { //This method performs a blocking
                                        String response = performIO(selector);
                                        if (response != null) {
                                            String[] split = response.split(":");
                                            addSocketChannel(Long.valueOf(split[0]), createSocketChanel(split[1]));
                                        }
                                    }

                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            sleep(responseBreakSeconds);
                        }
                    }
                }
        ).start();
    }

    private SocketChannel createSocketChanel(String s) {
        return null;
    }

    @Override
    public void addMessageToQueue(Long queueOwnerAddress, MyMessage message) {
        if (!queuesMap.containsKey(queueOwnerAddress)) {
            LinkedBlockingQueue<MyMessage> queue = new LinkedBlockingQueue<>();
            queue.add(message);
            queuesMap.put(queueOwnerAddress, queue);
            new Thread(
                    () -> {
                        try {
                            System.out.println("Создан поток");
                            while (true) {
                                processor.process(
                                        longSocketChannelMap.get(queueOwnerAddress),
                                        queue.take()
                                );
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            ).start();
            return;
        }
        queuesMap.get(queueOwnerAddress).add(message);
    }

    @Override
    public void addSocketChannel(Long address, SocketChannel socketChannel) {
        longSocketChannelMap.put(
                address,
                socketChannel
        );

    }

    @Override
    public Long getAddress(long type) {
        return getRandomElement(addressMap.get(type));
    }

    @Override
    public void addAddress(long type, Long address) {
        if (!addressMap.containsKey(type)) {
            Set<Long> addressSet = new HashSet<>();
            addressSet.add(address);
            addressMap.put(type, addressSet);
            return;
        }
        addressMap.get(type).add(address);
    }

    private Long getRandomElement(Set<Long> LongSet) {
        if (LongSet == null) {
            return null;
        }
        if (LongSet.size() == 0) {
            return null;
        }

        int resultIndex = generateIndex(LongSet.size());

        int currentIndex = 0;
        Long resultAddress = null;
        for (Long address : LongSet) {
            if (resultIndex == currentIndex) {
                resultAddress = address;
                break;
            }
            currentIndex++;
        }
        return resultAddress;
    }

    private String performIO(Selector selector) throws IOException {
        Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

        while (keys.hasNext()) {
            SelectionKey key = keys.next();
            if (key.isAcceptable()) {
                acceptConnection(key, selector);
            } else if (key.isReadable()) {
                return readWriteClient(key);
            }

            keys.remove();
        }
        return null;
    }

    private void acceptConnection(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept(); //The socket channel for the new connection

        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private String readWriteClient(SelectionKey selectionKey) throws IOException {
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
        return requestFromClient;
    }

    private int generateIndex(int size) {
        if (size == 0) {
            return -1;
        }
        if (size == 1) {
            return 0;
        }
        int max = size - 1;
        return (int) (max * Math.random());
    }
}
