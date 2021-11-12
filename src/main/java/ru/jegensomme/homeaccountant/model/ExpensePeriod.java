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
public class ExpensePeriod {
    private int number;

    @Enumerated(EnumType.STRING)
    private ChronoUnit unit;

    public ExpensePeriod(int number, ChronoUnit unit) {
        if (number <= 0) {
            throw new IllegalArgumentException("number must be > 0");
        }
        this.number = number;
        this.unit = unit;
    }

    @Embedded
    public static ExpensePeriod DAY = new ExpensePeriod(1, ChronoUnit.DAYS);
    @Embedded
    public static ExpensePeriod WEEK = new ExpensePeriod(1, ChronoUnit.WEEKS);
    @Embedded
    public static ExpensePeriod DECADE = new ExpensePeriod(1, ChronoUnit.DECADES);
    @Embedded
    public static ExpensePeriod MONTH = new ExpensePeriod(1, ChronoUnit.MONTHS);
    @Embedded
    public static ExpensePeriod HALF_YEAR = new ExpensePeriod(6, ChronoUnit.MONTHS);
    @Embedded
    public static ExpensePeriod YEAR = new ExpensePeriod(1, ChronoUnit.YEARS);
}
