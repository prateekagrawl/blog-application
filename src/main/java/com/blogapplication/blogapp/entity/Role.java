package com.blogapplication.blogapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Role {
    @Id
    private Integer id; //won't autoincrement role since they will be limited.

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();


}
