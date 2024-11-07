package com.pruu.pombo.model.selector;

import com.pruu.pombo.model.entity.Complaint;
import com.pruu.pombo.model.enums.ComplaintStatus;
import com.pruu.pombo.model.enums.Reason;
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
public class ComplaintSelector extends BaseSelector implements Specification<Complaint> {

    private String userId;
    private String publicationId;
    private Reason reason;
    private ComplaintStatus status;
    private LocalDateTime createdAtStart;
    private LocalDateTime createdAtEnd;

    @Override
    public Predicate toPredicate(Root<Complaint> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if(this.getUserId() != null) {
            predicates.add(cb.equal(root.get("user").get("id"), this.getUserId()));
        }

        if(this.getPublicationId() != null) {
            predicates.add(cb.equal(root.get("publication").get("id"), this.getPublicationId()));
        }

        if(this.getReason() != null) {
            predicates.add(cb.equal(root.get("reason"), this.getReason()));
        }

        if(this.getStatus() != null) {
            predicates.add(cb.equal(root.get("status"), this.getStatus()));
        }

        applyDateRangeFilter(root, cb, predicates, this.getCreatedAtStart(), this.getCreatedAtEnd(), "createdAt");

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
