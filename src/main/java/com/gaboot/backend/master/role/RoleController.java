package com.gaboot.backend.master.role;

import com.gaboot.backend.common.dto.ResponseDto;
import com.gaboot.backend.master.role.dto.CreateRoleDto;
import com.gaboot.backend.master.role.dto.FilterRoleDto;
import com.gaboot.backend.master.role.dto.UpdateRoleDto;
import com.gaboot.backend.master.role.entity.Role;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/role")
@AllArgsConstructor
@Validated
public class RoleController {

    private RoleServiceInterface roleSvc;

    @GetMapping()
    public ResponseEntity<ResponseDto<Role>> findAll(@ModelAttribute FilterRoleDto pageReq) {
        return ResponseEntity.ok(roleSvc.findAll(pageReq));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Role>> findOne(@PathVariable UUID id) {
        return ResponseEntity.ok(roleSvc.findOne(id));
    }

    @PostMapping()
    public ResponseEntity<ResponseDto<Role>> create(@Valid @RequestBody CreateRoleDto roleDto) {
        return ResponseEntity.ok(roleSvc.create(roleDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Role>> update(@Valid @RequestBody UpdateRoleDto roleDto, @PathVariable UUID id) {
        return ResponseEntity.ok(roleSvc.update(roleDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Role>> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(roleSvc.delete(id));
    }

}
