package com.caloriecatching.caloriecatching.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "food")
public class Food {

    @Id
    @Column(name = "food_id")
    private Integer foodId;

    @Column(name = "food_name", length = 100)
    private String foodName;

    @Column(name = "kor_name", length = 50)
    private String korName;

    @Column(name = "food_calorie")
    private Integer foodCalorie;
}
