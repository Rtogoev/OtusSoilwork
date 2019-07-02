package ru.otus.homework.model;

import java.util.Objects;

public class User {
    private Long id;
    private String name;
    private int age;
    private AddressDataSet addressDataSet;
    private PhoneDataSet phoneDataSet;

    public User(Long id, String name, int age, AddressDataSet addressDataSet, PhoneDataSet phoneDataSet) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.addressDataSet = addressDataSet;
        this.phoneDataSet = phoneDataSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
                Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(addressDataSet, user.addressDataSet) &&
                Objects.equals(phoneDataSet, user.phoneDataSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, addressDataSet, phoneDataSet);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", addressDataSet=" + addressDataSet +
                ", phoneDataSet=" + phoneDataSet +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressDataSet getAddressDataSet() {
        return addressDataSet;
    }

    public void setAddressDataSet(AddressDataSet addressDataSet) {
        this.addressDataSet = addressDataSet;
    }

    public PhoneDataSet getPhoneDataSet() {
        return phoneDataSet;
    }

    public void setPhoneDataSet(PhoneDataSet phoneDataSet) {
        this.phoneDataSet = phoneDataSet;
    }
}
