package com.pruu.pombo.model.selector;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pruu.pombo.model.entity.Publication;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PublicationSelector extends BaseSelector implements Specification<Publication> {

    private String userId;
    private String content;
    private LocalDateTime createdAtStart;
    private LocalDateTime createdAtEnd;

    @JsonProperty("isLiked")
    private boolean isLiked;

    @Override
    public Predicate toPredicate(Root<Publication> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if(this.getContent() != null && !this.getContent().trim().isEmpty()) {
            // WHERE/AND  COLUMN  OPERATOR         VALUE
            //   where    content   like  '%substring do content%'
            predicates.add(cb.like(root.get("content"), "%" + this.getContent() + "%"));
        }

        if(this.getUserId() != null) {
            predicates.add(cb.equal(root.get("user").get("id"), this.getUserId()));
        }

        applyDateRangeFilter(root, cb, predicates, this.getCreatedAtStart(), this.getCreatedAtEnd(), "createdAt");

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
