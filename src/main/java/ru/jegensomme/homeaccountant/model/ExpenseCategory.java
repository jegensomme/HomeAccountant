package ru.jegensomme.homeaccountant.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ExpenseCategory extends NamedEntity {
    private @NotNull User user;

    private @Nullable Integer limit;

    private @Nullable ExpensePeriod period;

    public ExpenseCategory(@NotNull Integer id, @NotNull String name, @NotNull User user) {
        this(id, name, user, null, null);
    }

    public ExpenseCategory(@NotNull Integer id,
                           @NotNull String name,
                           @NotNull User user,
                           @Nullable Integer limit,
                           @Nullable ExpensePeriod period) {
        super(id, name);
        this.user = user;
        if (limit == null && period != null || limit != null && period == null) {
            throw new IllegalArgumentException("Limit and period must be both null or not null");
        }
        this.limit = limit;
        this.period = period;
    }
}
