package ru.jegensomme.homeaccountant.util.converter;

import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import static ru.jegensomme.homeaccountant.util.DateTimeUtil.*;

public class DateTimeFormatters {
    public static class LocalDateFormatter implements Formatter<LocalDate> {

        @Override
        public @NonNull LocalDate parse(@NonNull String text, @NonNull Locale locale) {
            return Objects.requireNonNullElse(parseLocalDate(text, locale), MIN_DATE.toLocalDate());
        }

        @Override
        public @NonNull String print(@NonNull LocalDate lt, @NonNull Locale locale) {
            return lt.format(DateTimeFormatter.ISO_LOCAL_DATE.withLocale(locale));
        }
    }

    public static class LocalTimeFormatter implements Formatter<LocalTime> {

        @Override
        public @NonNull LocalTime parse(@NonNull String text, @NonNull Locale locale) {
            return Objects.requireNonNullElse(parseLocalTime(text, locale), LocalTime.MIN);
        }

        @Override
        public @NonNull String print(@NonNull LocalTime lt, @NonNull Locale locale) {
            return lt.format(DateTimeFormatter.ISO_LOCAL_TIME.withLocale(locale));
        }
    }
}
