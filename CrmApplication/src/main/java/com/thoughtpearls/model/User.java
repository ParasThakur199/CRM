package com.thoughtpearls.model;

import com.thoughtpearls.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role= Role.User;
    @OneToMany(mappedBy = "user")
    private List<Lead> leads;
}
