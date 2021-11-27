package ru.jegensomme.homeaccountant;

import lombok.experimental.UtilityClass;
import ru.jegensomme.homeaccountant.model.Role;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.util.JsonUtil;

import java.util.Collections;
import java.util.Currency;

import static ru.jegensomme.homeaccountant.TestMatcher.usingIgnoringFieldsComparator;

@UtilityClass
public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = usingIgnoringFieldsComparator(User.class, "registered", "password", "categories", "expenses");

    public static final int NOT_FOUND = 10;

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;

    public static final Currency RUB = Currency.getInstance("RUB");
    public static final Currency USD = Currency.getInstance("USD");

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", "30000.00", RUB, Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", "30000.00", USD, Role.USER, Role.ADMIN);

    public static User getNew() {
        return new User(null, "New", "new@mail.ru", "newPassword", "3000", RUB, Role.USER);
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("Updated");
        updated.setEmail("updated@yandex.ru");
        updated.setPassword("updatedPassword");
        updated.setMonthlyLimit("50000.00");
        updated.setCurrency(USD);
        updated.setRoles(Collections.singleton(Role.ADMIN));
        return updated;
    }

    public static String jsonWithPassword(Authenticatable user) {
        return jsonWithPassword(user, user.getPassword());
    }

    public static String jsonWithPassword(Authenticatable user, String password) {
        return JsonUtil.writeAdditionProps(user, "password", password);
    }
}
