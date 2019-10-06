package ru.otus.homework.service;


import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.MessageToDB;
import ru.otus.homework.model.MyMessage;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class MessageServiceImpl implements MessageService {

    private final NetworkService networkService;
    private MessageProcessor processor;
    private Gson gson = new Gson();

    public MessageServiceImpl(NetworkService networkService) {
        this.networkService = networkService;
    }

    @Override
    public void addMessageToQueue(MyMessage message) throws IOException {
        networkService.send(gson.toJson(message));
    }

    @Override
    public void setProcessor(MessageProcessor processor) {
        this.processor = processor;
    }

    @PostConstruct
    void init() {
        try {
            networkService.connectToMessageSystemAsync();
            new Thread(
                    () -> {
                        try {
                            while (!NetworkService.isMessageExchangeStarted) {
                                System.out.println("wait for connection");
                                SleepService.sleep(1L);
                            }
                            networkService.initiateDataExchange();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        while (true) {
                            try {
                                processor.process(
                                        gson.fromJson(
                                                networkService.receiveMessage(),
                                                MessageToDB.class
                                        )
                                );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ).start();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
