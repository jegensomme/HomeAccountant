package ru.jegensomme.home.accountant.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.service.ExpenseService;
import ru.jegensomme.homeaccountant.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertThrows;
import static ru.jegensomme.home.accountant.testdata.ExpenseTestData.*;
import static ru.jegensomme.home.accountant.testdata.UserTestData.ADMIN_ID;
import static ru.jegensomme.home.accountant.testdata.UserTestData.USER_ID;
import static ru.jegensomme.home.accountant.testdata.CategoryTestData.USER_FOOD_ID;

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
                service.getBetween(USER_ID, LocalDate.of(2021, Month.JANUARY, 30), LocalDate.of(2021, Month.FEBRUARY, 25)),
                EXPENSE3, EXPENSE2, EXPENSE1
        );
    }

    @Test
    public void getByCategory() {
        EXPENSE_MATCHER.assertMatch(service.getByCategory(USER_FOOD_ID, USER_ID), EXPENSE3, EXPENSE2, EXPENSE1);
    }

    @Test
    public void getByCategoryBetween() {
        EXPENSE_MATCHER.assertMatch(
                service.getByCategoryBetween(USER_FOOD_ID, USER_ID, LocalDate.of(2021, Month.JANUARY, 30), LocalDate.of(2021, Month.FEBRUARY, 10)),
                EXPENSE2, EXPENSE1
        );
    }

    @Test
    public void createWithException() {
        validateRootCause(() -> service.create(new Expense(null, null, 10000, null), USER_ID), IllegalArgumentException.class);
        validateRootCause(() -> service.create(new Expense(null, LocalDateTime.now(), -10, null), USER_ID), ConstraintViolationException.class);
    }
}
