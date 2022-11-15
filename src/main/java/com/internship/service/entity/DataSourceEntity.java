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
public class DbEntity {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String jdbcUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DbEntity dbEntity)) return false;
        return id.equals(dbEntity.id) && name.equals(dbEntity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
