package com.internship.microservice.util;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.internship.microservice.entity.DataSourceEntity;
import com.internship.microservice.exception.WrongDatabaseCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

@Component
public class DataSourceConverter {
    @Autowired
    private Map<String, String> clientPasswords;

    public DataSource entityToDataSource(DataSourceEntity dataSrcEntity) {
        String falsePasswordClient = clientPasswords.get(dataSrcEntity.getName());
        if (!PasswordUtils.verifyPassword(dataSrcEntity.getPassword(), dataSrcEntity.getSalt(), falsePasswordClient)) {
            throw new WrongDatabaseCredentialsException(dataSrcEntity.getName());
        }
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setUniqueResourceName(dataSrcEntity.getName());
        ds.setXaDataSourceClassName("org.postgresql.xa.PGXADataSource");
        Properties p = new Properties();
        p.setProperty("user", dataSrcEntity.getUsername());
        p.setProperty("password", clientPasswords.get(dataSrcEntity.getName()));
        p.setProperty("serverName", "localhost");
        p.setProperty("portNumber", "3002");
        p.setProperty("databaseName", dataSrcEntity.getName());
        ds.setXaProperties(p);
        ds.setPoolSize(10);
        return ds;
    }
}