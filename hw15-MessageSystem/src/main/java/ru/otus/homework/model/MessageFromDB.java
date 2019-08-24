package ru.otus.homework.model;

public class MessageFromDB implements MyMessage {
    private UserForm userForm;

    public MessageFromDB(UserForm userForm) {
        this.userForm = userForm;
    }

    public UserForm getUserForm() {
        return userForm;
    }

    public void setUserForm(UserForm userForm) {
        this.userForm = userForm;
    }
}
