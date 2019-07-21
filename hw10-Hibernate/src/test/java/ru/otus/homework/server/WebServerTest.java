package ru.otus.homework.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.Server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class WebServerTest {
    private Server server;

    @BeforeEach
    void setUp() throws Exception {
        server.start();
    }

    private URL makeUrl(String part) throws MalformedURLException {
        return new URL("http://localhost:8080" + part);
    }

    @Test
    @DisplayName("test POST /users/add/")
    void UsersAdd() throws IOException {
    }

//    @Test
//    @DisplayName("test /data/testParam/")
//    void doGet() throws IOException {
//        HttpURLConnection connection = (HttpURLConnection) makeUrl("/data/testParam").openConnection();
//        connection.setRequestMethod("GET");
//        assertEquals(HttpStatus.OK_200, connection.getResponseCode(), "doGet works");
//
//        StringBuilder stringBuilder = new StringBuilder();
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//        }
//        assertEquals("from server:testParam", new Gson().fromJson(stringBuilder.toString(), String.class));
//    }

    @AfterEach
    void tearDown() throws Exception {
        server.stop();
    }
}