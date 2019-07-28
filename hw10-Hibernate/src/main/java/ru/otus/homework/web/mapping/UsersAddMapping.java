package ru.otus.homework.web.mapping;

import ru.otus.homework.model.AddressDataSet;
import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.model.User;
import ru.otus.homework.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class UsersAddMapping extends HttpServlet {
    private UserService userService;

    public UsersAddMapping(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(
                "<html>\n" +
                        "<head></head>" +
                        "<body>" +
                        "user created with id: " +
                        userService.create(
                                parseFromPath(
                                        getStringBodyFromRequest(request)
                                )
                        ) +
                        "<br><input value=\"Show all\" type=\"button\" onclick=\"location.href='http://localhost:8080/'\" />" +
                        "</body>" +
                        "</html>"
        );
        printWriter.flush();
    }

    private User parseFromPath(String stringPath) {
        String name = "";
        int age = 0;
        AddressDataSet address = new AddressDataSet("");
        Set<PhoneDataSet> phonesSet = new HashSet<>();

        String[] keyValuePairs = stringPath.split("&");
        for (String keyValuePair : keyValuePairs) {

            String[] keyValueSplitted = keyValuePair.split("=");
            if (keyValueSplitted[0].contains("name")) {
                name = keyValueSplitted[1];
            }
            if (keyValueSplitted[0].contains("age")) {
                age = Integer.parseInt(keyValueSplitted[1]);
            }
            if (keyValueSplitted[0].contains("street")) {
                address = new AddressDataSet(keyValueSplitted[1]);
            }
            if (keyValueSplitted[0].contains("number")) {
                phonesSet.add(
                        new PhoneDataSet(keyValueSplitted[1])
                );
            }
        }
        return new User(name, age, address, phonesSet);
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
