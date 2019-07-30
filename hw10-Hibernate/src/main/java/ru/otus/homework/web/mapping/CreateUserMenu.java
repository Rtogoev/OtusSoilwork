package ru.otus.homework.web.mapping;

import ru.otus.homework.Server;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CreateUserMenu extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String resultAsString = " <html>\n" +
                "<head></head>" +
                "<body>" +
                "<form method=\"POST\" " +
                "action=\"http://"+ Server.address +":" + Server.PORT +"/users/add\">" +
                "Name: <input type=\"text\" name=\"name\"><br>" +
                "Age: <input type=\"text\" name=\"age\"><br>" +
                "Street: <input type=\"text\" name=\"street\"><br>" +
                "Number: <input type=\"text\" name=\"number\"><br>" +
                "\n<input value=\"Create\" type=\"submit\" /></form>\n" +
                "<input value=\"Show all\" type=\"button\" onclick=\"location.href='http://"+ Server.address +":" + Server.PORT +"/'\" />" +
                "</body>" +
                "</html>";
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resultAsString);
        printWriter.flush();
    }
}
