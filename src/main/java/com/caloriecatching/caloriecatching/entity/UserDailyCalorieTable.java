package com.caloriecatching.caloriecatching.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class UserDailyCalorieTable {

    @EmbeddedId
    private UserDailyCalorieId id;

    private Integer dailyCalorie;

    @Embeddable
    public static class UserDailyCalorieId implements Serializable {

        private LocalDate date;

        @ManyToOne(fetch = FetchType.LAZY)
        @MapsId("userID")
        private UserTable userID;
    }
}
