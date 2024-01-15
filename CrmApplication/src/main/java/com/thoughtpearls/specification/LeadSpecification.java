package com.thoughtpearls.specification;

import com.thoughtpearls.dto.requestdto.SearchParameterRequestDto;
import com.thoughtpearls.model.Lead;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LeadSpecification {
    public static Specification<Lead> getSearchSpecification(SearchParameterRequestDto searchParameterRequestDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchParameterRequestDto.getSearchString() != null && !searchParameterRequestDto.getSearchString().isEmpty()) {
                String searchString = "%" + searchParameterRequestDto.getSearchString().trim().toLowerCase() + "%";
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("leadName")), searchString),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchString),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("reminderTopic")), searchString),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("user").get("firstName")), searchString),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("status").get("code")), searchString),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("leadType").get("code")), searchString)
                ));
            }

            if (searchParameterRequestDto.getLeadType()!=null) {
                predicates.add(criteriaBuilder.equal(root.get("leadType").get("id"), searchParameterRequestDto.getLeadType()));
            }

            if (searchParameterRequestDto.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status").get("id"), searchParameterRequestDto.getStatus()));
            }

            if (searchParameterRequestDto.getUserId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), searchParameterRequestDto.getUserId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
