package com.gaboot.backend.master.role;

import com.gaboot.backend.master.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepo extends JpaRepository<Role, UUID> {

    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.users WHERE r.id = :id")
    Optional<Role> findByIdWithUsers(@Param("id") UUID id);
}
