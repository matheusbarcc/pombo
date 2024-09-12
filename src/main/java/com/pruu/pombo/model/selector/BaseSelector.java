package com.pruu.pombo.model.selector;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

@Data
public abstract class BaseSelector {

    private int page;
    private int limit;

    public BaseSelector() {
        this.limit = 0;
        this.page = 0;
    }

    public boolean hasPagination() {
        return this.limit > 0 && this.page > 0;
    }

    public boolean isValidString(String text) {
        return text != null && !text.isBlank();
    }

    public static void applyDateRangeFilter(Root root,
                                            CriteriaBuilder cb, List<Predicate> predicates,
                                            LocalDateTime startDate, LocalDateTime endDate, String attributeName) {
        if (startDate != null && endDate != null) {
            // WHERE attribute BETWEEN min AND max
            predicates.add(cb.between(root.get(attributeName), startDate, endDate));
        } else if (startDate != null) {
            // WHERE attribute >= min
            predicates.add(cb.greaterThanOrEqualTo(root.get(attributeName), startDate));
        } else if (endDate != null) {
            // WHERE attribute <= max
            predicates.add(cb.lessThanOrEqualTo(root.get(attributeName), endDate));
        }
    }
}
