package ru.jegensomme.homeaccountant.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.repository.ExpenseRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseRepository {
    private final CrudUserRepository crudUserRepository;
    private final CrudExpenseRepository crudRepository;

    @Transactional
    @Override
    public Expense save(@NonNull Expense expense, int userId) {
        if (!expense.isNew() && get(expense.id(), userId) == null) {
            return null;
        }
        expense.setUser(crudUserRepository.getById(userId));
        return crudRepository.save(expense);
    }

    @Transactional
    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) > 0;
    }

    @Override
    public @Nullable Expense get(int id, int userId) {
        return crudRepository.findById(id)
                .filter(e -> {
                    assert e.getUser() != null;
                    return Objects.equals(e.getUser().getId(), userId);
                })
                .orElse(null);
    }

    @Override
    public List<Expense> getAll(int userId) {
        return crudRepository.getAll(userId);
    }

    @Override
    public List<Expense> getWithoutCategory(int userId) {
        return crudRepository.getWithoutCategory(userId);
    }

    @Override
    public List<Expense> getBetween(int userId,
                                    @NonNull LocalDateTime startInclusive,
                                    @NonNull LocalDateTime endExclusive) {
        return crudRepository.getBetween(userId, startInclusive, endExclusive);
    }

    @Override
    public List<Expense> getByCategory(String category, int userId) {
        return crudRepository.getByCategory(category, userId);
    }

    @Override
    public List<Expense> getByCategoryBetween(String category, int userId,
                                              @NonNull LocalDateTime startInclusive,
                                              @NonNull LocalDateTime endExclusive) {
        return crudRepository.getByCategoryBetween(category, userId, startInclusive, endExclusive);
    }

    @Override
    public List<Expense> getWithoutCategoryBetween(int userId,
                                                   @NonNull LocalDateTime startInclusive,
                                                   @NonNull LocalDateTime endExclusive) {
        return crudRepository.getWithoutCategoryBetween(userId, startInclusive, endExclusive);
    }

    @Override
    public BigDecimal getTotalAmountForCurrentMonth(int userId) {
        return crudRepository.getTotalAmountForCurrentMonth(userId);
    }
}
