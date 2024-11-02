package com.gaboot.backend.master.role;

import com.gaboot.backend.common.dto.ResponseDto;
import com.gaboot.backend.common.exception.ResourceNotFoundException;
import com.gaboot.backend.common.service.MappingService;
import com.gaboot.backend.master.role.dto.*;
import com.gaboot.backend.master.role.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RoleService implements RoleServiceInterface {

    private RoleRepo roleRepo;
    private MappingService<Role> mapSvc;

    @Override
    public ResponseDto<Role> findAll(FilterRoleDto filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        Specification<Role> spec = RoleSpec.filterByCriteria(filter);

        final Page<Role> roles = roleRepo.findAll(spec, pageable);
        // System.out.println("Service: "+roles);
        final ResponseDto<Role> respDto = new ResponseDto<>();
        mapSvc.mapResponseSuccess(respDto, roles.getContent(), "",roles.getTotalPages(), ((int) roles.getTotalElements()));
        return respDto;
    }

    @Override
    public ResponseDto<Role> findOne(UUID id) {
        final Role role = roleRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found with given id: " + id)
        );
        System.out.println("ROLE: "+role);
        System.out.println("USERS: "+role.getUsers());
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
        mapSvc.mapResponseSuccess(respDto, role, "Success delete");
        return respDto;
    }
}
