package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class BackendRunnerService {

    private static final String BACKEND_RUN_COMMAND = "java -jar hw16-MessageServer/Backend/target/Backend-1.0-SNAPSHOT.jar";
    private final RunnerService runnerService;
    @Value("${backendInstanseCount}")
    private int backendInstanseCount;

    public BackendRunnerService(RunnerService runnerService) {
        this.runnerService = runnerService;
    }

    @PostConstruct
    void init() throws IOException, InterruptedException {
        runnerService.run(BACKEND_RUN_COMMAND, backendInstanseCount);
    }
}