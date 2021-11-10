package ru.jegensomme.homeaccountant.util;

import ru.jegensomme.homeaccountant.model.Role;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.to.UserTo;

import java.util.Currency;

public class UserUtil {
    public static final int DEFAULT_MONTHLY_LIMIT = 50000;

    public static final Currency DEFAULT_CURRENCY = Currency.getInstance("RUB");

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), userTo.getMonthlyLimit(), userTo.getDefaultCurrency(), Role.USER);
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getMonthlyLimit(), user.getDefaultCurrency());
    }

    public static void updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        user.setMonthlyLimit(userTo.getMonthlyLimit());
        user.setDefaultCurrency(userTo.getDefaultCurrency());
    }
}