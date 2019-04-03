package com.example.model;

import java.util.Date;

public class Exam {

    private Long id;
    private Date date;
    private Student student;
    private String kindExam, comment, evaluation;


    public Exam() {
    }

    public Exam(Long id, Date date, Student student, String kindExam, String comment, String evaluation) {
        this.id = id;
        this.date = date;
        this.student = student;
        this.kindExam = kindExam;
        this.comment = comment;
        this.evaluation = evaluation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getKindExam() {
        return kindExam;
    }

    public void setKindExam(String kindExam) {
        this.kindExam = kindExam;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }
}
