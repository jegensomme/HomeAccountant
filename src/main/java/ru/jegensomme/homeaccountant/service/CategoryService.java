package ru.jegensomme.homeaccountant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.repository.CategoryRepository;
import ru.jegensomme.homeaccountant.to.CategoryEditTo;
import ru.jegensomme.homeaccountant.util.CategoryUtil;

import java.util.List;
import java.util.Objects;

import static ru.jegensomme.homeaccountant.util.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    @CacheEvict(value = "categories", key = "#userId")
    public Category create(Category category, int userId) {
        Assert.notNull(category, "category must not be null");
        return Objects.requireNonNull(repository.save(category, userId));
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

    @Transactional
    @CacheEvict(value = "categories", key = "#userId")
    public void update(CategoryEditTo categoryTo, int userId) {
        Assert.notNull(categoryTo, "category must not be null");
        Category category = get(categoryTo.id(), userId);
        CategoryUtil.updateFromTo(category, categoryTo);
    }

    public Category get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Cacheable(value = "categories", key = "#userId")
    public List<Category> getAll(int userId) {
        return repository.getAll(userId);
    }
}
