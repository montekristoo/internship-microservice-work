package com.internship.microservice.service.user;

import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.mapper.UserMapper;
import com.internship.microservice.routing.DataSourceContext;
import com.internship.microservice.routing.RoutingDataSource;
import lombok.SneakyThrows;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@EnableAspectJAutoProxy(exposeProxy = true)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JtaTransactionManager jtaTransactionManager;
    @Autowired
    private UserTransaction userTransaction;
    @Autowired
    private SqlSessionFactoryBean sqlSessionFactory;
    @Autowired
    private DataSourceContext dataSourceContext;
    @Autowired
    private RoutingDataSource routingDataSource;
    private static final List<UserEntity> dbUsers = new ArrayList<>();
    private static final int BATCH_SIZE = 20;

    @Override
    public void addUsers(List<UserEntity> users) {
        users.forEach((user) -> {
            dbUsers.add(user);
            if (dbUsers.size() == BATCH_SIZE) {
                Map<String, List<UserEntity>> usersByCountry = dbUsers.stream()
                        .collect(Collectors.groupingBy(UserEntity::getNationality));
                sendToTransactionContainer(usersByCountry);
                usersByCountry.clear();
                dbUsers.clear();
            }
        });
    }

    @SneakyThrows
    public void sendToTransactionContainer(Map<String, List<UserEntity>> dbUsersToSend) {
        dataSourceContext.setContext("md");
        DataSource dataSource1 = routingDataSource.determineTargetDataSource();
        dataSourceContext.removeContext();
        dataSourceContext.setContext("uk");
        DataSource dataSource2 = routingDataSource.determineTargetDataSource();
        dataSourceContext.removeContext();
        userTransaction.begin();
        List<DataSource> dataSources = new ArrayList<>(List.of(dataSource1, dataSource2));
        dataSources.forEach(dataSource -> {
            try {
                sqlSessionFactory.setDataSource(dataSource);
                SqlSession session = sqlSessionFactory.getObject().openSession(false);
                UserMapper userMapper1 = session.getMapper(UserMapper.class);
                userMapper1.addUser(dbUsersToSend.get("MD").get(0));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        userTransaction.commit();
    }

    @Override
    @SneakyThrows
    public void connect(String name, List<UserEntity> users) {
////        SqlSession session = sqlSessionFactory.openSession();
//        session.getMapper(UserMapper.class).addUser(users.get(0));
//        session.close();

//        for (UserEntity user : users) {
//            userMapper.addUser(user);
//        }
    }

    @Override
    public void addUser(UserEntity user) {
        userMapper.addUser(user);
    }
}
