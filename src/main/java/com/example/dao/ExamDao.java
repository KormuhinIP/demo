package com.example.dao;

import com.example.model.Exam;

import java.util.List;

public interface ExamDao {
    public List<Exam> findAll();

    public void save(Exam exam);

    public void delete(Exam exam);

    public void update(Exam exam);

    public List<Exam> findByName(String name);
}
