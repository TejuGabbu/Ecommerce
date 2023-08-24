package com.example.ecommerce;

import java.sql.ResultSet;

public class Login
{
    // this function is for to check the username and password is match with the database or not
    public Customer customerLogin(String username , String password)
    {
        String query = "select * from customer where email = '"+ username +"' and password = '"+password+"' ";
        DbConnection connection = new DbConnection();
        try
        {
            ResultSet rs = connection.getQueryTable(query); //  we get result in this rs
            if(rs.next())
                return new Customer(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("mobile"));

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args)
    {
        Login lg = new Login();
        Customer customer = lg.customerLogin("tskulkarni123@gmail.com" , "tejas@123");
        System.out.println("Welcome : " + customer.getName());
    }
}
