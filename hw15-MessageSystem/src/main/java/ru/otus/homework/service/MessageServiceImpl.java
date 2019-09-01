package ru.otus.homework.service;


import org.springframework.stereotype.Service;
import ru.otus.homework.model.MyMessage;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

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
