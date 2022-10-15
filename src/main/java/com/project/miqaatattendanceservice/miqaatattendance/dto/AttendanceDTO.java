package com.project.miqaatattendanceservice.miqaatattendance.dto;

import com.project.miqaatattendanceservice.Utils;

import java.util.Set;

import lombok.Data;

@Data
public class AttendanceDTO {
    private String miqaatId;
    private Set<Integer> attending;
    private Set<Integer> notAttending;
    private Set<Integer> tentative;

    public Set<Integer> getAttending() {
        return Utils.checkGet(attending);
    }

    public Set<Integer> getNotAttending() {
        return Utils.checkGet(notAttending);
    }

    public Set<Integer> getTentative() {
        return Utils.checkGet(tentative);
    }
}
