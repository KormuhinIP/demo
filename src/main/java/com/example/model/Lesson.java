package com.example.model;

import java.util.Date;

public class Lesson {


    private Long id;
    private Date dateLesson;
    private Student student;
    private Teacher teacher;


    public Lesson() {
    }

    public Lesson(Long id, Date dateLesson, Student student, Teacher teacher) {
        this.id = id;
        this.dateLesson = dateLesson;
        this.student = student;
        this.teacher = teacher;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateLesson() {
        return dateLesson;
    }

    public void setDateLesson(Date dateLesson) {
        this.dateLesson = dateLesson;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}