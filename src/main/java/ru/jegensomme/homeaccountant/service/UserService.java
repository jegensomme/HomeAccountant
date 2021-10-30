package ru.jegensomme.homeaccountant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return Objects.requireNonNull(repository.save(user));
    }

    @Caching(evict = {
            @CacheEvict(value = "users", allEntries = true),
            @CacheEvict(value = "categories", key = "#id")
    })
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Caching(evict = {
            @CacheEvict(value = "users", allEntries = true),
            @CacheEvict(value = "categories", key = "#user.id")
    })
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.save(user), user.id());
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Cacheable("users")
    public List<User> getAll() {
        return repository.getAll();
    }
}
