package ru.otus.homework.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RunnerService {

    private void runInstance(String command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        Process process = processBuilder.start();
        while(process.isAlive()){
            Thread.sleep(1000);
        }
    }

    public void run(String command,int instanceCount) throws IOException, InterruptedException {
        for (int i=0;i<instanceCount;i++){
            runInstance(command);
        }
    }
}
