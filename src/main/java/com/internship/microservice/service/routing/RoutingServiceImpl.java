package com.internship.microservice.service.routing;

import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.mapper.UserMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service

public class RoutingServiceImpl implements RoutingService {

    @Override
    @SneakyThrows
    public void connect(SqlSession sqlSession, String name, List<UserEntity> users) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(sqlSession.getConnection().getCatalog());
        users.forEach(mapper::addUser);
    }
}
