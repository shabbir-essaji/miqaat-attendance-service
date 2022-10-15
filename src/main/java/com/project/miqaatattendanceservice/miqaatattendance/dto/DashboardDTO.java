package com.project.miqaatattendanceservice.miqaatattendance.dto;

import com.project.miqaatattendanceservice.users.dto.UserDTO;

import java.util.Set;

import lombok.Data;

@Data
public class DashboardDTO {
    private String miqaatName;
    private Set<UserDTO> attending;
    private Set<UserDTO> notAttending;
    private Set<UserDTO> tentative;
}
