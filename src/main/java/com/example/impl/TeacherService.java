package com.example.impl;

import com.example.dao.TeacherDao;
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
import java.util.List;

@Component
public class TeacherService implements TeacherDao {

    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public List<Teacher> findAll() {

        logger.debug("findAll method (TeacherService) invoked;");

        String sql = "SELECT* FROM teachers";
        return jdbcTemplate.query(sql, new TeacherRowMapper());
    }

    @Override
    public Teacher findById(long id) {

        logger.debug("findById method (TeacherService) invoked;" + id);

        String sql = "SELECT* FROM teachers where id=:id";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id", id);
        return jdbcTemplate.queryForObject(sql, source, new TeacherRowMapper());
    }


    @Override
    public void save(Teacher teacher) {

    }

    @Override
    public void delete(Teacher teacher) {

    }

    @Override
    public void update(Teacher teacher) {

    }

    @Override
    public List<Teacher> findByName(String name) {

        logger.debug("findByName method (TeacherService) invoked;" + name);

        String sql = "select * from teachers where concat(upper(lastName), ' ', upper(firstName),' ',upper(patronymic)) like :name";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("name", "%" + name.toUpperCase() + "%");
        return jdbcTemplate.query(sql, source, new TeacherRowMapper());
    }

    @Override
    public int getTeacherStatistic(int month) {

        logger.debug("getStudentStatistic method (TeacherService) invoked;" + month);

        String sql = "SELECT COUNT(*) FROM teachers WHERE MONTH(dateAdding) = ";
        return jdbcTemplate.getJdbcOperations().queryForObject(sql + String.valueOf(month), Integer.class);
    }

    private final class TeacherRowMapper implements RowMapper<Teacher> {

        @Override
        public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {

            logger.debug("mapRow method (TeacherRowMapper) invoked;");

            return new Teacher(rs.getLong("id"), rs.getString("firstName"), rs.getString("lastName"),
                rs.getString("patronymic"), rs.getString("numberLicense"), rs.getDate("birthDay"), rs.getInt("experience"));
        }
    }
}
