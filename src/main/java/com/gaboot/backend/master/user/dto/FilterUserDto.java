package com.gaboot.backend.master.user.dto;

import com.gaboot.backend.common.dto.PageRequestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class FilterUserDto extends PageRequestDto {
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private Boolean isActive;
}
