package com.internship.service.mapper;

import com.internship.service.entity.DbModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DbMapper implements RowMapper<DbModel> {
    public DbModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        DbModel dbModel = new DbModel();
        dbModel.setId(rs.getLong("id"));
        dbModel.setName(rs.getString("name"));
        dbModel.setUsername(rs.getString("username"));
        dbModel.setPassword(rs.getString("password"));
        dbModel.setJdbcUrl(rs.getString("jdbc_url"));
        return dbModel;
    }
}
