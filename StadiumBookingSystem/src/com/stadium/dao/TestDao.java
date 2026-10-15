package com.stadium.dao;

import com.stadium.dao.CustomerDAO;
public class TestDao {
    public static void main(String[] args){
        CustomerDAO dao = new CustomerDAO();
        dao.findCustomerById(2);
        dao.updateCustomerEmail(5, "newemail@example.com");
        dao.getAllCustomers();




    }
}
