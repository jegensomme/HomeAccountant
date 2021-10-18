package ru.jegensomme.homeaccountant.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Expense extends BaseEntity {
    private @NotNull User user;

    private @NotNull ExpenseCategory category;

    private @NotNull LocalDateTime dateTime;

    private int amount;

    public Expense(@NotNull Integer id,
                   @NotNull User user,
                   @NotNull ExpenseCategory category,
                   @NotNull LocalDateTime dateTime,
                   int amount) {
        super(id);
        this.user = user;
        this.category = category;
        this.dateTime = dateTime;
        this.amount = amount;
    }
}
