//package com.internship.service.controller;
//
//import com.internship.service.entity.DbModel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.support.JdbcUtils;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.List;
//
//import static com.internship.service.mapper.DataSourceMapper.*;
//
//@RestController
//@RequestMapping("/")
//public class MainDbController {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    private List<DbModel> databases;
//    private static final String sql = "CREATE TABLE IF NOT EXISTS test (id int, description text);";
//
//    @Scheduled(fixedDelayString = "2000")
//    public void insertIntoDb1() throws SQLException {
//        DbModel dbModel = databases.stream()
//                .filter(dbModel1 -> dbModel1.getName().equals("db_1"))
//                .findFirst()
//                .get();
//        jdbcTemplate.setDataSource(modelToDataSource(dbModel));
//        jdbcTemplate.execute(sql);
//        JdbcUtils.closeConnection(jdbcTemplate.getDataSource().getConnection());
//    }
//
//    @Scheduled(fixedDelayString = "2000")
//    public void insertIntoDb2() throws SQLException {
//        DbModel dbModel = databases.stream()
//                .filter(dbModel1 -> dbModel1.getName().equals("db_2"))
//                .findFirst()
//                .get();
//        jdbcTemplate.setDataSource(modelToDataSource(dbModel));
//        jdbcTemplate.execute(sql);
//        JdbcUtils.closeConnection(jdbcTemplate.getDataSource().getConnection());
//    }
//
//    @Scheduled(fixedDelayString = "2000")
//    public void insertIntoDb3() throws SQLException {
//        DbModel dbModel = databases.stream()
//                .filter(dbModel1 -> dbModel1.getName().equals("db_3"))
//                .findFirst()
//                .get();
//        jdbcTemplate.setDataSource(modelToDataSource(dbModel));
//        jdbcTemplate.execute(sql);
//        JdbcUtils.closeConnection(jdbcTemplate.getDataSource().getConnection());
//    }
//
//}
