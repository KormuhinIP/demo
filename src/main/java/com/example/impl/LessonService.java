package com.example.impl;

import com.example.dao.LessonDao;
import com.example.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class LessonService implements LessonDao {


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;


    @Override
    public List<Lesson> findAll() {
        String sql = "SELECT* FROM lessons";
        return jdbcTemplate.query(sql, new LessonRowMapper());
    }


    @Override
    public Lesson findById(long id) {
        return null;
    }

    @Override
    public void save(Lesson lesson) {

    }

    @Override
    public void delete(Lesson lesson) {

    }

    @Override
    public void update(Lesson lesson) {

    }

    @Override
    public List<Lesson> findByName(String name) {
        return null;
    }

    private final class LessonRowMapper implements RowMapper<Lesson> {
        @Override
        public Lesson mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Lesson(rs.getLong("id"), rs.getDate("dateLesson"), studentService.findById(rs.getLong("student_id")),
                    teacherService.findById(rs.getLong("teacher_id")));

        }
    }


}



