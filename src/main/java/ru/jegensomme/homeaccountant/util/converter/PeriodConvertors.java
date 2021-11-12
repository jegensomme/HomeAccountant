package ru.jegensomme.homeaccountant.util.converter;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import ru.jegensomme.homeaccountant.model.ExpensePeriod;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

@UtilityClass
public class PeriodConvertors {

    public static class Formatter implements org.springframework.format.Formatter<ExpensePeriod> {
        @Override
        public @NonNull ExpensePeriod parse(String text, @NonNull Locale locale) throws ParseException {
            String[] args = text.split("\\$");
            return new ExpensePeriod(Integer.parseInt(args[0]), ChronoUnit.valueOf(args[1]));
        }

        @Override
        public @NonNull String print(@NonNull ExpensePeriod expensePeriod, @NonNull Locale locale) {
            return expensePeriod.getNumber() + "$" + expensePeriod.getUnit();
        }
    }
}
