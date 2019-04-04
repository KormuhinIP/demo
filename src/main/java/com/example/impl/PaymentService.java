package com.example.impl;

import com.example.dao.PaymentDao;
import com.example.model.Payment;
import com.example.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class PaymentService implements PaymentDao {

    @Autowired
    private StudentService studentService;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Payment> findAll() {
        String sql = "SELECT* FROM payments";
        return jdbcTemplate.query(sql, new PaymentRowMapper());
    }

    @Override
    public Payment findById(long id) {
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
    public List<Payment> findByName(String name) {
        return null;
    }

    private final class PaymentRowMapper implements RowMapper<Payment> {

        @Override
        public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Payment(rs.getLong("id"), studentService.findById(rs.getLong("student_id")), rs.getDate("paymentDate"), rs.getDouble("sumPayment"));

        }
    }


}