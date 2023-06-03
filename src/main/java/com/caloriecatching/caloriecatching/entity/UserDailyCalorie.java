package com.caloriecatching.caloriecatching.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "user_daily_calorie")
public class UserDailyCalorie {

    @EmbeddedId
    private UserDailyCalorieId userDailyCalorieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "daily_calorie")
    private Integer dailyCalorie;

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class UserDailyCalorieId implements Serializable {

        @Column(name = "date")
        private LocalDate date;

        @Column(name = "login_id")
        private String loginId;
    }
}
