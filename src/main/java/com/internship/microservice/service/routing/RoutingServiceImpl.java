package com.internship.microservice.service.routing;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoutingServiceImpl implements RoutingService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
}
