package com.internship.microservice.service.routing;

import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RoutingServiceImpl implements RoutingService {
    private final SqlSessionFactory sqlSessionFactory;

    @Autowired
    public RoutingServiceImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public void connect(String name, List<UserEntity> users) {
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH); session) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            for (UserEntity user : users) {
                mapper.addUser(user);
            }
            session.flushStatements();
        }
        catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
