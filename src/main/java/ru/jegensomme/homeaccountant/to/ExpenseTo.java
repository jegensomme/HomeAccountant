package ru.jegensomme.homeaccountant.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.jegensomme.homeaccountant.model.Expense;

import java.time.LocalDateTime;

public record ExpenseTo(
        @JsonProperty Integer id,
        @JsonProperty LocalDateTime dateTime,
        @JsonProperty int amount,
        @JsonProperty String description
) {
    public static ExpenseTo of(Expense expense) {
        return new ExpenseTo(expense.getId(), expense.getDateTime(), expense.getAmount(), expense.getDescription());
    }
}
