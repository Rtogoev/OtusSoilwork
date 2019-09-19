package ru.otus.homework.service;


import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.MessageFromDB;
import ru.otus.homework.model.User;

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
    public void addMessageToQueue(User value) throws IOException {
        processor.process(
                gson.fromJson(
                        networkService.sendAndReceiveMessage(value),
                        MessageFromDB.class
                )
        );
    }

    @Override
    public void setProcessor(MessageProcessor processor) {
        this.processor = processor;
    }

    @PostConstruct
    void init() {
        try {
            networkService.connectToMessageSystem();
            networkService.initiateDataExchange();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
