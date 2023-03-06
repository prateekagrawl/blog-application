package com.blogapplication.blogapp.payloads;

import jakarta.persistence.SequenceGenerators;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {

    private int id;
    private String name;
}
