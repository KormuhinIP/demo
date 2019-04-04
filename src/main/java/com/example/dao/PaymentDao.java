package com.example.dao;

import com.example.model.Payment;
import com.example.model.Teacher;

import java.util.List;

public interface PaymentDao {
    public List<Payment> findAll();

    public Payment findById(long id);

    public void save(Teacher teacher);

    public void delete(Teacher teacher);

    public void update(Teacher teacher);

    public List<Payment> findByName(String name);
}
