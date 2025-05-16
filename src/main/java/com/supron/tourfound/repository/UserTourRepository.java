package com.supron.tourfound.repository;

import com.supron.tourfound.entity.UserTourEntity;
import com.supron.tourfound.entity.UserTourKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTourRepository extends JpaRepository<UserTourEntity, UserTourKey> {
}
