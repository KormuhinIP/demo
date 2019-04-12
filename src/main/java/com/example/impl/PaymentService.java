package com.example.impl;

import com.example.dao.PaymentDao;
import com.example.model.Payment;
import com.example.model.Student;
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
public class PaymentService implements PaymentDao {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Payment> findAll() {

        logger.debug("findAll method (PaymentService) invoked;");

        String sql = "SELECT* FROM payments";
        return jdbcTemplate.query(sql, new PaymentRowMapper());
    }

    @Override
    public Payment findById(long id) {
        return null;
    }

    @Override
    public void save(Payment payment) {

    }

    @Override
    public void delete(Payment payment) {

    }

    @Override
    public void update(Payment payment) {

    }


    public List<Student> paidStudents() {

        logger.debug("paidStudents method (PaymentService) invoked;");

        List<Student> list = new ArrayList<>();
        String sql = "SELECT DISTINCT student_id  FROM payments";
        for (Long studentId : jdbcTemplate.getJdbcOperations().queryForList(sql, Long.class)) {
            list.add(studentService.findById(studentId));
        }
        return list;
    }


    @Override
    public List<Payment> findPaidStudents(long studentId) {

        logger.debug("findPaidStudents method (PaymentService) invoked;" + studentId);

        String sql = "select * from payments where student_id like :student";
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("student", studentId);
        return jdbcTemplate.query(sql, source, new PaymentRowMapper());
    }

    @Override
    public double paymentOfMonth(int month) {

        logger.debug("paymentOfMonth method (PaymentService) invoked;" + month);

        String sql = "SELECT SUM (sumPayment) FROM payments WHERE MONTH(paymentDate) = ";
        return jdbcTemplate.getJdbcOperations().queryForObject(sql + String.valueOf(month), Integer.class);
    }

    private final class PaymentRowMapper implements RowMapper<Payment> {

        @Override
        public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {

            logger.debug("mapRow method (ExamRowMapper) invoked;");

            return new Payment(rs.getLong("id"), studentService.findById(rs.getLong("student_id")), rs.getDate("paymentDate"), rs.getDouble("sumPayment"));

        }
    }


}