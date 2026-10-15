package com.stadium.model;

public class Customer {
    private int customerId;
    private String name;
    private String address;
    private String email;
    private String contact;
    private String country;
    private String username;
    private String password;

    public Customer(int customerId, String name, String address, String email,
                    String contact, String country, String username, String password) {

        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.contact = contact;
        this.country = country;
        this.username = username;
        this.password = password;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getCountry() {
        return country;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
