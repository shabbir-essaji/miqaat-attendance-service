package com.project.miqaatattendanceservice.miqaatattendance.dto;

import java.util.Set;

import lombok.Data;

@Data
public class AttendanceDTO {
    private String miqaatId;
    private Set<Integer> attending;
    private Set<Integer> notAttending;
    private Set<Integer> tentative;
}
