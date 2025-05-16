package com.supron.tourfound.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "user_ratting")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRattingEntity {

    @EmbeddedId
    private UserTourKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @MapsId("tourId")
    @JoinColumn(name = "tour_id")
    private TourEntity tour;

    private byte ratting;

    public UserRattingEntity(UserEntity user, TourEntity tour) {
        this.user = user;
        this.tour = tour;
        this.id = new UserTourKey(user.getId(), tour.getId());
    }
}
