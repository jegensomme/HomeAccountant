package ru.jegensomme.homeaccountant.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.jegensomme.homeaccountant.model.Expense;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository {
    @Nullable Expense save(@NotNull Expense expense, int userId);

    boolean delete(int id, int userId);

    @Nullable Expense get(int id, int userId);

    List<Expense> getAll(int userId);

    List<Expense> getWithoutCategory(int userId);

    List<Expense> getBetween(int userId, @NotNull LocalDateTime startInclusive, @NotNull LocalDateTime endExclusive);

    List<Expense> getByCategory(int categoryId, int userId);

    List<Expense> getByCategoryBetween(int categoryId, int userId,
                                       @NotNull LocalDateTime startInclusive, @NotNull LocalDateTime endExclusive);
}
