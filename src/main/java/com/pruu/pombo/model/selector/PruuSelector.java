package com.pruu.pombo.model.selector;

import com.pruu.pombo.model.entity.Pruu;
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
public class PruuSelector extends BaseSelector implements Specification<Pruu> {

    private String userId;
    private String content;
    private LocalDateTime createdAtStart;
    private LocalDateTime createdAtEnd;

    public boolean hasFilter() {
        return (this.isValidString(content))
                || (this.userId != null)
                || (this.createdAtStart != null)
                || (this.createdAtEnd != null);
    }

    @Override
    public Predicate toPredicate(Root<Pruu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if(this.getContent() != null && this.getContent().trim().length() > 0) {
            // WHERE/AND  COLUMN  OPERATOR         VALUE
            //   where    content   like  '%substring do content%'
            predicates.add(cb.like(root.get("content"), "%" + this.getContent() + "%"));
        }

        if(this.getUserId() != null) {
            predicates.add(cb.equal(root.get("user").get("id"), this.getUserId()));
        }

        applyDateRangeFilter(root, cb, predicates, this.getCreatedAtStart(), this.getCreatedAtEnd(), "createdAt");

        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
