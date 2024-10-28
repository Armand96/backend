package com.gaboot.backend.master.role.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateRoleDto {

    @NotEmpty
    private String roleName;
}
