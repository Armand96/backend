package com.gaboot.backend.master.user;

import com.gaboot.backend.master.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.role r") // Adjust 'role' to match your field name
    Page<User> findAllWithRoles(Specification<User> spec, Pageable pageable);

    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.id = :id ")
    Optional<User> findByIdWithRole(@Param("id") UUID id);

}
