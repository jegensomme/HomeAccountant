package ru.jegensomme.homeaccountant.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import ru.jegensomme.homeaccountant.model.Role;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.to.UserTo;

import java.util.Currency;

public class UserUtil {
    public static final Currency DEFAULT_CURRENCY = Currency.getInstance("RUB");

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), userTo.getMonthlyLimit(), userTo.getCurrency(), Role.USER);
    }

    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getMonthlyLimit(), user.getCurrency());
    }

    public static UserTo asToExceptPassword(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), "", user.getMonthlyLimit(), user.getCurrency());
    }

    public static UserTo exceptPassword(UserTo userTo) {
        return new UserTo(userTo.getId(), userTo.getName(), userTo.getEmail(), "", userTo.getMonthlyLimit(), userTo.getCurrency());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        user.setMonthlyLimit(userTo.getMonthlyLimit());
        user.setCurrency(userTo.getCurrency());
        return user;
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.hasText(password) ? passwordEncoder.encode(password) : password);
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}