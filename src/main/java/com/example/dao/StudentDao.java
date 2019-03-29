package com.example.dao;

import com.example.model.Student;

import java.util.List;

public interface StudentDao {
    public List<Student> findAll();

    public void save(Student customer);

    public void delete(Student customer);

    public void update(Student customer);
}
