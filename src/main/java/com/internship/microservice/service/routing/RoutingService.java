package com.internship.microservice.service.routing;

import com.internship.microservice.entity.UserEntity;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public interface RoutingService {
    void connect(SqlSession sqlSession, String name, List<UserEntity> users);
}
