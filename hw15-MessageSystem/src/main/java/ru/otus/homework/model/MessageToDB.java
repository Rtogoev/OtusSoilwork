package ru.otus.homework.model;

public class MessageToDB implements MyMessage<User> {

    private User value;

    public MessageToDB(User user) {
        this.value = user;
    }

    @Override
    public void setValue(User value) {
        this.value = value;
    }

    @Override
    public User getValue() {
        return value;
    }
}
