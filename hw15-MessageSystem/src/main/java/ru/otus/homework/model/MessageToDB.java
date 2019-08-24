package ru.otus.homework.model;

public class MessageToDB implements MyMessage {

    private User user;

    public MessageToDB(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
