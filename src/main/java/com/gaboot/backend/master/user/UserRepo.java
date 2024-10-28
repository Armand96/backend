package com.gaboot.backend.master.user;

import com.gaboot.backend.master.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {

    // This method won't fetch roles eagerly
    // List<User> findAll();

    // @Query("SELECT u FROM User u JOIN FETCH u.role")
    // List<User> findAllUsersWithRoles();

    // @EntityGraph(attributePaths = "role")
    // @Query("SELECT u FROM User u")
    // List<User> findAllWithRole();
}
