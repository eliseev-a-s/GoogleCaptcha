package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 50)
    private String firstName;

    @NotEmpty @Size(max = 50)
    private String lastName;

    @NotEmpty @Size(max = 128)
    private String email;

    @NotEmpty @Size(max = 60)
    @JsonIgnore
    private String password;

    @Transient
    @JsonIgnore
    private String confirmPassword;

}
