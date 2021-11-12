package ru.jegensomme.homeaccountant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.repository.CategoryRepository;
import ru.jegensomme.homeaccountant.repository.ExpenseRepository;
import ru.jegensomme.homeaccountant.to.ExpenseEditTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static ru.jegensomme.homeaccountant.util.DateTimeUtil.atStartOfDayOrMin;
import static ru.jegensomme.homeaccountant.util.DateTimeUtil.atStartOfNextDayOrMax;
import static ru.jegensomme.homeaccountant.util.ExpenseUtil.createNewFromTo;
import static ru.jegensomme.homeaccountant.util.ExpenseUtil.updateFromTo;
import static ru.jegensomme.homeaccountant.util.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository repository;
    private final CategoryRepository categoryRepository;

    public Expense create(Expense expense, int userId) {
        Assert.notNull(expense, "expense must not be null");
        return Objects.requireNonNull(repository.save(expense, userId));
    }

    @Transactional
    public Expense create(ExpenseEditTo expenseTo, int userId) {
        Assert.notNull(expenseTo, "expense must not be null");
        Category category = expenseTo.getCategory() > 0 ? categoryRepository.get(expenseTo.getCategory(), userId) : null;
        return create(createNewFromTo(expenseTo, category), userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public void update(Expense expense, int userId) {
        Assert.notNull(expense, "expense must not be null");
        checkNotFoundWithId(repository.save(expense, userId), expense.id());
    }

    @Transactional
    public void update(ExpenseEditTo expenseTo, int userId) {
        Assert.notNull(expenseTo, "expense must not be null");
        Category category = expenseTo.getCategory() > 0 ? categoryRepository.get(expenseTo.getCategory(), userId) : null;
        Expense expense = get(expenseTo.id(), userId);
        updateFromTo(expense, expenseTo, category);
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

    public List<Expense> getWithoutCategoryBetween(int userId,
                                                   @Nullable LocalDate startInclusive,
                                                   @Nullable LocalDate endExclusive) {
        return repository.getWithoutCategoryBetween(userId,
                atStartOfDayOrMin(startInclusive),
                atStartOfNextDayOrMax(endExclusive));
    }
}
