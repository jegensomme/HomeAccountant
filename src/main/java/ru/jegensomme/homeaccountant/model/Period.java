package ru.jegensomme.homeaccountant.model;

import lombok.*;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
@Embeddable
public class Period {
    private int number;

    @Enumerated(EnumType.STRING)
    private ChronoUnit unit;

    public Period(int number, ChronoUnit unit) {
        if (number <= 0) {
            throw new IllegalArgumentException("number must be > 0");
        }
        this.number = number;
        this.unit = unit;
    }

    @Embedded
    public static Period DAY = new Period(1, ChronoUnit.DAYS);
    @Embedded
    public static Period WEEK = new Period(1, ChronoUnit.WEEKS);
    @Embedded
    public static Period DECADE = new Period(1, ChronoUnit.DECADES);
    @Embedded
    public static Period MONTH = new Period(1, ChronoUnit.MONTHS);
    @Embedded
    public static Period HALF_YEAR = new Period(6, ChronoUnit.MONTHS);
    @Embedded
    public static Period YEAR = new Period(1, ChronoUnit.YEARS);
}
