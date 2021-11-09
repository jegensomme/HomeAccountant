package ru.jegensomme.homeaccountant.testdata;

import lombok.experimental.UtilityClass;
import ru.jegensomme.homeaccountant.util.TestMatcher;
import ru.jegensomme.homeaccountant.model.Role;
import ru.jegensomme.homeaccountant.model.User;

import java.util.Collections;
import java.util.Currency;

import static ru.jegensomme.homeaccountant.util.TestMatcher.usingIgnoringFieldsComparator;
import static ru.jegensomme.homeaccountant.model.BaseEntity.START_SEQ;

@UtilityClass
public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = usingIgnoringFieldsComparator(User.class, "registered");

    public static final int NOT_FOUND = 10;

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final Currency RUB = Currency.getInstance("RUB");
    public static final Currency USD = Currency.getInstance("USD");

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", true, 30000, RUB, Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", true, 30000, USD, Role.USER, Role.ADMIN);

    public static User getNew() {
        return new User(null, "New", "new@mail.ru", "newPassword", true, 3000, RUB, Role.USER);
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("Updated");
        updated.setEmail("updated@yandex.ru");
        updated.setPassword("updatedPassword");
        updated.setMonthlyLimit(50000);
        updated.setDefaultCurrency(USD);
        updated.setRoles(Collections.singleton(Role.ADMIN));
        return updated;
    }

    public static User getDuplicateEmail() {
        return new User(null, "Duplicate", "user@yandex.ru", "newPass", true, RUB, Role.USER);
    }
}
