package ru.otus.homework.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class RunnerService {

    private void runInstance(String command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        new Thread(
                () -> {
                    try {
                        while (process.isAlive()) {
                            Thread.sleep(1000);
                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String line = reader.readLine();
                            while ((line != null) || process.isAlive() || line.length() == 0 || line.equals("")) {
                                System.out.println(line);

                                line = reader.readLine();
                            }
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }

    public void run(String command, int instanceCount) throws IOException, InterruptedException {
        for (int i = 0; i < instanceCount; i++) {
            runInstance(command);
        }
    }
}
