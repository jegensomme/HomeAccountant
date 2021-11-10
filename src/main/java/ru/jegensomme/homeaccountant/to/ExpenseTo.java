package ru.jegensomme.homeaccountant.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.Nullable;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public final class ExpenseTo extends BaseTo {
    private final LocalDateTime dateTime;
    private final int amount;
    private final Currency currency;
    private final @Nullable String description;

    @ConstructorProperties({"id", "dateTime", "amount", "currency", "description"})
    public ExpenseTo(@Nullable Integer id, LocalDateTime dateTime, int amount, Currency currency, @Nullable String description) {
        super(id);
        this.dateTime = dateTime;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
    }
}
