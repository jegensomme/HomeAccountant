package ru.jegensomme.homeaccountant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.jegensomme.homeaccountant.testdata.CategoryTestData.USER_FOOD;
import static ru.jegensomme.homeaccountant.testdata.ExpenseTestData.*;
import static ru.jegensomme.homeaccountant.testdata.UserTestData.ADMIN_ID;
import static ru.jegensomme.homeaccountant.testdata.UserTestData.USER_ID;

public class ExpenseServiceTest extends ServiceTestBase {
    @Autowired
    private ExpenseService service;

    @Test
    public void create() {
        Expense created = service.create(getNew(), USER_ID);
        int newId = created.id();
        Expense newExpense = getNew();
        newExpense.setId(newId);
        EXPENSE_MATCHER.assertMatch(created, newExpense);
        EXPENSE_MATCHER.assertMatch(service.get(newId, USER_ID), newExpense);
    }

    @Test
    public void duplicateDateTimeCreate() {
        Expense newExpense = new Expense(null, EXPENSE1.getDateTime(), 1000, "new");
        assertThrows(DataAccessException.class, () -> service.create(newExpense, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(EXPENSE1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(EXPENSE1_ID, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(EXPENSE1_ID, ADMIN_ID));
    }

    @Test
    public void get() {
        EXPENSE_MATCHER.assertMatch(service.get(EXPENSE1_ID, USER_ID), EXPENSE1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(EXPENSE1_ID, ADMIN_ID));
    }

    @Test
    public void update() {
        service.update(getUpdated(), USER_ID);
        EXPENSE_MATCHER.assertMatch(service.get(EXPENSE1_ID, USER_ID), getUpdated());
    }

    @Test
    public void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(), ADMIN_ID));
    }

    @Test
    public void getAll() {
        EXPENSE_MATCHER.assertMatch(service.getAll(USER_ID), EXPENSE5, EXPENSE4, EXPENSE3, EXPENSE2, EXPENSE1);
    }

    @Test
    public void getBetween() {
        EXPENSE_MATCHER.assertMatch(
                service.getBetween(USER_ID,
                        LocalDate.of(2021, Month.JANUARY, 30),
                        LocalDate.of(2021, Month.FEBRUARY, 26)),
                EXPENSE4, EXPENSE3, EXPENSE2, EXPENSE1
        );
    }

    @Test
    public void getByCategory() {
        EXPENSE_MATCHER.assertMatch(service.getByCategory(USER_FOOD.getName(), USER_ID), EXPENSE3, EXPENSE2, EXPENSE1);
    }

    @Test
    public void getWithoutCategory() {
        EXPENSE_MATCHER.assertMatch(service.getWithoutCategory(USER_ID), EXPENSE5);
    }

    @Test
    public void getByCategoryBetween() {
        EXPENSE_MATCHER.assertMatch(
                service.getByCategoryBetween(USER_FOOD.getName(), USER_ID,
                        LocalDate.of(2021, Month.FEBRUARY, 1),
                        LocalDate.of(2021, Month.FEBRUARY, 26)),
                EXPENSE3, EXPENSE2
        );
    }

    @Test
    public void getWithoutCategoryBetween() {
        EXPENSE_MATCHER.assertMatch(
                service.getWithoutCategoryBetween(USER_ID,
                        LocalDate.of(2021, Month.FEBRUARY, 1),
                        LocalDate.of(2021, Month.FEBRUARY, 27)), EXPENSE5
        );
    }

    @Test
    public void testTotalAmountForCurrentMonth() {
        LocalDate now = LocalDate.now();
        LocalDateTime start = LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0);
        service.create(new Expense(null, start, 1000, "new"), USER_ID);
        service.create(new Expense(null, start.plusDays(1), 2000, "new"), USER_ID);
        service.create(new Expense(null, start.plusDays(2), 3000, "new"), USER_ID);
        assertEquals(BigDecimal.valueOf(6000.), service.getTotalAmountForCurrentMonth(USER_ID));
    }

    @Test
    public void createWithException() {
        validateRootCause(() -> service.create(new Expense(null, LocalDateTime.now(), -10, "New"), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Expense(null, LocalDateTime.now(), 10000, ""), USER_ID), ConstraintViolationException.class);
    }
}
