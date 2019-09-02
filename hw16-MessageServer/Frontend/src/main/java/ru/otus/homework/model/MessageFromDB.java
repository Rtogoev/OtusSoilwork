package ru.otus.homework.model;

public class MessageFromDB implements MyMessage<UserForm> {
    private UserForm value;

    public MessageFromDB(UserForm value) {
        this.value = value;
    }

    @Override
    public void setValue(UserForm value) {
        this.value = value;
    }

    @Override
    public UserForm getValue() {
        return value;
    }
}
