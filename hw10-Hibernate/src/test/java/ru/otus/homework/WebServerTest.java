package ru.otus.homework;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebServerTest {
    private final static int PORT = 8081;
    private static Server server;

    @AfterAll
    static void stopServer() throws Exception {
        server.stop();
    }

    @BeforeEach
    void startServer() throws Exception {
        Application appl = new Application();
        server = appl.createServer(PORT);
        server.start();
    }

    private URL makeUrl(String part) throws MalformedURLException {
        return new URL("http://localhost:" + PORT + part);
    }

    @Test
    @DisplayName("test /data/testParam/")
    void doGet() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) makeUrl("/data/testParam").openConnection();
        connection.setRequestMethod("GET");
        assertEquals(HttpStatus.OK_200, connection.getResponseCode(), "doGet works");

        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        assertEquals("from server:testParam", new Gson().fromJson(stringBuilder.toString(), String.class));
    }
}
