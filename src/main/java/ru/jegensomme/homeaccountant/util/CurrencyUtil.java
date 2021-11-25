package ru.jegensomme.homeaccountant.util;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static java.util.Currency.getInstance;

@UtilityClass
public class CurrencyUtil {
    public static final List<Currency> SUPPORTED_CURRENCIES = List.of(
            getInstance("RUB"), getInstance("USD"), getInstance("EUR"),
            getInstance("BYN"), getInstance("UAH"), getInstance("KZT"),
            getInstance("GBP"), getInstance("DEM"), getInstance("PLN"),
            getInstance("XAU"), getInstance("XAG"), getInstance("XPT")
    );

    public static String getDisplayName(@NonNull Currency currency, @NonNull Locale locale) {
        return currency.getCurrencyCode() + " " + currency.getDisplayName(locale);
    }
}
