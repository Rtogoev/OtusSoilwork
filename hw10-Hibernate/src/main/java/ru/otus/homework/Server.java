package ru.otus.homework;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.otus.homework.cache.CacheImpl;
import ru.otus.homework.model.AddressDataSet;
import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.model.User;
import ru.otus.homework.service.UserService;
import ru.otus.homework.web.filter.SimpleFilter;
import ru.otus.homework.web.mapping.RootLink;
import ru.otus.homework.web.mapping.UsersAddMapping;
import ru.otus.homework.web.mapping.UsersAddRandomMapping;
import ru.otus.homework.web.mapping.UsersGetAllMapping;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;

public class Server {
    private static final int PORT = 8080;
    private UserService userService;
    private org.eclipse.jetty.server.Server server;

    public static void main(String[] args) throws Exception {
        new Server().start();
    }

    public void start() throws Exception {
        userService = new UserService(createSessionFactory(), new CacheImpl<Long, User>());
        this.server = createServer(PORT);
        server.start();
        server.join();
    }

    public void stop() throws Exception {
        server.stop();
    }

    private SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml");
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(AddressDataSet.class)
                .addAnnotatedClass(PhoneDataSet.class)
                .addAnnotatedClass(User.class)
                .getMetadataBuilder()
                .build();

        return metadata.getSessionFactoryBuilder().build();
    }

    public org.eclipse.jetty.server.Server createServer(int port) throws IOException {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new UsersGetAllMapping(userService)), "/users/get/all");
        context.addServlet(new ServletHolder(new UsersAddMapping(userService)), "/users/add");
        context.addServlet(new ServletHolder(new UsersAddRandomMapping(userService)), "/users/add/random");
        context.addServlet(new ServletHolder(new RootLink()), "/*");

        context.addFilter(new FilterHolder(new SimpleFilter()), "/*", null);

        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(port);
        server.setHandler(new HandlerList(context));

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{createSecurityHandler(context)});
        server.setHandler(handlers);
        return server;
    }

    private SecurityHandler createSecurityHandler(ServletContextHandler context) throws IOException {
        Constraint constraint = new Constraint();
        constraint.setName("auth");
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[]{"admin"});

        ConstraintMapping mapping = new ConstraintMapping();
        mapping.setPathSpec("/*");
        mapping.setConstraint(constraint);

        ConstraintSecurityHandler security = new ConstraintSecurityHandler();
        //как декодировать стороку с юзером:паролем https://www.base64decode.org/
        security.setAuthenticator(new BasicAuthenticator());
        URL propFile = null;
        File realmFile = new File("hw10-Hibernate/src/main/resources/realm.properties");
        if (realmFile.exists()) {
            propFile = realmFile.toURI().toURL();
        }
        if (propFile == null) {
            throw new RuntimeException("Realm property file not found");
        }

        security.setLoginService(new HashLoginService("MyRealm", propFile.getPath()));
        security.setHandler(new HandlerList(context));
        security.setConstraintMappings(Collections.singletonList(mapping));

        return security;
    }
}
