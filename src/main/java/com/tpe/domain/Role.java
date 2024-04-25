package com.tpe.domain;

import com.tpe.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 25,nullable = false)
    //ADMIN,STUDENT //admin/ Admin, Student these can cause an exception
    @Enumerated(EnumType.STRING)
    //This annotation is useful when you want to store the string representation of enum constants in database
    private UserRole name;

    //toString

    @Override
    public String toString() {
        return "Role{" +
                "name=" + name +
                '}';
    }

}
