package com.gaboot.backend.master.role.dto;

import com.gaboot.backend.master.role.entity.Role;
import org.springframework.beans.BeanUtils;

public class RoleMapper {

    // ============================ create
    public static Role mapToRole(CreateRoleDto roleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        return role;
    }

    public static void mapToRole(Role role, CreateRoleDto roleDto) {
        BeanUtils.copyProperties(roleDto, role);
    }

    // ============================ update
    public static Role mapToRole(UpdateRoleDto roleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        return role;
    }

    public static void mapToRole(Role role, UpdateRoleDto roleDto) {
        BeanUtils.copyProperties(roleDto, role);
    }
}
