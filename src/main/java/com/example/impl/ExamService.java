package com.example.impl;

import com.example.dao.ExamDao;
import com.example.model.Exam;
import com.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ExamService implements ExamDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public List<Exam> findAll() {
        return jdbcTemplate.query("SELECT* FROM exams", new ExamService.ExamRowMapper());
    }

    @Override
    public void save(Exam exam) {

    }

    @Override
    public void delete(Exam exam) {

    }

    @Override
    public void update(Exam exam) {

    }

    @Override
    public List<Exam> findByName(String name) {
        return null;
    }


    private final class ExamRowMapper implements RowMapper<Exam> {

        @Override
        public Exam mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Exam(rs.getLong("id"), rs.getDate("date_exam"), (Student) rs.getObject("student_id"), rs.getString("kind_exam"),
                    rs.getString("comment"), rs.getString("evaluation"));


        }
    }


}