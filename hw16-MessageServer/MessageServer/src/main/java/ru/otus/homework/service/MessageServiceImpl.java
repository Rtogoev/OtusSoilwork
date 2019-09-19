package ru.otus.homework.service;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Connection;
import ru.otus.homework.model.MyMessage;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
    private Map<Long, Set<Connection>> addressMap = new ConcurrentHashMap<>();
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
            e.printStackTrace();
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
                                                startAcceptConnections(
                                                        Integer.parseInt(split[0]),
                                                        Integer.parseInt(split[1])
                                                );
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

    private void startAcceptConnections(int type, int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        new Thread(
                () -> {
                    while (true) {
                        try {
                            Socket clientSocket = serverSocket.accept();
                            Connection connection = new Connection(clientSocket);
                            addAddress(type, connection);
                            startMessaging(connection);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }

    private void startMessaging(Connection connection) {
        new Thread(
                () -> {
                    while (true) {
                        try {
                            String receive = networkService.receive(connection);
                            MyMessage message = gson.fromJson(receive, MyMessage.class);
                            addMessageToQueue(message.getDestinationType(), message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        sleep(responseBreakSeconds);
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
                                            queuesMap.get(type).take()
                                    )
                            );
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            ).start();
            return;
        }
        queuesMap.get(type).add(message);
    }

    @Override
    public Connection getAddress(long type) {
        return getRandomElement(addressMap.get(type));
    }

    @Override
    public void addAddress(long type, Connection connection) {
        if (!addressMap.containsKey(type)) {
            Set<Connection> connectionSet = new HashSet<>();
            connectionSet.add(connection);
            addressMap.put(type, connectionSet);
            return;
        }
        addressMap.get(type).add(connection);
    }

    private Connection getRandomElement(Set<Connection> connectionSet) {
        if (connectionSet == null) {
            return null;
        }
        if (connectionSet.size() == 0) {
            return null;
        }

        int resultIndex = generateIndex(connectionSet.size());

        int currentIndex = 0;
        Connection resultConnection = null;
        for (Connection connection : connectionSet) {
            if (resultIndex == currentIndex) {
                resultConnection = connection;
                break;
            }
            currentIndex++;
        }
        return resultConnection;
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
