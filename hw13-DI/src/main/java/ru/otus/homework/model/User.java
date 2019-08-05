package ru.otus.homework.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;
    @Column(insertable = false, updatable = false)
    private String address;
    @Column(insertable = false, updatable = false)
    private String phone;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "address")
    private AddressDataSet addressDataSet;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "phone_number")
    private Set<PhoneDataSet> phoneDataSet;

    public User() {
    }

    public User(String name, int age, AddressDataSet addressDataSet, Set<PhoneDataSet> phoneDataSet) {
        this.name = name;
        this.age = age;
        this.addressDataSet = addressDataSet;
        this.phoneDataSet = phoneDataSet;
        this.address = addressDataSet.getStreet();
        setPhone(phoneDataSet);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private void setPhone(Set<PhoneDataSet> phoneDataSet) {
        StringBuilder stringBuilder = new StringBuilder();
        for (PhoneDataSet phone : phoneDataSet) {
            stringBuilder.append(phone.getNumber())
                    .append(" ");
        }
        this.phone = stringBuilder.toString();
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
        this.address = addressDataSet.getStreet();
    }

    public Set<PhoneDataSet> getPhoneDataSet() {
        return phoneDataSet;
    }

    public void setPhoneDataSet(Set<PhoneDataSet> phoneDataSet) {
        this.phoneDataSet = phoneDataSet;
        setPhone(phoneDataSet);
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
}
