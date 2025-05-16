package com.supron.tourfound.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public interface SpecificationBuilder<T> {

    Specification<T> build(Map<String, String> params);
}
