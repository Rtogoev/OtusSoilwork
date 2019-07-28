package ru.otus.homework.web.mapping;

import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.model.User;
import ru.otus.homework.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AdminPanel extends HttpServlet {

    private UserService userService;

    public AdminPanel(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String resultAsString = createTable(userService.getAll());
        resultAsString = resultAsString + "\n";
        resultAsString = resultAsString + "\n";
        resultAsString = resultAsString + createButton("http://localhost:8080/users/create","Create user");
        resultAsString = resultAsString + createButton("http://localhost:8080/","Update list");
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resultAsString);
        printWriter.flush();
    }

    private String createButton(String link, String name) {
        return "<input value=\"" + name + "\" " +
                "type=\"button\" " +
                "onclick=\"location.href='" + link + "'\" />";
    }

    private String createTable(List<User> usersAll) {
        String result = "<p> Users:<p> \n" +
                "<table>" +
                "<tr>" +
                "<th>Id</th>" +
                "<th>Name</th>" +
                "<th>Age</th>" +
                "<th>Address</th>" +
                "<th>Phones</th>" +
                "</tr>";
        for (User user : usersAll) {
            String numbers = "";
            for (PhoneDataSet phoneDataSet : user.getPhoneDataSet()) {
                numbers = phoneDataSet.getNumber() + " ";
            }
            result = result + "<tr>" +
                    "<td>" + user.getId() + "</td>" +
                    "<td>" + user.getName() + "</td>" +
                    "<td>" + user.getAge() + "</td>" +
                    "<td>" + user.getAddressDataSet().getStreet() + "</td>" +
                    "<td>" + numbers + "</td></tr>";
        }
        result = result + "</table >";
        return result;
    }
}
