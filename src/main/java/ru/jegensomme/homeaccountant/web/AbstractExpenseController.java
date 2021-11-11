package ru.jegensomme.homeaccountant.web;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.service.ExpenseService;
import ru.jegensomme.homeaccountant.to.ExpenseTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.jegensomme.homeaccountant.util.ExpenseUtil.getFilteredTos;
import static ru.jegensomme.homeaccountant.util.ExpenseUtil.getTos;
import static ru.jegensomme.homeaccountant.util.ValidationUtil.assureIdConsistent;
import static ru.jegensomme.homeaccountant.web.SecurityUtil.authUserCurrency;
import static ru.jegensomme.homeaccountant.web.SecurityUtil.authUserId;

@RequiredArgsConstructor
public class AbstractExpenseController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final ExpenseService service;

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
        return getTos(service.getAll(userId), authUserCurrency());
    }

    public List<ExpenseTo> getWithoutCategory() {
        int userId = authUserId();
        log.info("getWithoutCategory for user {}", userId);
        return getTos(service.getWithoutCategory(userId), authUserCurrency());
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
        return getFilteredTos(service.getBetween(userId, startDate, endDate), startTime, endTime, authUserCurrency());
    }

    public List<ExpenseTo> getByCategory(int categoryId) {
        int userId = authUserId();
        log.info("getByCategory {} for user {}", categoryId, userId);
        return getTos(service.getByCategory(categoryId, userId), authUserCurrency());
    }

    /**
     * @see #getBetween(LocalDate, LocalTime, LocalDate, LocalTime)
     */
    public List<ExpenseTo> getByCategoryBetween(int categoryId,
                                                @Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                                @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getByCategory {} between dates({} - {}) time({} - {}) for user {}", categoryId, startDate, endDate, startTime, endTime, userId);
        return getFilteredTos(service.getByCategoryBetween(categoryId, userId, startDate, endDate), startTime, endTime, authUserCurrency());
    }

    /**
     * @see #getBetween(LocalDate, LocalTime, LocalDate, LocalTime)
     */
    public List<ExpenseTo> getWithoutCategoryBetween(
            @Nullable LocalDate startDate, @Nullable LocalTime startTime,
            @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getWithoutCategoryBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        return getFilteredTos(service.getWithoutCategoryBetween(userId, startDate, endDate), startTime, endTime, authUserCurrency());
    }
}