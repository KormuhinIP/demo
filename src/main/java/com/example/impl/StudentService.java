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


    @Override
    public List<Student> findAll() {
        String sql = "SELECT* FROM students";
        return jdbcTemplate.query(sql, new StudentRowMapper());
    }

    @Override
    public Student findById(long id) {
        String sql = "SELECT* FROM students where id=:id";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("id", id);
        return jdbcTemplate.queryForObject(sql, source, new StudentRowMapper());
    }


    @Override
    public List<Student> findByName(String name) {
        String sql = "select * from students where concat(upper(lastName), ' ', upper(firstName),' ',upper(patronymic)) like :name";
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
            return new Student(rs.getLong("id"), rs.getString("photo"), rs.getString("lastName"),
                    rs.getString("firstName"), rs.getString("patronymic"), rs.getString("phone"),
                    rs.getDate("birthDay"), rs.getString("license"));
        }
    }


}
