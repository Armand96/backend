package com.gaboot.backend.master.user;

import com.gaboot.backend.master.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User>, UserRepoCustom {

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.id = :id ")
    Optional<User> findByIdWithRole(@Param("id") UUID id);

}
