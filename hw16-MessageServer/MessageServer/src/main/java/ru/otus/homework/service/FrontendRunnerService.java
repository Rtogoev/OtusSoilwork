package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class FrontendRunnerService {
    private static final String FRONTEND_RUN_COMMAND = "";
    private final RunnerService runnerService;
    @Value("${frontendInstanseCount}")
    private int frontendInstanseCount;

    public FrontendRunnerService(RunnerService runnerService) {
        this.runnerService = runnerService;
    }

    @PostConstruct
    void init() throws IOException, InterruptedException {
        runnerService.run(FRONTEND_RUN_COMMAND, frontendInstanseCount);
    }
}
