package com.gaboot.backend.master.role;

import com.gaboot.backend.common.dto.ResponseDto;
import com.gaboot.backend.common.exception.ResourceNotFoundException;
import com.gaboot.backend.common.service.MappingService;
import com.gaboot.backend.master.role.dto.CreateRoleDto;
import com.gaboot.backend.master.role.dto.RoleMapper;
import com.gaboot.backend.master.role.dto.UpdateRoleDto;
import com.gaboot.backend.master.role.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RoleService implements RoleServiceInterface {

    private RoleRepo roleRepo;
    private MappingService<Role> mapSvc;

    @Override
    public ResponseDto<Role> findAll() {
        final List<Role> roles = roleRepo.findAll();
        System.out.println(roles.toString());
        final ResponseDto<Role> respDto = new ResponseDto<>();
        mapSvc.mapResponseSuccess(respDto, roles, "",1, roles.size());
        return respDto;
    }

    @Override
    public ResponseDto<Role> findOne(UUID id) {
        final Role role = roleRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found with given id: " + id)
        );
        System.out.println("ROLES: "+role.toString());
        final ResponseDto<Role> respDto = new ResponseDto<>();
        mapSvc.mapResponseSuccess(respDto, role, "");
        return respDto;
    }

    @Override
    public ResponseDto<Role> create(CreateRoleDto roleDto) {
        Role role = RoleMapper.mapToRole(roleDto);
        role = roleRepo.save(role);
        final ResponseDto<Role> respDto = new ResponseDto<>();
        mapSvc.mapResponseSuccess(respDto, role, "Success create");
        return respDto;
    }

    @Override
    public ResponseDto<Role> update(UpdateRoleDto roleDto, UUID id) {
        Role role = roleRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found with given id: " + id)
        );

        RoleMapper.mapToRole(role, roleDto);

        role = roleRepo.save(role);
        final ResponseDto<Role> respDto = new ResponseDto<>();
        mapSvc.mapResponseSuccess(respDto, role, "Success update");
        return respDto;
    }

    @Override
    public ResponseDto<Role> delete(UUID id) {
        Role role = roleRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found with given id: " + id)
        );

        final ResponseDto<Role> respDto = new ResponseDto<>();
        mapSvc.mapResponseSuccess(respDto, (Role) null, "Success delete");
        return respDto;
    }
}
