package com.gaboot.backend.master.role.dto;

import com.gaboot.backend.master.role.entity.Role;
import com.gaboot.backend.master.role.entity.RoleDto;
import org.springframework.beans.BeanUtils;

public class RoleMapper {
    public static void mapToDto(Role role, RoleDto roleDto) {
        BeanUtils.copyProperties(role, roleDto);
    }

    public static RoleDto mapToDto(Role role) {
        RoleDto dto = new RoleDto();
        BeanUtils.copyProperties(role, dto, "users");
        return dto;
    }

    // ============================ create
    public static Role mapToRole(CreateRoleDto createRoleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(createRoleDto, role);
        return role;
    }

    public static void mapToRole(Role role, CreateRoleDto createRoleDto) {
        BeanUtils.copyProperties(createRoleDto, role);
    }

    // ============================ update
    public static Role mapToRole(UpdateRoleDto updateRoleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(updateRoleDto, role);
        return role;
    }

    public static void mapToRole(Role role, UpdateRoleDto updateRoleDto) {
        BeanUtils.copyProperties(updateRoleDto, role);
    }
}
