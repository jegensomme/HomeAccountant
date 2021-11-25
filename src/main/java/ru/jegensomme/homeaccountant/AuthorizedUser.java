package ru.jegensomme.homeaccountant;

import org.springframework.lang.NonNull;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.to.UserTo;
import ru.jegensomme.homeaccountant.util.UserUtil;

import java.io.Serial;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    @Serial
    private static final long serialVersionUID = 1L;

    private @NonNull UserTo userTo;

    public AuthorizedUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = UserUtil.asToExceptPassword(user);
    }

    public int getId() {
        return userTo.id();
    }

    public void update(@NonNull UserTo newTo) {
        userTo = UserUtil.exceptPassword(newTo);
    }

    public @NonNull UserTo getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}