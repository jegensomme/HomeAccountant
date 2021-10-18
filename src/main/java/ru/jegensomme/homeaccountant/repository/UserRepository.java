package ru.jegensomme.homeaccountant.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.jegensomme.homeaccountant.model.User;

import java.util.List;

public interface UserRepository {
    @Nullable User save(@NotNull User user);

    boolean delete(int id);

    @Nullable User get(int id);

    @Nullable User getByEmail(@NotNull String email);

    List<User> getAll();
}
