package ru.otus.homework.model;

public class MyMessage {
    private String destinationPort;
    private String value;

    public MyMessage(String destinationPort, String value) {
        this.destinationPort = destinationPort;
        this.value = value;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}