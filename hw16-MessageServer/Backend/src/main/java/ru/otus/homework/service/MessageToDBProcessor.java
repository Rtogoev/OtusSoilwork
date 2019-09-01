package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.model.*;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

@Service
public class MessageToDBProcessor implements MessageProcessor {
    private static Long address;
    private final MessageService messageService;
    private DbService<User, Long> userService;

    public MessageToDBProcessor(MessageService messageService, DbService<User, Long> userService) {
        this.messageService = messageService;
        this.userService = userService;
        address = IdGenerator.generate();
    }

    @PostConstruct
    void init() {
        messageService.addMessageProcessor(address, this);
        messageService.setDbAddress(address);
    }

    @Override
    public void process(MyMessage message) {
        new Thread(
                () -> {
                    MessageToDB messageToDB = (MessageToDB) message;
                    User user = messageToDB.getValue();
                    try {
                        userService.create(user);
                    } catch (SQLException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    StringBuilder phone = new StringBuilder();
                    for (PhoneDataSet phoneDataSet : user.getPhoneDataSet()) {
                        phone.append(phoneDataSet.getNumber());
                    }
                    messageService.addMessageToQueue(
                            messageService.getFrontAddress(),
                            new MessageFromDB(
                                    new UserForm(
                                            user.getId().toString(),
                                            user.getName(),
                                            String.valueOf(user.getAge()),
                                            user.getAddressDataSet().getStreet(),
                                            phone.toString()
                                    )
                            )
                    );
                }
        ).start();
    }
}

