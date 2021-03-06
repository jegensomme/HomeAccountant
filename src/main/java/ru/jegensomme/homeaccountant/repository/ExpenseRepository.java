package ru.jegensomme.homeaccountant.repository;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.jegensomme.homeaccountant.model.Expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository {
    Expense save(@NonNull Expense expense, int userId);

    boolean delete(int id, int userId);

    @Nullable Expense get(int id, int userId);

    List<Expense> getAll(int userId);

    List<Expense> getWithoutCategory(int userId);

    List<Expense> getBetween(int userId,
                             @NonNull LocalDateTime startInclusive,
                             @NonNull LocalDateTime endExclusive);

    List<Expense> getByCategory(String category, int userId);

    List<Expense> getByCategoryBetween(String category, int userId,
                                       @NonNull LocalDateTime startInclusive,
                                       @NonNull LocalDateTime endExclusive);

    List<Expense> getWithoutCategoryBetween(int userId,
                                            @NonNull LocalDateTime startInclusive,
                                            @NonNull LocalDateTime endExclusive);

    BigDecimal getTotalAmountForCurrentMonth(int userId);
}
