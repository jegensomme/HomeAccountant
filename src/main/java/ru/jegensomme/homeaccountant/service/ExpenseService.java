package ru.jegensomme.homeaccountant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.repository.ExpenseRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static ru.jegensomme.homeaccountant.util.DateTimeUtil.atStartOfDayOrMin;
import static ru.jegensomme.homeaccountant.util.DateTimeUtil.atStartOfNextDayOrMax;
import static ru.jegensomme.homeaccountant.util.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository repository;

    public Expense create(Expense expense, int userId) {
        Assert.notNull(expense, "expense must not be null");
        return Objects.requireNonNull(repository.save(expense, userId));
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public void update(Expense expense, int userId) {
        Assert.notNull(expense, "expense must not be null");
        checkNotFoundWithId(repository.save(expense, userId), expense.id());
    }

    public Expense get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Expense> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<Expense> getWithoutCategory(int userId) {
        return repository.getWithoutCategory(userId);
    }

    public List<Expense> getBetween(int userId,
                                    @Nullable LocalDate startInclusive,
                                    @Nullable LocalDate endInclusive) {
        return repository.getBetween(userId,
                atStartOfDayOrMin(startInclusive),
                atStartOfNextDayOrMax(endInclusive));
    }

    public List<Expense> getByCategory(int categoryId, int userId) {
        return repository.getByCategory(categoryId, userId);
    }

    public List<Expense> getByCategoryBetween(int categoryId, int userId,
                                              @Nullable LocalDate startInclusive,
                                              @Nullable LocalDate endExclusive) {
        return repository.getByCategoryBetween(
                categoryId, userId,
                atStartOfDayOrMin(startInclusive),
                atStartOfNextDayOrMax(endExclusive));
    }
}
