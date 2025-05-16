package com.supron.tourfound.repository.specification;

import com.supron.tourfound.entity.TourEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

import static java.util.Objects.nonNull;

public abstract class AbstractTourSpecificationBuilder implements SpecificationBuilder<TourEntity> {

    @Override
    public Specification<TourEntity> build(Map<String, String> params) {
        String filter = params.get(getFilter());
        if (nonNull(filter) && !filter.isBlank()) {
            return (root, query, criteriaBuilder) ->
                    getFilterPredicate(criteriaBuilder, root, filter);
        }

        return null;

    }

    abstract String getFilter();

    abstract Predicate getFilterPredicate(CriteriaBuilder criteriaBuilder, Root<TourEntity> root, String filter);
}
