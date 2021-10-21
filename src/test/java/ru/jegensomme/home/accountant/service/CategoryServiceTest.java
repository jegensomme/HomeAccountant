package ru.jegensomme.home.accountant.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jegensomme.homeaccountant.model.*;
import ru.jegensomme.homeaccountant.service.CategoryService;
import ru.jegensomme.homeaccountant.service.ExpenseService;
import ru.jegensomme.homeaccountant.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static ru.jegensomme.home.accountant.testdata.CategoryTestData.*;
import static ru.jegensomme.home.accountant.testdata.UserTestData.ADMIN_ID;
import static ru.jegensomme.home.accountant.testdata.UserTestData.USER_ID;

public class CategoryServiceTest extends ServiceTestBase {
    @Autowired
    private CategoryService service;

    @Autowired
    private ExpenseService expenseService;

    @Before
    public void setUp() {
        Objects.requireNonNull(cacheManager.getCache("categories")).clear();
    }

    @Test
    public void create() {
        Category created = service.create(getNew(), USER_ID);
        int newId = created.id();
        Category newCategory = getNew();
        newCategory.setId(newId);
        CATEGORY_MATCHER.assertMatch(created, newCategory);
        CATEGORY_MATCHER.assertMatch(service.get(newId, USER_ID), newCategory);
    }

    @Test
    public void delete() {
        List<Expense> expenses = expenseService.getByCategory(USER_FOOD_ID, USER_ID);
        service.delete(USER_FOOD_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_FOOD_ID, USER_ID));
        assertTrue(expenseService.getWithoutCategory(USER_ID).containsAll(expenses));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_FOOD_ID, ADMIN_ID));
    }

    @Test
    public void get() {
        CATEGORY_MATCHER.assertMatch(service.get(USER_FOOD_ID, USER_ID), USER_FOOD);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_FOOD_ID, ADMIN_ID));
    }

    @Test
    public void update() {
        service.update(getUpdated(), USER_ID);
        CATEGORY_MATCHER.assertMatch(service.get(USER_FOOD_ID, USER_ID), getUpdated());
    }

    @Test
    public void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdated(), ADMIN_ID));
    }

    @Test
    public void getAll() {
        CATEGORY_MATCHER.assertMatch(service.getAll(USER_ID), USER_FOOD, USER_HOUSEHOLD);
    }

    @Test
    public void createWithException() {
        validateRootCause(() -> service.create(new Category(null, "   "), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Category(null, "Category", -10, ExpensePeriod.MONTH), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Category(null, "Category", null, ExpensePeriod.MONTH), USER_ID), IllegalArgumentException.class);
        validateRootCause(() -> service.create(new Category(null, "Category", 10000, null), USER_ID), IllegalArgumentException.class);
    }
}
