package com.thoughtpearls.specification;

import com.thoughtpearls.dto.SearchParametersDto;
import com.thoughtpearls.model.Lead;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LeadSpecification {
    public static Specification<Lead> getSearchSpecification(SearchParametersDto searchParametersDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchParametersDto.getSearchString() != null && !searchParametersDto.getSearchString().isEmpty()) {
                String searchString = "%" + searchParametersDto.getSearchString().trim().toLowerCase() + "%";
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("leadName")), searchString),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchString),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("reminderTopic")), searchString)
                ));
            }

            if (searchParametersDto.getLeadType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("leadType"), searchParametersDto.getLeadType()));
            }

            if (searchParametersDto.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), searchParametersDto.getStatus()));
            }

            if (searchParametersDto.getReminderDate() != null) {
                predicates.add(criteriaBuilder.equal(root.get("reminderDate"), searchParametersDto.getReminderDate()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
