package com.example.impl;

import com.example.dao.LessonDao;
import com.example.model.Lesson;
import com.example.model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LessonService implements LessonDao {

    private static final Logger logger = LoggerFactory.getLogger(LessonService.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;


    @Override
    public List<Lesson> findAll() {

        logger.debug("findAll method (LessonService) invoked;");

        String sql = "SELECT* FROM lessons";
        return jdbcTemplate.query(sql, new LessonRowMapper());
    }


    public List<Teacher> listTeachers() {

        logger.debug("listTeachers method (LessonService) invoked;");

        List<Teacher> list = new ArrayList<>();
        String sql = "SELECT DISTINCT teacher_id  FROM lessons";
        for (Long teacherId : jdbcTemplate.getJdbcOperations().queryForList(sql, Long.class))
            list.add(teacherService.findById(teacherId));
        return list;
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

    public List<Lesson> findLessons(long teacherId) {

        logger.debug("findLessons method (LessonService) invoked;" + teacherId);

        String sql = "select * from lessons where teacher_id like :teacher_id";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("teacher_id", teacherId);
        return jdbcTemplate.query(sql, source, new LessonRowMapper());
    }

    private final class LessonRowMapper implements RowMapper<Lesson> {
        @Override
        public Lesson mapRow(ResultSet rs, int rowNum) throws SQLException {

            logger.debug("mapRow method (LessonService) invoked;");

            return new Lesson(rs.getLong("id"), rs.getTimestamp("dateLesson"), studentService.findById(rs.getLong("student_id")),
                    teacherService.findById(rs.getLong("teacher_id")));

        }
    }


}



