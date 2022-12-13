package com.internship.microservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @NotBlank
    private String genre;
    @NotNull
    private Date dateOfBirth;
    @NotNull
    @Size(min = 2, max = 3)
    private String nationality;
    @NotNull
    @Size(min = 3, max = 15)
    private String username;
    @NotNull
    private String password;
}
