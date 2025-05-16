package com.supron.tourfound.repository.specification;

import com.supron.tourfound.entity.TourEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

@Component
public class TourNameSpecificationBuilder extends AbstractTourSpecificationBuilder {

    @Override
    String getFilter() {
        return "filter";
    }

    @Override
    Predicate getFilterPredicate(CriteriaBuilder criteriaBuilder, Root<TourEntity> root, String filter) {
        return criteriaBuilder.like(root.get("name"), "%" + filter + "%");
    }
}
