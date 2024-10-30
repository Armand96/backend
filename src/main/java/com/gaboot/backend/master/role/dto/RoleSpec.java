package com.gaboot.backend.master.role.dto;

import com.gaboot.backend.master.role.entity.Role;
import org.springframework.data.jpa.domain.Specification;

public class RoleSpec {

    public static Specification<Role> filterByCriteria(FilterRoleDto filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter.getRoleName() != null && !filter.getRoleName().isEmpty()) {
                return criteriaBuilder.like(root.get("roleName"), "%" + filter.getRoleName() + "%");
            }
            return criteriaBuilder.conjunction();
        };
    }
}

