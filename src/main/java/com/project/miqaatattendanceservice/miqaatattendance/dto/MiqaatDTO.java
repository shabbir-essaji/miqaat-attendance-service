package com.project.miqaatattendanceservice.miqaatattendance.dto;

import com.project.miqaatattendanceservice.miqaatattendance.domain.Miqaat;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MiqaatDTO {

    private String miqaatId;
    private String name;

    public MiqaatDTO(Miqaat miqaat) {
        this.miqaatId = miqaat.getMiqaatId();
        this.name = miqaat.getName();
    }
}
