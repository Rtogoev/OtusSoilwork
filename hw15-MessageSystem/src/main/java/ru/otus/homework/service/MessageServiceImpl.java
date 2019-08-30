package ru.otus.homework.service;


import org.springframework.stereotype.Service;
import ru.otus.homework.model.MyMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class MessageServiceImpl implements MessageService {

    Map<Long, LinkedBlockingQueue<MyMessage>> queuesMap = new HashMap<>();

    @Override
    public void addMessageToQueue(long queueOwnerId, MyMessage message) {
        if (queuesMap.get(queueOwnerId) == null) {
            LinkedBlockingQueue<MyMessage> queue = new LinkedBlockingQueue<>();
            queue.add(message);
            queuesMap.put(queueOwnerId, queue);
            return;
        }
        queuesMap.get(queueOwnerId).add(message);
    }

    @Override
    public MyMessage getMessageFromQueue(long queueOwnerId) {
        LinkedBlockingQueue<MyMessage> queue = queuesMap.get(queueOwnerId);
        if (queue == null) {
            return null;
        }
        return queue.poll();
    }
}
