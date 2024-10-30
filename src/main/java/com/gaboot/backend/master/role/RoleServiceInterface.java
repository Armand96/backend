package com.gaboot.backend.master.role;

import com.gaboot.backend.common.dto.ResponseDto;
import com.gaboot.backend.master.role.dto.CreateRoleDto;
import com.gaboot.backend.master.role.dto.FilterRoleDto;
import com.gaboot.backend.master.role.dto.UpdateRoleDto;
import com.gaboot.backend.master.role.entity.Role;

import java.util.UUID;

public interface RoleServiceInterface {
    ResponseDto<Role> findAll(FilterRoleDto filter);
    ResponseDto<Role> findOne(UUID id);
    ResponseDto<Role> create(CreateRoleDto roleDto);
    ResponseDto<Role> update(UpdateRoleDto roleDto, UUID id);
    ResponseDto<Role> delete(UUID id);
}
