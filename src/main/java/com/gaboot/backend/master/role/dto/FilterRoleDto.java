package com.gaboot.backend.master.role.dto;

import com.gaboot.backend.common.dto.PageRequestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FilterRoleDto extends PageRequestDto {
    private String roleName;
}
