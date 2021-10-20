package ru.jegensomme.home.accountant.testdata;

import lombok.experimental.UtilityClass;
import ru.jegensomme.home.accountant.util.TestMatcher;
import ru.jegensomme.homeaccountant.model.Role;
import ru.jegensomme.homeaccountant.model.User;

import java.util.Collections;

import static ru.jegensomme.home.accountant.util.TestMatcher.usingIgnoringFieldsComparator;
import static ru.jegensomme.homeaccountant.model.BaseEntity.START_SEQ;

@UtilityClass
public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = usingIgnoringFieldsComparator("registered");

    public static final int NOT_FOUND = 10;

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", 30000, Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", 30000, Role.ADMIN);

    public static User getNew() {
        return new User(null, "New", "new@mail.ru", "newPassword", 3000, Role.USER);
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("Updated");
        updated.setEmail("updated@yandex.ru");
        updated.setPassword("updatedPassword");
        updated.setMonthlyLimit(50000);
        updated.setRoles(Collections.singleton(Role.ADMIN));
        return updated;
    }

    public static User getDuplicateEmail() {
        return new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.USER);
    }
}
