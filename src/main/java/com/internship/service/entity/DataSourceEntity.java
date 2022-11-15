package com.internship.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceEntity {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String jdbcUrl;

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
