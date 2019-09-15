package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class FrontendRunnerService {
    private static final String FRONTEND_RUN_COMMAND = "java -jar hw16-MessageServer/Frontend/target/Frontend-1.0-SNAPSHOT.jar ";
    private final RunnerService runnerService;
    @Value("${frontend.instance.count}")
    private int frontendInstanceCount;

    public FrontendRunnerService(RunnerService runnerService) {
        this.runnerService = runnerService;
    }

    @PostConstruct
    void init() throws IOException, InterruptedException {
        runnerService.run(FRONTEND_RUN_COMMAND, frontendInstanceCount);
    }
}
