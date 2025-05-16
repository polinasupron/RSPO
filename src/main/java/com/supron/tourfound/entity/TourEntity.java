package com.supron.tourfound.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Table(name = "tour")
@Entity
public class TourEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String info;
    private String startCountry;
    private int duration;
    private int rating;
    private int sum;

    @OneToMany(mappedBy = "tour")
    Set<UserTourEntity> usersTours;
}
