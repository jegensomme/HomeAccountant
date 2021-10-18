package ru.jegensomme.homeaccountant.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Expense extends BaseEntity {
    private @NotNull User user;

    private @NotNull Category category;

    private @NotNull LocalDateTime dateTime;

    private int amount;

    private @Nullable String description;

    public Expense(@NotNull Integer id,
                   @NotNull User user,
                   @NotNull Category category,
                   @NotNull LocalDateTime dateTime,
                   int amount,
                   @Nullable String description) {
        super(id);
        this.user = user;
        this.category = category;
        this.dateTime = dateTime;
        this.amount = amount;
        this.description = description;
    }
}
