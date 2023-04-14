package com.example.test_work_4.enums;

import lombok.Getter;

import java.time.Duration;
@Getter
public enum ExpirationTime {
    MINUTES_10(Duration.ofMinutes(10)),
    HOUR_1(Duration.ofHours(1)),
    HOURS_3(Duration.ofHours(3)),
    DAY_1(Duration.ofDays(1)),
    WEEK_1(Duration.ofDays(7)),
    MONTH_1(Duration.ofDays(30)),
    UNLIMITED(Duration.ofDays(2000000));
    private final Duration duration;

    ExpirationTime(Duration duration) {
        this.duration = duration;
    }
}


