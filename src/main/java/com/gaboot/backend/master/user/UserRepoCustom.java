package com.gaboot.backend.master.user;

import com.gaboot.backend.master.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserRepoCustom {
    Page<User> findAllWithRoles(Specification<User> spec, Pageable pageable);
}
