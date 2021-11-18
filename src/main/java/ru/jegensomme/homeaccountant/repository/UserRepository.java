package ru.jegensomme.homeaccountant.repository;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.jegensomme.homeaccountant.model.User;

import java.util.List;

public interface UserRepository {
    User save(@NonNull User user);

    boolean delete(int id);

    @Nullable User get(int id);

    @Nullable User getByEmail(@NonNull String email);

    List<User> getAll();
}
