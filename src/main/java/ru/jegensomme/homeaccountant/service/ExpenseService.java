package ru.jegensomme.homeaccountant.service;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.repository.ExpenseRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.jegensomme.homeaccountant.util.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class ExpenseService {
    private ExpenseRepository repository;

    public Expense create(Expense expense, int userId) {
        Assert.notNull(expense, "expense must not be null");
        return repository.save(expense, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public void update(Expense expense, int userId) {
        Assert.notNull(expense, "expense must not be null");
        checkNotFoundWithId(repository.save(expense, userId), expense.id());
    }

    public @NotNull Expense get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Expense> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<Expense> getBetween(int userId, @NotNull LocalDateTime startInclusive, @NotNull LocalDateTime endExclusive) {
        return repository.getBetween(userId, startInclusive, endExclusive);
    }

    public List<Expense> getByCategory(int userId, int categoryId) {
        return repository.getByCategory(userId, categoryId);
    }

    public List<Expense> getByCategoryBetween(int userId, int categoryId,
                                              @NotNull LocalDateTime startInclusive, @NotNull LocalDateTime endExclusive) {
        return repository.getByCategoryBetween(userId, categoryId, startInclusive, endExclusive);
    }
}