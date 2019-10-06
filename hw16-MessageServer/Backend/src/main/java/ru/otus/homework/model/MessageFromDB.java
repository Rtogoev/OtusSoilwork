package ru.otus.homework.model;

public class MessageFromDB implements MyMessage<UserForm> {
    private int sourcePort;
    private int sourceType;
    private int destinationPort;
    private int destinationType;
    private UserForm value;

    public MessageFromDB(int sourcePort, int sourceType, int destinationPort, int destinationType, UserForm value) {
        this.sourcePort = sourcePort;
        this.sourceType = sourceType;
        this.destinationPort = destinationPort;
        this.destinationType = destinationType;
        this.value = value;
    }

    @Override
    public String toString() {
        return "MessageFromDB{" +
                "sourcePort=" + sourcePort +
                ", sourceType=" + sourceType +
                ", destinationPort=" + destinationPort +
                ", destinationType=" + destinationType +
                ", value=" + value +
                '}';
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(int sourcePort) {
        this.sourcePort = sourcePort;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public int getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(int destinationPort) {
        this.destinationPort = destinationPort;
    }

    public int getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(int destinationType) {
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
}
