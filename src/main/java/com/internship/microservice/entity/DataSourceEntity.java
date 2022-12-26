package com.internship.microservice.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@Builder
@EqualsAndHashCode
public class DataSourceEntity{
    private Long id;
    private String name;
    private String username;
    private String password;
    private String salt;
    private String jdbcUrl;
    private String driverClassName;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof DataSourceEntity dataSourceEntity)) return false;
//        return id.equals(dataSourceEntity.id) && name.equals(dataSourceEntity.name);
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name);
//    }
}
