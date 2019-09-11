package ru.otus.homework.service;


import org.springframework.stereotype.Service;
import ru.otus.homework.model.MyMessage;

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
    private Map<Long, Set<UUID>> addressMap = new ConcurrentHashMap<>();

    @Override
    public void addMessageToQueue(UUID queueOwnerAddress, MyMessage message) {
        if (!queuesMap.containsKey(queueOwnerAddress)) {
            LinkedBlockingQueue<MyMessage> queue = new LinkedBlockingQueue<>();
            queue.add(message);
            queuesMap.put(queueOwnerAddress, queue);
            new Thread(
                    () -> {
                        try {
                            System.out.println("Создан поток");
                            while (true) {
                                processorMap.get(queueOwnerAddress).process(queue.take());
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
    public void addMessageProcessor(UUID address, MessageProcessor processor) {
        processorMap.put(address, processor);
    }

    @Override
    public UUID getAddress(long type) {
        return getRandomElement(addressMap.get(type));
    }

    @Override
    public void addAddress(long type, UUID address) {
        if (!addressMap.containsKey(type)) {
            Set<UUID> addressSet = new HashSet<>();
            addressSet.add(address);
            addressMap.put(type, addressSet);
            return;
        }
        addressMap.get(type).add(address);
    }

    private UUID getRandomElement(Set<UUID> uuidSet) {
        if (uuidSet == null) {
            return null;
        }
        if (uuidSet.size() == 0) {
            return null;
        }

        int resultIndex = generateIndex(uuidSet.size());

        int currentIndex = 0;
        UUID resultAddress = null;
        for (UUID address : uuidSet) {
            if (resultIndex == currentIndex) {
                resultAddress = address;
                break;
            }
            currentIndex++;
        }
        return resultAddress;
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
