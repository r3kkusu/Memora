package com.memora.api.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Size(min = 6, max = 100)
    @Column(unique = true)
    private String username;

    @NotNull
    @Size(min = 6, max = 100)
    private String firstName;

    @NotNull
    @Size(min = 6, max = 100)
    private String lastName;

    @NotNull
    @Size(min = 6, max = 100)
    private String password;

    private long birthDate;
    private long dateCreated;
}
