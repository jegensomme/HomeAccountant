package ru.jegensomme.homeaccountant;

import lombok.experimental.UtilityClass;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.model.Period;

import java.math.BigDecimal;

import static ru.jegensomme.homeaccountant.TestMatcher.usingIgnoringFieldsComparator;

@UtilityClass
public class CategoryTestData {
    public static final TestMatcher<Category> CATEGORY_MATCHER = usingIgnoringFieldsComparator(Category.class, "user", "expenses");

    public static final int NOT_FOUND = 10;

    public static final int USER_FOOD_ID = 1;
    public static final int USER_HOUSEHOLD_ID = 2;
    public static final int ADMIN_FOOD_ID = 3;
    public static final int ADMIN_HOUSEHOLD_ID = 4;

    public static final Category USER_FOOD = new Category(USER_FOOD_ID, "Food", new BigDecimal("12000.00"), Period.MONTH);
    public static final Category USER_HOUSEHOLD = new Category(USER_HOUSEHOLD_ID, "Household", new BigDecimal("10000.00"), Period.MONTH);
    public static final Category ADMIN_FOOD = new Category(ADMIN_FOOD_ID, "Food", new BigDecimal("10000.00"), Period.MONTH);
    public static final Category ADMIN_HOUSEHOLD = new Category(ADMIN_HOUSEHOLD_ID, "Household", new BigDecimal("8000.00"), Period.MONTH);

    public static Category getNew() {
        return new Category(null, "New");
    }

    public static Category getUpdated() {
        return new Category(USER_FOOD_ID, "Updated", new BigDecimal("10000.00"), Period.WEEK);
    }
}
