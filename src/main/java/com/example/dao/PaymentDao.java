package com.example.dao;

import com.example.model.Payment;

import java.util.List;

public interface PaymentDao {
    public List<Payment> findAll();

    public Payment findById(long id);

    public void save(Payment payment);

    public void delete(Payment payment);

    public void update(Payment payment);

    public List<Payment> findPaidStudents(long studentId);

    public double paymentOfMonth(int month);
}
