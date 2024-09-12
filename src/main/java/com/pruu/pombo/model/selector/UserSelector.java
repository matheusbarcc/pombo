package com.pruu.pombo.model.selector;

import com.pruu.pombo.model.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserSelector extends BaseSelector implements Specification<User> {

    private String name;
    private String email;
    private LocalDateTime createdAtStart;
    private LocalDateTime createdAtEnd;

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if(this.getName() != null && !this.getName().trim().isEmpty()) {
            predicates.add(cb.like(root.get("name"), "%" + this.getName() + "%"));
        }

        if(this.getEmail() != null && !this.getEmail().trim().isEmpty()) {
            predicates.add(cb.like(root.get("email"), "%" + this.getEmail() + "%"));
        }

        applyDateRangeFilter(root, cb, predicates, this.getCreatedAtStart(), this.getCreatedAtEnd(), "createdAt");

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
