package ru.jegensomme.homeaccountant.web;

import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import ru.jegensomme.homeaccountant.model.User;

@Getter
@ToString(of = "user")
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public AuthorizedUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }
}