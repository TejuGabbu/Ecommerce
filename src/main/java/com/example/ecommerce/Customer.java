package com.example.ecommerce;
// this classs we made for displaying the user account details when it login from their account
public class Customer
{
    private int id;
    private String name , email , mobile;

    public Customer(int id, String name, String email, String mobile)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }
}
