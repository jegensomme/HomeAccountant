package ru.jegensomme.homeaccountant.model;

import org.jetbrains.annotations.NotNull;

import java.time.temporal.ChronoUnit;

public record ExpensePeriod(int number, @NotNull ChronoUnit unit) {
    public ExpensePeriod {
        if (number <= 0) {
            throw new IllegalArgumentException("number must be > 0");
        }
    }

    public static ExpensePeriod DAY = new ExpensePeriod(1, ChronoUnit.DAYS);
    public static ExpensePeriod WEEK = new ExpensePeriod(1, ChronoUnit.WEEKS);
    public static ExpensePeriod DECADE = new ExpensePeriod(1, ChronoUnit.DECADES);
    public static ExpensePeriod MONTH = new ExpensePeriod(1, ChronoUnit.MONTHS);
    public static ExpensePeriod YEAR = new ExpensePeriod(1, ChronoUnit.YEARS);
}
