package com.supron.tourfound.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "user_tour")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTourEntity {

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

    private boolean isTourPaid;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dateTime;

    public UserTourEntity(UserEntity user, TourEntity tour) {
        this.user = user;
        this.tour = tour;
        this.id = new UserTourKey(user.getId(), tour.getId());

    }
}
