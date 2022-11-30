package com.internship.microservice.service.routing;

import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.guice.transactional.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@EnableScheduling
public class RoutingServiceImpl implements RoutingService {
    @Autowired
    private SqlSessionFactory sessionFactory;
    @Autowired
    private UserMapper userMapper;


    @Override
    public void connect(String name, List<UserEntity> users) {
        if (name.equals("md")) {
            users.get(4).setGenre("GGGGGG");
        }
        users.forEach(userMapper::addUser);
    }
}
