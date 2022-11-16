package com.internship.service.entity;

import lombok.*;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataSourceEntity {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String jdbcUrl;

    public DataSourceEntity(String name, String username, String password, String jdbcUrl) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.jdbcUrl = jdbcUrl;
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
