package com.tpe.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 25, nullable = false)
    private String firstName;
    @Column(length = 25, nullable = false)
    private String lastName;
    @Column(length = 25, nullable = false,unique = true)
    private String userName;
    @Column(length = 255, nullable = false) //2 --> base64
    private String password;

    @ManyToMany(fetch = FetchType.EAGER) //by default fetch type is lazy. If we want to see user's role
    //directly we need to set fetch ro EAGER
    @JoinTable(name = "tbl_user_role",
            joinColumns = @JoinColumn(name=("user_id")),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>(); //To set unique roles like Student, Admin, Teacher
}
