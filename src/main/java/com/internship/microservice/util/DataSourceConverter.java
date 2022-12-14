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
    private static final String POSTGRES_CLASS_NAME = "org.postgresql.xa.PGXADataSource";
    private static final String HOST_SERVER = "localhost";
    private static final String PORT_NUMBER = "3002";

    public DataSource entityToDataSource(DataSourceEntity dataSrcEntity) {
        if (checkPassword(dataSrcEntity)) {
            AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
            ds.setUniqueResourceName(dataSrcEntity.getName());
            ds.setXaDataSourceClassName(POSTGRES_CLASS_NAME);
            Properties p = new Properties();
            p.setProperty("user", dataSrcEntity.getUsername());
            p.setProperty("password", dataSrcEntity.getName().equals("main_db") ? dataSrcEntity.getPassword() : clientPasswords.get(dataSrcEntity.getName()));
            p.setProperty("serverName", HOST_SERVER);
            p.setProperty("portNumber", PORT_NUMBER);
            p.setProperty("databaseName", dataSrcEntity.getName());
            ds.setXaProperties(p);
            ds.setPoolSize(10);

            return ds;

        } else {
            throw new WrongDatabaseCredentialsException(dataSrcEntity.getName());
        }
    }


    private boolean checkPassword(DataSourceEntity dataSource) {
        if (dataSource.getName().equals("main_db")) {

            return true;
        }
        String falsePasswordClient = clientPasswords.get(dataSource.getName());
        return PasswordUtils.verifyPassword(dataSource.getPassword(), dataSource.getSalt(), falsePasswordClient);
    }
}