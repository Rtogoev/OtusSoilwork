package ru.otus.homework.service;


import org.springframework.stereotype.Service;
import ru.otus.homework.model.MyMessage;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class MessageServiceImpl implements MessageService {

    private Map<UUID, LinkedBlockingQueue<MyMessage>> queuesMap = new ConcurrentHashMap<>();
    private Map<UUID, MessageProcessor> processorMap = new ConcurrentHashMap<>();
    private Set<UUID> dbAddressSet = new HashSet<>();
    private Set<UUID> frontAddressSet = new HashSet<>();

    @Override
    public void addMessageToQueue(UUID queueOwnerAddress, MyMessage message) {
        if (!queuesMap.containsKey(queueOwnerAddress)) {
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
                        for (Map.Entry<UUID, LinkedBlockingQueue<MyMessage>> entry : queuesMap.entrySet()) {
                            MessageProcessor processor = processorMap.get(entry.getKey());
                            LinkedBlockingQueue<MyMessage> queue = entry.getValue();
                            new Thread(
                                    () -> {
                                        try {
                                            MyMessage myMessage = queue.poll();
                                            while (myMessage != null) {
                                                processor.process(myMessage);
                                                myMessage = queue.poll();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                            ).start();
                        }
                    }
                }
        ).start();
    }

    @Override
    public void addMessageProcessor(UUID address, MessageProcessor processor) {
        processorMap.put(address, processor);
    }

    @Override
    public UUID getDbAddress() {
        return getRandomElement(dbAddressSet);
    }

    private UUID getRandomElement(Set<UUID> uuidSet) {
        if (uuidSet.size() == 0) {
            return null;
        }

        int resultIndex = generateIndex(uuidSet.size());

        int currentIndex = 0;
        UUID resultDbAddress = null;
        for (UUID dbAddress : uuidSet) {
            if (resultIndex == currentIndex) {
                resultDbAddress = dbAddress;
                break;
            }
            currentIndex++;
        }
        return resultDbAddress;
    }

    private int generateIndex(int size) {
        if (size == 0) {
            return -1;
        }
        if (size == 1) {
            return 0;
        }
        int max = dbAddressSet.size() - 1;
        return (int) (max * Math.random());
    }

    @Override
    public void addDbAddress(UUID dbAddress) {
        dbAddressSet.add(dbAddress);
    }

    @Override
    public UUID getFrontAddress() {
        return getRandomElement(frontAddressSet);
    }

    @Override
    public void addFrontAddress(UUID frontAddress) {
        frontAddressSet.add(frontAddress);
    }
}
