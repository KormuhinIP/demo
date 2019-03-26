package com.example.dao;

import com.example.model.Customer;

import java.util.List;

public interface CustomerDao {
    public List<Customer> findAll();

    public void save(Customer customer);

    public void delete(Customer customer);

    public void update(Customer customer);
}
