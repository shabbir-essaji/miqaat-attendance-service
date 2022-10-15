package com.project.miqaatattendanceservice.users.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserWithActionDTO extends UserDTO {
    //0 - NA, 1 - Attending, -1 - Tentative
    private Integer response;
}
