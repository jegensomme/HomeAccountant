package ru.jegensomme.homeaccountant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.repository.ExpenseRepository;
import ru.jegensomme.homeaccountant.to.ExpenseTo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static ru.jegensomme.homeaccountant.util.DateTimeUtil.atStartOfDayOrMin;
import static ru.jegensomme.homeaccountant.util.DateTimeUtil.atStartOfNextDayOrMax;
import static ru.jegensomme.homeaccountant.util.ExpenseUtil.createNewFromTo;
import static ru.jegensomme.homeaccountant.util.ExpenseUtil.updateFromTo;
import static ru.jegensomme.homeaccountant.util.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository repository;
    private final CategoryService categoryService;

    public Expense create(Expense expense, int userId) {
        Assert.notNull(expense, "expense must not be null");
        return repository.save(expense, userId);
    }

    @Transactional
    public Expense create(ExpenseTo expenseTo, int userId) {
        Assert.notNull(expenseTo, "expense must not be null");
        Category category = StringUtils.hasText(expenseTo.getCategory()) ? categoryService.getByName(expenseTo.getCategory(), userId) : null;
        return repository.save(createNewFromTo(expenseTo, category), userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public void update(Expense expense, int userId) {
        Assert.notNull(expense, "expense must not be null");
        checkNotFoundWithId(repository.save(expense, userId), expense.id());
    }

    @Transactional
    public void update(ExpenseTo expenseTo, int userId) {
        Assert.notNull(expenseTo, "expense must not be null");
        Category category = StringUtils.hasText(expenseTo.getCategory()) ? categoryService.getByName(expenseTo.getCategory(), userId) : null;
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

    public List<Expense> getByCategory(String category, int userId) {
        return repository.getByCategory(category, userId);
    }

    public List<Expense> getByCategoryBetween(String category, int userId,
                                              @Nullable LocalDate startInclusive,
                                              @Nullable LocalDate endExclusive) {
        return repository.getByCategoryBetween(
                category, userId,
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

    public BigDecimal getTotalAmountForCurrentMonth(int userId) {
        return repository.getTotalAmountForCurrentMonth(userId);
    }
}
