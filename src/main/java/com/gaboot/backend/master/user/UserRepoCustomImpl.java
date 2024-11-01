package com.gaboot.backend.master.user;

import com.gaboot.backend.master.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepoCustomImpl implements UserRepoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<User> findAllWithRoles(Specification<User> spec, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // Create query for fetching Users with roles
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        // Apply Specification filters
        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
            query.where(predicate);
        }

        // Join roles
        root.fetch("role", JoinType.LEFT);

        // Execute query with pagination
        List<User> users = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        // Create a separate query for the count
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        if (spec != null) {
            Predicate countPredicate = spec.toPredicate(countRoot, countQuery, criteriaBuilder);
            countQuery.where(countPredicate);
        }
        countQuery.select(criteriaBuilder.count(countRoot));
        long count = entityManager.createQuery(countQuery).getSingleResult();

        // Return a PageImpl with the results and total count
        return new PageImpl<>(users, pageable, count);
    }
}

