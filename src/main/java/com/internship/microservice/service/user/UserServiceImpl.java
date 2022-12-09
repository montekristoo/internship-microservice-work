package com.internship.microservice.service.user;

import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.mapper.UserMapper;
import com.internship.microservice.routing.DataSourceContext;
import com.internship.microservice.routing.RoutingDataSource;
import com.internship.microservice.service.routing.RoutingService;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@EnableAspectJAutoProxy(exposeProxy = true)
public class UserServiceImpl implements UserService {
    @Autowired
    private RoutingService routingService;
    @Autowired
    private RoutingDataSource routingDataSource;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private SqlSession sqlSession;
    @Autowired
    private UserTransaction userTransaction;
    @Autowired
    private UserMapper userMapper;
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
        userTransaction.begin();
        dbUsersToSend.keySet()
                .forEach((dbToConnect) -> {
                    ((UserService) AopContext.currentProxy()).connect(dbToConnect.toLowerCase(), dbUsersToSend.get(dbToConnect));
                });
        userTransaction.commit();
    }

    @SneakyThrows
    public void connect(String name, List<UserEntity> users) {
        SqlSession session = sqlSessionFactory.openSession();
        for (UserEntity user : users) {
            userMapper.addUser(user);
        }
    }
}
