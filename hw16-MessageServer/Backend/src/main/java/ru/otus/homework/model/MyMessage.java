package ru.otus.homework.model;

public interface MyMessage<U> {
    U getValue();

    void setValue(U value);


    String getSourcePort();

    void setSourcePort(String sourcePort);

    String getSourceType();

    void setSourceType(String sourceType);

    String getDestinationPort();

    void setDestinationPort(String destinationPort);

    String getDestinationType();

    void setDestinationType(String destinationType);
}
