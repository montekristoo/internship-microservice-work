package com.internship.microservice.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class UserEntity {
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String genre;
    @NotNull
    private Date dateOfBirth;
    @NotNull
    private String nationality;
    @NotNull
    @Size(min = 3, max = 15)
    private String username;
    @NotNull
    private String password;
}
