package ru.jegensomme.homeaccountant.to;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.Nullable;
import ru.jegensomme.homeaccountant.util.converter.CurrencyConvertor;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
@EqualsAndHashCode(callSuper = true)
public final class ExpenseTo extends BaseTo {

    private final LocalDateTime dateTime;

    private final int amount;

    @JsonSerialize(converter = CurrencyConvertor.JsonSerialiseConverter.class)
    @JsonDeserialize(converter = CurrencyConvertor.JsonDeserializeConverter.class)
    private final Currency currencyCode;

    private final @Nullable String description;

    @ConstructorProperties({"id", "dateTime", "amount", "currency", "description"})
    public ExpenseTo(@Nullable Integer id, LocalDateTime dateTime, int amount, Currency currencyCode, @Nullable String description) {
        super(id);
        this.dateTime = dateTime;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.description = description;
    }
}
