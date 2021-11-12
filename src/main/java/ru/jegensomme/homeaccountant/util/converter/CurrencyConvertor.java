package ru.jegensomme.homeaccountant.util.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.springframework.format.Formatter;
import org.springframework.lang.NonNull;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Currency;
import java.util.Locale;

@Converter
public class CurrencyConvertor implements Formatter<Currency>, AttributeConverter<Currency, String> {
    @Override
    public String convertToDatabaseColumn(Currency currency) {
        return currency.getCurrencyCode();
    }

    @Override
    public Currency convertToEntityAttribute(String code) {
        return Currency.getInstance(code);
    }

    @Override
    public @NonNull Currency parse(@NonNull String code, @NonNull Locale locale) {
        return Currency.getInstance(code);
    }

    @Override
    public @NonNull String print(@NonNull Currency currency, @NonNull Locale locale) {
        return currency.getCurrencyCode();
    }

    public static class JsonSerialiseConverter extends StdConverter<Currency, String> {
        @Override
        public String convert(Currency currency) {
            return currency.getCurrencyCode();
        }
    }

    public static class JsonDeserializeConverter extends StdConverter<String, Currency> {
        @Override
        public Currency convert(String code) {
            return Currency.getInstance(code);
        }
    }
}
