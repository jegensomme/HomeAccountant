package ru.jegensomme.homeaccountant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.repository.CategoryRepository;

import java.util.List;

import static ru.jegensomme.homeaccountant.util.ValidationUtil.checkNotFound;
import static ru.jegensomme.homeaccountant.util.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    @CacheEvict(value = "categories", key = "#userId")
    public Category create(Category category, int userId) {
        Assert.notNull(category, "category must not be null");
        return repository.save(category, userId);
    }

    @CacheEvict(value = "categories", key = "#userId")
    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @CacheEvict(value = "categories", key = "#userId")
    public void update(Category category, int userId) {
        Assert.notNull(category, "category must not be null");
        checkNotFoundWithId(repository.save(category, userId), category.id());
    }

    public @NonNull Category get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public @NonNull Category getByName(String name, int userId) {
        return checkNotFound(repository.getByName(name, userId), "name=" + name);
    }

    @Cacheable(value = "categories", key = "#userId")
    public List<Category> getAll(int userId) {
        return repository.getAll(userId);
    }
}
