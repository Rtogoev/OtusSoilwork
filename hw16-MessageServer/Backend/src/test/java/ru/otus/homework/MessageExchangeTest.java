package ru.otus.homework;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.homework.model.*;
import ru.otus.homework.service.NetworkService;
import ru.otus.homework.service.UserService;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class MessageExchangeTest {

    private static final int MESSAGES_AMOUNT = 10;
    private static final int DESTINATION_TYPE = 4;
    private static final int DESTINATION_PORT = 8088;
    private Connection connectionDB;
    @Value("${message.system.port}")
    private int messageSystemPort;
    private Gson gson;
    private MessageToDB messageToDB;
    private User user;

    @Autowired
    private UserService userService;
    @Autowired
    private NetworkService networkService;

    @Before
    public void setUp() throws IOException {
        gson = new Gson();
        user = new User(
                "name",
                123,
                new AddressDataSet("street"),
                Collections.singleton(
                        new PhoneDataSet("number")
                )
        );
        messageToDB = new MessageToDB(
                networkService.getSourcePort(),
                2,
                DESTINATION_PORT,
                DESTINATION_TYPE,
                user
        );
        ServerSocket socket = new ServerSocket(messageSystemPort);
        Connection connectionMS = new Connection(socket.accept());

        String read = connectionMS.read();
        System.out.println("server received " + read);
        connectionMS.write("200");
        System.out.println("server send 200");


        ServerSocket socketDB = new ServerSocket(networkService.getSourcePort());
        connectionDB = new Connection(socketDB.accept());
        System.out.println("test connected to DB");
    }

    @Test
    public void messageExchange() throws IOException {

        for (int i = 0; i < MESSAGES_AMOUNT; i++) {
            System.out.println("test send: " + messageToDB.toString());
            connectionDB.write(
                    gson.toJson(messageToDB)
            );
            System.out.println("test receive...");
            MessageFromDB messageFromDB = gson.fromJson(
                    connectionDB.read(),
                    MessageFromDB.class
            );
            System.out.println("test received!");
            UserForm userForm = messageFromDB.getValue();
            check(user, userForm);
            check(
                    userService.load(userForm.getId()),
                    messageFromDB.getValue()
            );
        }
    }

    private void check(User user, UserForm userForm) {
        Assert.assertEquals(user.getName(), userForm.getName());
        Assert.assertEquals(user.getAge(), userForm.getAge());
        Assert.assertEquals(user.getAddressDataSet().getStreet(), userForm.getAddress());
        StringBuilder phone = new StringBuilder();
        for (PhoneDataSet phoneDataSet : user.getPhoneDataSet()) {
            phone.append(phoneDataSet.getNumber());
        }
        Assert.assertEquals(phone.toString(), userForm.getPhone());
    }
}
