package ru.otus.homework.service;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.MyMessage;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class MessageServiceImpl implements MessageService {

    private final NetworkService networkService;
    private Map<Long, LinkedBlockingQueue<MyMessage>> queuesMap = new ConcurrentHashMap<>();
    private Map<Long, Set<ServerSocketChannel>> addressMap = new ConcurrentHashMap<>();
    private Gson gson = new Gson();
    @Value("${message.system.port}")
    private int messageSystemPort;
    @Value("${response.break.count}")
    private int responseBreakCount;
    @Value("${response.break.seconds}")
    private long responseBreakSeconds;

    public MessageServiceImpl(NetworkService networkService) {
        this.networkService = networkService;
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
        startDataFlow();
    }

    private void startDataFlow() {
        new Thread(
                () -> {
                    try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
                        serverSocketChannel.configureBlocking(false);

                        ServerSocket serverSocket = serverSocketChannel.socket();
                        serverSocket.bind(new InetSocketAddress(messageSystemPort));
                        while (true) {
                            try {
                                networkService.receiveAndAnswer(
                                        serverSocketChannel,
                                        (response) -> {
                                            if (response != null) {
                                                String[] split = response.split(":");
                                                ServerSocketChannel createdServerSocketChannel = networkService
                                                        .createServerSocketChannel(
                                                                Integer.parseInt(split[1])
                                                        );
                                                addAddress(
                                                        Long.parseLong(split[0]),
                                                        createdServerSocketChannel
                                                );
                                                startMessaging(createdServerSocketChannel);
                                                return "200";
                                            }
                                            return "400";
                                        }
                                );
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }

    private void startMessaging(ServerSocketChannel socketForMessaging) {
        new Thread(
                () -> {
                    while (true) {
                        try {
                            String receive = networkService.receive(socketForMessaging);
                            if (receive != null) {
                                if (receive.length() != 0) {
                                    MyMessage message = gson.fromJson(receive, MyMessage.class);
                                    addMessageToQueue(message.getDestinationType(), message);
                                }
                            }
                            sleep(responseBreakSeconds);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }

    @Override
    public void addMessageToQueue(Long type, MyMessage message) {
        if (!queuesMap.containsKey(type)) {
            LinkedBlockingQueue<MyMessage> queue = new LinkedBlockingQueue<>();
            queue.add(message);
            queuesMap.put(type, queue);
            new Thread(
                    () -> {
                        try {
                            // todo если порт указан, то слать на этот порт
                            networkService.send(
                                    getAddress(type),
                                    gson.toJson(
                                            queuesMap.get(type)
                                    )
                            );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            ).start();
            return;
        }
        queuesMap.get(type).add(message);
    }

    @Override
    public ServerSocketChannel getAddress(long type) {
        return getRandomElement(addressMap.get(type));
    }

    @Override
    public void addAddress(long type, ServerSocketChannel socketChannel) {
        if (!addressMap.containsKey(type)) {
            Set<ServerSocketChannel> addressSet = new HashSet<>();
            addressSet.add(socketChannel);
            addressMap.put(type, addressSet);
            return;
        }
        addressMap.get(type).add(socketChannel);
    }

    private ServerSocketChannel getRandomElement(Set<ServerSocketChannel> addressSet) {
        if (addressSet == null) {
            return null;
        }
        if (addressSet.size() == 0) {
            return null;
        }

        int resultIndex = generateIndex(addressSet.size());

        int currentIndex = 0;
        ServerSocketChannel resultServerSocketChannel = null;
        for (ServerSocketChannel serverSocketChannel : addressSet) {
            if (resultIndex == currentIndex) {
                resultServerSocketChannel = serverSocketChannel;
                break;
            }
            currentIndex++;
        }
        return resultServerSocketChannel;
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
