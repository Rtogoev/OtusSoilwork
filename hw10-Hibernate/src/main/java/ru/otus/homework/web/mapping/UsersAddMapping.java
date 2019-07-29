package ru.otus.homework.web.mapping;

import ru.otus.homework.model.AddressDataSet;
import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.model.User;
import ru.otus.homework.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class UsersAddMapping extends HttpServlet {
    private UserService userService;

    public UsersAddMapping(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;Windows-1251");
        userService.create(
                createUserFromRequest(request)
        );
        new AdminPanel(userService).doGet(request, response);
    }

    private User createUserFromRequest(HttpServletRequest request) {
        return new User(
                request.getParameter("name"),
                Integer.parseInt(request.getParameter("age")),
                new AddressDataSet(
                        request.getParameter("street")
                ),
                Collections.singleton(
                        new PhoneDataSet(
                                request.getParameter("number")
                        )
                )
        );
    }
}
