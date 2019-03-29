package com.example.impl;

import com.example.dao.StudentDao;
import com.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentService implements StudentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Student> findAll() {
        return jdbcTemplate.query("SELECT id, first_name, last_name FROM students",
                (rs, rowNum) -> new Student(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")));
    }

    @Override
    public void save(Student student) {

    }

    @Override
    public void delete(Student student) {

    }


    public void update(Student customer) {
        jdbcTemplate.update("UPDATE students SET first_name=?, last_name=? WHERE id=?",
                customer.getFirstName(), customer.getLastName(), customer.getId());
    }

}
