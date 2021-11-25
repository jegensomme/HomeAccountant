package ru.jegensomme.homeaccountant.util.converter;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import ru.jegensomme.homeaccountant.model.Period;

import java.time.temporal.ChronoUnit;
import java.util.Locale;

@UtilityClass
public class PeriodConvertors {

    public static class Formatter implements org.springframework.format.Formatter<Period> {
        @Override
        public @NonNull Period parse(String text, @NonNull Locale locale) {
            String[] args = text.split("\\$");
            return new Period(Integer.parseInt(args[0]), ChronoUnit.valueOf(args[1]));
        }

        @Override
        public @NonNull String print(@NonNull Period period, @NonNull Locale locale) {
            return period.getNumber() + "$" + period.getUnit();
        }
    }
}
