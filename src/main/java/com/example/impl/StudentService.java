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
        return jdbcTemplate.query("SELECT id, photo, first_name, last_name, patronymic, phone, birthDay, license  FROM students",
                (rs, rowNum) -> new Student(rs.getLong("id"), rs.getString("photo"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("patronymic"),
                        rs.getString("phone"), rs.getDate("birthDay"), rs.getBoolean("license")));
    }

    @Override
    public void save(Student student) {

    }

    @Override
    public void delete(Student student) {

    }

    @Override
    public void update(Student student) {

    }

    public List<Student> findByName(String name) {
/*List<Student>list=new ArrayList<>();
        for (Student student : findAll()) {
            if (student.getLastName().contains(name))
                list.add(student);
        }
        return list;*/
        String sql = "SELECT* FROM students WHERE last_name like CONCAT  ('%', :name, '%') ";


        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Student(rs.getLong("id"), rs.getString("photo"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("patronymic"),
                        rs.getString("phone"), rs.getDate("birthDay"), rs.getBoolean("license")));
    }

}
