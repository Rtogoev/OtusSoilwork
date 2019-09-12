package ru.otus.homework.model;

public class MyMessage {
    private String sourcePort;
    private String sourceType;
    private String destinationPort;
    private String destinationType;
    private String value;
    public MyMessage(String sourcePort, String destinationPort, String value) {
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.value = value;
    }

    @Override
    public String toString() {
        return "MyMessage{" +
                "sourcePort='" + sourcePort + '\'' +
                ", destinationPort='" + destinationPort + '\'' +
                ", value='" + value + '\'' +
                '}';
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

    public String getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
    }
}