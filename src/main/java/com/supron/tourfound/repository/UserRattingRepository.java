package com.supron.tourfound.repository;

import com.supron.tourfound.entity.UserRattingEntity;
import com.supron.tourfound.entity.UserTourKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRattingRepository extends JpaRepository<UserRattingEntity, UserTourKey> {

    Optional<UserRattingEntity> findByUserIdAndTourId(Long userId, Long tourId);

    List<UserRattingEntity> findByTourId(Long tourId);

    int countByTourId(Long tourId);
}
