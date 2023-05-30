package com.caloriecatching.caloriecatching.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FoodTable {
    @Id
    private Integer foodId;

    @Column(length = 100)
    private String foodName;

    @Column(length = 50)
    private String korName;

    private Integer foodCalorie;
}
