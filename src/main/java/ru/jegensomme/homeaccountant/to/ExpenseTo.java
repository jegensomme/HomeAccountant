package ru.jegensomme.homeaccountant.to;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.lang.Nullable;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.util.converter.CurrencyConvertor;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.Currency;

@Getter
@EqualsAndHashCode(callSuper = true)
public final class ExpenseTo extends BaseTo {

    private final @Nullable Category category;

    private final LocalDateTime dateTime;

    private final int amount;

    @JsonSerialize(converter = CurrencyConvertor.JsonSerialiseConverter.class)
    @JsonDeserialize(converter = CurrencyConvertor.JsonDeserializeConverter.class)
    private final Currency currencyCode;

    private final @Nullable String description;

    @ConstructorProperties({"id", "category", "dateTime", "amount", "currency", "description"})
    public ExpenseTo(@Nullable Integer id,
                     @Nullable Category category,
                     LocalDateTime dateTime,
                     int amount,
                     Currency currencyCode,
                     @Nullable String description) {
        super(id);
        this.category = category;
        this.dateTime = dateTime;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.description = description;
    }

    @Override
    public String toString() {
        return "ExpenseTo{" +
                "id=" + id +
                "category=" + (category == null ? "" : category.getName()) +
                ", dateTime=" + dateTime +
                ", amount=" + amount +
                ", currencyCode=" + currencyCode +
                ", description='" + description + '\'' +
                '}';
    }
}
