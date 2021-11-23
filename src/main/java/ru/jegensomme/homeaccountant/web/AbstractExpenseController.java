package ru.jegensomme.homeaccountant.web;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.service.ExpenseService;
import ru.jegensomme.homeaccountant.to.ExpenseTo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.jegensomme.homeaccountant.util.ExpenseUtil.filter;
import static ru.jegensomme.homeaccountant.util.ExpenseUtil.getTos;
import static ru.jegensomme.homeaccountant.util.ValidationUtil.assureIdConsistent;
import static ru.jegensomme.homeaccountant.web.SecurityUtil.authUserId;

@RequiredArgsConstructor
public class AbstractExpenseController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final ExpenseService service;

    public @NonNull Expense create(ExpenseTo expenseTo) {
        int userId = authUserId();
        log.info("create from to {} for user {}", expenseTo, userId);
        return service.create(expenseTo, userId);
    }

    public @NonNull Expense create(Expense expense) {
        int userId = authUserId();
        log.info("create {} for user {}", expense, userId);
        return service.create(expense, userId);
    }

    public void delete(int id) {
        int userId = authUserId();
        log.info("delete {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public void update(ExpenseTo expenseTo, int id) {
        int userId = authUserId();
        assureIdConsistent(expenseTo, id);
        log.info("update from to {} for user {}", expenseTo, userId);
        service.update(expenseTo, userId);
    }

    public void update(Expense expense, int id) {
        int userId = authUserId();
        assureIdConsistent(expense, id);
        log.info("update {} for user {}", expense, userId);
        service.update(expense, userId);
    }

    public @NonNull Expense get(int id) {
        int userId = authUserId();
        log.info("get {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public List<ExpenseTo> getAll() {
        int userId = authUserId();
        log.info("getAll for user {}", userId);
        return getTos(service.getAll(userId));
    }

    public List<ExpenseTo> getWithoutCategory() {
        int userId = authUserId();
        log.info("getWithoutCategory for user {}", userId);
        return getTos(service.getWithoutCategory(userId));
    }

    /**
     * <ol>Filter separately
     * <li>by date</li>
     * <li>by time for every date</li>
     * </ol>
     */
    public List<ExpenseTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                      @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        return filter(service.getBetween(userId, startDate, endDate), startTime, endTime);
    }

    public List<ExpenseTo> getByCategory(String category) {
        int userId = authUserId();
        log.info("getByCategory {} for user {}", category, userId);
        return getTos(service.getByCategory(category, userId));
    }

    /**
     * @see #getBetween(LocalDate, LocalTime, LocalDate, LocalTime)
     */
    public List<ExpenseTo> getByCategoryBetween(String category,
                                              @Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                              @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getByCategory {} between dates({} - {}) time({} - {}) for user {}", category, startDate, endDate, startTime, endTime, userId);
        return filter(service.getByCategoryBetween(category, userId, startDate, endDate), startTime, endTime);
    }

    /**
     * @see #getBetween(LocalDate, LocalTime, LocalDate, LocalTime)
     */
    public List<ExpenseTo> getWithoutCategoryBetween(
            @Nullable LocalDate startDate, @Nullable LocalTime startTime,
            @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getWithoutCategoryBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        return filter(service.getWithoutCategoryBetween(userId, startDate, endDate), startTime, endTime);
    }

    public BigDecimal getTotalAmountForCurrentMonth() {
        int userId = SecurityUtil.authUserId();
        log.info("getTotalAmountForCurrentMonth for user {}", userId);
        return service.getTotalAmountForCurrentMonth(userId);
    }
}