package com.example.model;

import java.util.Date;

public class Payment {

    private Long id;
    private Student student;

    private Date paymentDate;

    private Double sumPayment;


    public Payment() {
    }

    public Payment(Long id, Student student, Date paymentDate, Double sumPayment) {
        this.id = id;
        this.student = student;
        this.paymentDate = paymentDate;
        this.sumPayment = sumPayment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getSumPayment() {
        return sumPayment;
    }

    public void setSumPayment(Double sumPayment) {
        this.sumPayment = sumPayment;
    }
}