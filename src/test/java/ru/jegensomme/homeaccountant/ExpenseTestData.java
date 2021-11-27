package ru.jegensomme.homeaccountant;

import lombok.experimental.UtilityClass;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.to.ExpenseTo;

import java.time.Month;

import static java.time.LocalDateTime.of;
import static ru.jegensomme.homeaccountant.CategoryTestData.*;
import static ru.jegensomme.homeaccountant.TestMatcher.usingEqualsComparator;
import static ru.jegensomme.homeaccountant.TestMatcher.usingIgnoringFieldsComparator;

@UtilityClass
public class ExpenseTestData {
    public static final TestMatcher<Expense> EXPENSE_MATCHER = usingIgnoringFieldsComparator(Expense.class, "user", "category");
    public static final TestMatcher<ExpenseTo> EXPENSE_TO_MATCHER = usingEqualsComparator(ExpenseTo.class);

    public static final int NOT_FOUND = 10;

    public static final int EXPENSE1_ID = 1;
    public static final int ADMIN_EXPENSE1_ID = EXPENSE1_ID + 6;

    public static final Expense EXPENSE1 = new Expense(EXPENSE1_ID, USER_FOOD, of(2021, Month.JANUARY, 30, 10, 0), "5000.00", "Лента");
    public static final Expense EXPENSE2 = new Expense(EXPENSE1_ID + 1, USER_FOOD, of(2021, Month.FEBRUARY, 2,  11, 0), "2000.00", "Пятеречка");
    public static final Expense EXPENSE3 = new Expense(EXPENSE1_ID + 2, USER_FOOD, of(2021, Month.FEBRUARY, 11, 12, 0), "5000.00", "Лента");
    public static final Expense EXPENSE4 = new Expense(EXPENSE1_ID + 3, USER_HOUSEHOLD, of(2021, Month.FEBRUARY, 26, 13, 0), "9000.00", "Максидом");
    public static final Expense EXPENSE5 = new Expense(EXPENSE1_ID + 4, USER_HOUSEHOLD, of(2021, Month.FEBRUARY, 26, 16, 0), "4000.00", "Домовой");
    public static final Expense EXPENSE6 = new Expense(EXPENSE1_ID + 5, of(2021, Month.FEBRUARY, 27, 14, 0), "8000.00", "Потерял");

    public static final Expense ADMIN_EXPENSE1 = new Expense(ADMIN_EXPENSE1_ID, ADMIN_FOOD, of(2021, Month.JANUARY,  30, 10, 0), "13000.00", "Лента");
    public static final Expense ADMIN_EXPENSE2 = new Expense(ADMIN_EXPENSE1_ID + 1, ADMIN_HOUSEHOLD, of(2021, Month.FEBRUARY, 15, 12, 0), "11000.00", "Максидом");
    public static final Expense ADMIN_EXPENSE3 = new Expense(ADMIN_EXPENSE1_ID + 2, of(2021, Month.FEBRUARY, 25, 14, 0), "7000.00", "Потерял");
}
