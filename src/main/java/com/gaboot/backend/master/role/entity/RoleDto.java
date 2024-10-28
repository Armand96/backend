package com.gaboot.backend.master.role.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class RoleDto {
    private UUID id;
    private String roleName;
}
