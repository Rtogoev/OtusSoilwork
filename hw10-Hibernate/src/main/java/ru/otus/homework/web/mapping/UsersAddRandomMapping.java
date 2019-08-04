package ru.otus.homework.web.mapping;

import ru.otus.homework.model.AddressDataSet;
import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.model.User;
import ru.otus.homework.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;

public class UsersAddRandomMapping extends HttpServlet {
    private final String templatesPath;
    private final String address;
    private final int port;
    private UserService userService;

    public UsersAddRandomMapping(UserService userService, String templatesPath, String address, int port) {
        this.userService = userService;
        this.templatesPath = templatesPath;
        this.address = address;
        this.port = port;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        userService.create(
                new User(
                        "name" + LocalDateTime.now().toString(),
                        (int) (Math.random() * 10000),
                        new AddressDataSet("street" + LocalDateTime.now().toString()),
                        Collections.singleton(
                                new PhoneDataSet(
                                        "number" + LocalDateTime.now().toString()
                                )
                        )
                )
        );
        new AdminPanel(userService, templatesPath, address, port).doGet(request, response);
    }
}
