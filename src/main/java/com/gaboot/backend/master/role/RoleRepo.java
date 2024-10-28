package com.gaboot.backend.master.role;

import com.gaboot.backend.master.role.entity.Role;
import com.gaboot.backend.master.role.entity.RoleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {
    // @Query("SELECT r FROM Role r LEFT JOIN FETCH r.users")
    // List<Role> findAllRolesWithUsers();

    @Query("SELECT new RoleDto(a.id, a.role_name) FROM master_roles a WHERE a.id = :id")
    Optional<RoleDto> findRoleWithoutUsers(@Param("id") UUID id);
}
