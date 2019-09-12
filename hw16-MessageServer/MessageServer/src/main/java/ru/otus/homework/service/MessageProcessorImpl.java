package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.model.MyMessage;

import java.nio.channels.SocketChannel;

@Service
public class MessageProcessorImpl implements MessageProcessor {
    private String port;


    @Override
    public void process(SocketChannel socketChannel, MyMessage myMessage) {
// todo отправлять сообщение по указанному порту
    }
}
