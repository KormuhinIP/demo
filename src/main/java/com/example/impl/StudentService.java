package com.example.impl;

import com.example.dao.StudentDao;
import com.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class StudentService implements StudentDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    public List<Student> findAll() {
        return jdbcTemplate.query("SELECT* FROM students", new StudentRowMapper());
    }


    @Override
    public List<Student> findByName(String name) {

        String sql = "select * from students where concat(upper(last_name), ' ', upper(first_name),' ',upper(patronymic)) like :name";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("name", "%" + name.toUpperCase() + "%");


        return jdbcTemplate.query(sql, source, new StudentRowMapper());

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


    private final class StudentRowMapper implements RowMapper<Student> {

        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Student(rs.getLong("id"), rs.getString("photo"), rs.getString("last_name"),
                    rs.getString("first_name"), rs.getString("patronymic"), rs.getString("phone"),
                    rs.getDate("birthDay"), rs.getBoolean("license"));
        }
    }


}
