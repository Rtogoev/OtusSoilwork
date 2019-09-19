package ru.otus.homework.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class Connection {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()
                )
        );
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public String read() throws IOException {
        String line = in.readLine();
        while (line == null) {
            line = in.readLine();
        }
        return line;
    }

    public void write(String message) throws IOException {
        out.println(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return Objects.equals(socket, that.socket) &&
                Objects.equals(out, that.out) &&
                Objects.equals(in, that.in);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket, out, in);
    }
}
