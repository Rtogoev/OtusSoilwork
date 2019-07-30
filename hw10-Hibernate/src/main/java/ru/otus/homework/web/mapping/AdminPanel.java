package ru.otus.homework.web.mapping;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import ru.otus.homework.Server;
import ru.otus.homework.service.UserService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class AdminPanel extends HttpServlet {

    private UserService userService;
    private Configuration cfg;

    public AdminPanel(UserService userService) throws IOException {
        this.userService = userService;
        cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setDirectoryForTemplateLoading(new File("hw10-Hibernate/src/main/resources/templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> root = new HashMap<>();
        root.put("address", Server.address);
        root.put("users", userService.getAll());

        Template temp = cfg.getTemplate("AdminPanel.ftl");
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter printWriter = response.getWriter();
        try {
            temp.process(root, response.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        printWriter.flush();
    }
}
