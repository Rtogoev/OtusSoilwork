package ru.otus.homework.web.mapping;

import com.google.gson.Gson;
import ru.otus.homework.model.User;
import ru.otus.homework.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class UsersAddMapping extends HttpServlet {
    private UserService userService;
    private Gson gson;

    public UsersAddMapping(UserService userService) {
        this.userService = userService;
        this.gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(
                userService.create(
                        gson.fromJson(
                                getStringBodyFromRequest(request),
                                User.class
                        )
                )
        );
        printWriter.flush();
    }

    private String getStringBodyFromRequest(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            while (reader.ready()) {
                stringBuilder.append(reader.readLine());
            }
        }
        return stringBuilder.toString();
    }
}
