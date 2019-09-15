package ru.otus.homework.service;

import java.io.IOException;

public interface ResponseProcessor {
    String process(String response) throws IOException, InterruptedException;
}
