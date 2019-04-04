package com.example.dao;

import com.example.model.Student;
import com.example.model.Teacher;

import java.util.List;

public interface TeacherDao {
    public List<Teacher> findAll();

    public Student findById(long id);

    public void save(Teacher teacher);

    public void delete(Teacher teacher);

    public void update(Teacher teacher);

    public List<Teacher> findByName(String name);
}
