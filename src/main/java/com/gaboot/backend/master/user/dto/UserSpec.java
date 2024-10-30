package com.gaboot.backend.master.user.dto;

import com.gaboot.backend.master.user.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpec {

    public static Specification<User> filter(FilterUserDto filter) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            if (filter.getUsername() != null && !filter.getUsername().isEmpty()) {
               predicates = criteriaBuilder.and(criteriaBuilder.like(root.get("username"), "%" + filter.getUsername() + "%"));
            }
            if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
               predicates = criteriaBuilder.and(criteriaBuilder.like(root.get("email"), "%" + filter.getEmail() + "%"));
            }
            if (filter.getFirstname() != null && !filter.getFirstname().isEmpty()) {
                predicates = criteriaBuilder.and(criteriaBuilder.like(root.get("firstname"), "%" + filter.getFirstname() + "%"));
            }
            if (filter.getLastname() != null && !filter.getLastname().isEmpty()) {
                predicates = criteriaBuilder.and(criteriaBuilder.like(root.get("lastname"), "%" + filter.getLastname() + "%"));
            }
            if (filter.getIsActive() != null) {
                predicates = criteriaBuilder.and(criteriaBuilder.equal(root.get("isActive"), filter.getIsActive()));
            }
            return predicates;
        };
    }
}
