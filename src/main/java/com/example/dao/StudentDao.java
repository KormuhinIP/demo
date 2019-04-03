package com.example.dao;

import com.example.model.Student;

import java.util.List;

public interface StudentDao {
    public List<Student> findAll();

    public void save(Student student);

    public void delete(Student student);

    public void update(Student student);

    public List<Student> findByName(String name);
}
