package ru.jegensomme.homeaccountant.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.repository.UserRepository;

import java.util.List;
import java.util.Objects;

import static ru.jegensomme.homeaccountant.util.ValidationUtil.checkNotFound;
import static ru.jegensomme.homeaccountant.util.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public @NotNull User create(@Nullable User user) {
        Assert.notNull(user, "user must not be null");
        return Objects.requireNonNull(repository.save(user));
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public void update(@Nullable User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(user), user.id());
    }

    public @NotNull User get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public @NotNull User getByEmail(@Nullable String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    public List<User> getAll() {
        return repository.getAll();
    }
}
