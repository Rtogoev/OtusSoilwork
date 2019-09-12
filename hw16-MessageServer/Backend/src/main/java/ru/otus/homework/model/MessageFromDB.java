package ru.otus.homework.model;

public class MessageFromDB implements MyMessage<UserForm> {
    private String sourcePort;
    private String sourceType;
    private String destinationPort;
    private String destinationType;
    private UserForm value;

    public MessageFromDB(String sourcePort, String sourceType, String destinationPort, String destinationType, UserForm value) {
        this.sourcePort = sourcePort;
        this.sourceType = sourceType;
        this.destinationPort = destinationPort;
        this.destinationType = destinationType;
        this.value = value;
    }

    public String getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
    }

    public String getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }

    @Override
    public UserForm getValue() {
        return value;
    }

    @Override
    public void setValue(UserForm value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MessageFromDB{" +
                "sourcePort='" + sourcePort + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", destinationPort='" + destinationPort + '\'' +
                ", destinationType='" + destinationType + '\'' +
                ", value=" + value +
                '}';
    }
}
