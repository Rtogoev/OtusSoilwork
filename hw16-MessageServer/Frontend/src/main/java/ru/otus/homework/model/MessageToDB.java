package ru.otus.homework.model;

public class MessageToDB implements MyMessage<User> {
    private int sourcePort;
    private Long sourceType;
    private int destinationPort;
    private Long destinationType;
    private User value;

    public MessageToDB(int sourcePort, Long sourceType, int destinationPort, Long destinationType, User value) {
        this.sourcePort = sourcePort;
        this.sourceType = sourceType;
        this.destinationPort = destinationPort;
        this.destinationType = destinationType;
        this.value = value;
    }

    @Override
    public String toString() {
        return "MessageToDB{" +
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

    public Long getSourceType() {
        return sourceType;
    }

    public void setSourceType(Long sourceType) {
        this.sourceType = sourceType;
    }

    public int getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(int destinationPort) {
        this.destinationPort = destinationPort;
    }

    public Long getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(Long destinationType) {
        this.destinationType = destinationType;
    }

    @Override
    public User getValue() {
        return value;
    }

    @Override
    public void setValue(User value) {
        this.value = value;
    }
}
