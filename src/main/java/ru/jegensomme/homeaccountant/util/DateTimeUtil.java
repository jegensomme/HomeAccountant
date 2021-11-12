package ru.jegensomme.homeaccountant.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.*;

public class DateTimeUtil {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    // DB doesn't support LocalDate.MIN/MAX
    public static final LocalDateTime MIN_DATE = LocalDateTime.of(1, 1, 1, 0, 0);
    public static final LocalDateTime MAX_DATE = LocalDateTime.of(3000, 1, 1, 0, 0);

    private DateTimeUtil() {
    }

    public static LocalDateTime atStartOfDayOrMin(@Nullable LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay() : MIN_DATE;
    }

    public static LocalDateTime atStartOfNextDayOrMax(@Nullable LocalDate localDate) {
        return localDate != null ? localDate.plus(1, ChronoUnit.DAYS).atStartOfDay() : MAX_DATE;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static @Nullable LocalDate parseLocalDate(@Nullable String str, Locale locale) {
        return parseLocalDate(str, ISO_LOCAL_DATE.withLocale(locale));
    }

    public static @Nullable LocalTime parseLocalTime(@Nullable String str, Locale locale) {
        return parseLocalTime(str, ISO_LOCAL_TIME.withLocale(locale));
    }

    public static @Nullable LocalDate parseLocalDate(@Nullable String str, DateTimeFormatter formatter) {
        return !StringUtils.hasText(str) ? null : LocalDate.parse(str, formatter);
    }

    public static @Nullable LocalTime parseLocalTime(@Nullable String str, DateTimeFormatter formatter) {
        return !StringUtils.hasText(str) ? null : LocalTime.parse(str, formatter);
    }
}
