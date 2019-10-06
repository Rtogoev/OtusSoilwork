package ru.otus.homework.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Connection;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

@Service
public class NetworkService {

    public static volatile boolean isMessageExchangeStarted;
    @Value("${source.port}")
    private int sourcePort;
    @Value("${source.type}")
    private Long sourceType;
    @Value("${message.system.port}")
    private int messageSystemPort;
    private Connection connection;

    public int getSourcePort() {
        return sourcePort;
    }

    public String receiveMessage() throws IOException {
        String message = connection.read();
        System.out.println("back " + sourcePort + " received: " + message);
        return message;
    }

    public String sendAndReceiveMessage(String message) throws IOException {
        send(message);
        return receiveMessage();
    }

    public void send(String message) throws IOException {
        connection.write(message);
        System.out.println("back: " + sourcePort + " send:" + message);
    }

    public void initiateDataExchange() throws IOException {
        this.connection = new Connection(
                new Socket("localhost", sourcePort)
        );
    }

    public void connectToMessageSystemAsync() throws IOException, TimeoutException {
        new Thread(
                () -> {
                    Connection connectionMS;
                    String response = "";
                    while (!response.equals("200")) {
                        try {
                            Thread.sleep(1000);
                            connectionMS = new Connection(
                                    new Socket("localhost", messageSystemPort)
                            );
                            String message = sourceType + ":" + sourcePort;
                            connectionMS.write(message);
                            System.out.println("db send " + message);
                            response = connectionMS.read();
                            System.out.println("db received " + response);
                        } catch (InterruptedException | IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    System.out.println("back " + sourcePort + " in a band!");
                    isMessageExchangeStarted = true;
                }
        ).start();
    }
}
