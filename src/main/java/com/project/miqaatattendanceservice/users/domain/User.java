package com.project.miqaatattendanceservice.users.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "User")
public class User {
    // Attributes
    @Id
    private Integer its;
    private Integer hofIts;
    private String name;
    private Gender gender;

    public enum Gender {
        MALE, FEMALE
    }
}
