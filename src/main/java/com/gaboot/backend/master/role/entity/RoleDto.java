package com.gaboot.backend.master.role.entity;

import com.gaboot.backend.master.user.entity.User;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RoleDto {
    private UUID id;
    private String roleName;
    private List<User> users;
}
