package com.example.impl;

import com.example.dao.TeacherDao;
import com.example.model.Student;
import com.example.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class TeacherService implements TeacherDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    @Override
    public List<Teacher> findAll() {
        String sql = "SELECT* FROM teachers";
        return jdbcTemplate.query(sql, new TeacherRowMapper());
    }

    @Override
    public Student findById(long id) {
        return null;
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
        return null;
    }

    private final class TeacherRowMapper implements RowMapper<Teacher> {

        @Override
        public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Teacher(rs.getLong("id"), rs.getString("firstName"), rs.getString("lastName"),
                    rs.getString("patronymic"), rs.getString("numberLicense"), rs.getDate("birthDay"), rs.getInt("experience"));
        }
    }
}
