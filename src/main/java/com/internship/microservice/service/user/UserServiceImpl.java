package com.internship.microservice.service.user;

import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.service.routing.RoutingService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RoutingService routingService;
    @Autowired
    private SqlSessionFactory sessionFactory;
    private static final List<UserEntity> dbAndUsers = new ArrayList<>();
    private static final int BATCH_SIZE = 20;


    @Override
    public void addUsers(List<UserEntity> users) {
        users.forEach((user) -> {
            dbAndUsers.add(user);
            if (dbAndUsers.size() == BATCH_SIZE) {
                Map<String, List<UserEntity>> usersByCountry = dbAndUsers.stream()
                        .collect(Collectors.groupingBy(UserEntity::getNationality));
                sendToTransactionContainer(usersByCountry);
                usersByCountry.clear();
                dbAndUsers.clear();
            }
        });
    }


    public void sendToTransactionContainer(Map<String, List<UserEntity>> dbUsersToSend) {
        SqlSession sqlSession = sessionFactory.openSession(ExecutorType.BATCH);
        try {
            dbUsersToSend.keySet()
                    .forEach((dbToConnect) -> {
                        this.routingService.connect(sqlSession, dbToConnect.toLowerCase(), dbUsersToSend.get(dbToConnect));
                    });
            sqlSession.commit();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            sqlSession.rollback();
        }
        finally {
            sqlSession.close();
        }
    }
}
