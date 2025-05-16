package com.supron.tourfound.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTourKey implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "tour_id")
    Long tourId;

}
