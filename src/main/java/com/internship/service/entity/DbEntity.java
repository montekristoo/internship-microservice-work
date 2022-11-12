package com.internship.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}