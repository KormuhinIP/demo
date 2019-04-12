package com.example.impl;

import com.example.dao.ExamDao;
import com.example.model.Exam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Component
public class ExamService implements ExamDao {

    private static final Logger logger = LoggerFactory.getLogger(ExamService.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private StudentService studentService;


    @Override
    public List<Exam> findAll() {

        logger.debug("findAll method (ExamService) invoked;");

        String sql = "SELECT* FROM exams";
        return jdbcTemplate.query(sql, new ExamRowMapper());
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
    public List<Exam> findByDate(Date date) {

        logger.debug("findByDate method (ExamService) invoked;");

        String sql = "select * from exams where dateExam like :date";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("date", "%" + date + "%");
        return jdbcTemplate.query(sql, source, new ExamRowMapper());
    }


    private final class ExamRowMapper implements RowMapper<Exam> {
        @Override
        public Exam mapRow(ResultSet rs, int rowNum) throws SQLException {

            logger.debug("mapRow method (ExamRowMapper) invoked;");

            return new Exam(rs.getLong("id"), rs.getDate("dateExam"), studentService.findById(rs.getLong("student_id")),
                rs.getString("kindExam"), rs.getString("comment"), rs.getString("evaluation"));

        }
    }

}