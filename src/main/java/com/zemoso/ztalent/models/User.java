package com.zemoso.ztalent.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Entity
@Table(name = "User")
@Getter
@Setter
public class User extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Email
    @Column(nullable = false, name="EMAIL", unique = true)
    private String email;

    @Column(nullable = false, name="PASSWORD", unique = true)
    private String password;
}
