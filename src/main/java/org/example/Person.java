package org.example;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private int id;
    private String name;
    private String email;
    private List<Address> addressList;

    public Person() {
        addressList=new ArrayList<>();
    }

    public Person(int id, String name, String email, List<Address> addressList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.addressList = addressList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", addressList=" + addressList +
                '}';
    }
}
