package com.internship.microservice.service.routing;

import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.mapper.UserMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.guice.MyBatisModule;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service

public class RoutingServiceImpl implements RoutingService {

//    @Override
//    public void connect(String name, List<UserEntity> users) {
//        Connection connection = null;
////            if (name.equals("md")) {
////                users.get(4).setGenre("incorrect result man");
////            }
//        try {
//            connection = routingDataSource.determineTargetDataSource().getConnection();
//            connection.setAutoCommit(false);
//            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (first_name, last_name, genre, date_of_birth, nationality, username, password)" +
//                    " VALUES (?, ?, ?, ?, ?, ?, ?)");
//            Iterator<UserEntity> iterator = users.iterator();
//            while (iterator.hasNext()) {
//                UserEntity user = iterator.next();
//                preparedStatement.setString(1, user.getFirstName());
//                preparedStatement.setString(2, user.getLastName());
//                preparedStatement.setString(3, user.getGenre());
//                preparedStatement.setDate(4, user.getDateOfBirth());
//                preparedStatement.setString(5, user.getNationality());
//                preparedStatement.setString(6, user.getPassword());
//                preparedStatement.setString(7, user.getUsername());
//                preparedStatement.addBatch();
//            }
//            connections.put(connection, preparedStatement);
//        }
//        catch (SQLException sqlException) {
//            ROLLBACK = true;
//            sqlException.getMessage();
//        }
//    }


    //TODO XADataSource, JPBoss study

    @Override
    @SneakyThrows
    public void connect(SqlSession sqlSession, String name, List<UserEntity> users) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(sqlSession.getConnection().getCatalog());
        users.forEach(mapper::addUser);
    }
}
