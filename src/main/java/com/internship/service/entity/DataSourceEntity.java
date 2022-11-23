package com.internship.service.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class DataSourceEntity implements Serializable {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String jdbcUrl;
    private String driverClassName;

    public DataSourceEntity(Long id, String name, String username, String password, String jdbcUrl, String driverClassName) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.jdbcUrl = jdbcUrl;
        this.driverClassName = driverClassName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataSourceEntity dataSourceEntity)) return false;
        return id.equals(dataSourceEntity.id) && name.equals(dataSourceEntity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
