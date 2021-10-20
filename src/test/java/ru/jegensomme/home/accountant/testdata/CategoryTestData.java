package ru.jegensomme.home.accountant.testdata;

import lombok.experimental.UtilityClass;
import ru.jegensomme.home.accountant.util.TestMatcher;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.model.ExpensePeriod;

import static ru.jegensomme.home.accountant.util.TestMatcher.usingIgnoringFieldsComparator;
import static ru.jegensomme.homeaccountant.model.BaseEntity.START_SEQ;

@UtilityClass
public class CategoryTestData {
    public static final TestMatcher<Category> CATEGORY_MATCHER = usingIgnoringFieldsComparator("user");

    public static final int NOT_FOUND = 10;

    public static final int USER_FOOD_ID = START_SEQ + 2;
    public static final int USER_HOUSEHOLD_ID = START_SEQ + 3;
    public static final int ADMIN_FOOD_ID = START_SEQ + 4;
    public static final int ADMIN_HOUSEHOLD_ID = START_SEQ + 5;

    public static final Category USER_FOOD = new Category(USER_FOOD_ID, "Food", 120000, ExpensePeriod.MONTH);
    public static final Category USER_HOUSEHOLD = new Category(USER_HOUSEHOLD_ID, "Household", 10000, ExpensePeriod.MONTH);
    public static final Category ADMIN_FOOD = new Category(ADMIN_FOOD_ID, "Food", 10000, ExpensePeriod.MONTH);
    public static final Category ADMIN_HOUSEHOLD = new Category(ADMIN_HOUSEHOLD_ID, "Household", 8000, ExpensePeriod.MONTH);

    public static Category getNew() {
        return new Category(null, "New");
    }

    public static Category getUpdated() {
        return new Category(USER_FOOD_ID, "Updated", 10000, ExpensePeriod.WEEK);
    }
}
