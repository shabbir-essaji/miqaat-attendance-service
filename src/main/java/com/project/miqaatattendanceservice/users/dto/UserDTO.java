package com.project.miqaatattendanceservice.users.dto;

import com.project.miqaatattendanceservice.users.domain.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private Integer its;
    private String name;

    public UserDTO(User user) {
        this.its = user.getIts();
        this.name = user.getName();
    }
}
