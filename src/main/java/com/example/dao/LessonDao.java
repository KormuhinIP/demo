package com.example.dao;

import com.example.model.Lesson;

import java.util.List;

public interface LessonDao {
    public List<Lesson> findAll();

    public Lesson findById(long id);

    public void save(Lesson lesson);

    public void delete(Lesson lesson);

    public void update(Lesson lesson);

    public List<Lesson> findByName(String name);
}
