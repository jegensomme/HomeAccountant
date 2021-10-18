package ru.jegensomme.homeaccountant.service;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.repository.CategoryRepository;

import java.util.List;

import static ru.jegensomme.homeaccountant.util.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository repository;

    public Category create(Category category, int userId) {
        Assert.notNull(category, "category must not be null");
        return repository.save(category, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public void update(Category Category, int userId) {
        Assert.notNull(Category, "category must not be null");
        checkNotFoundWithId(repository.save(Category, userId), Category.id());
    }

    public @NotNull Category get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Category> getAll(int userId) {
        return repository.getAll(userId);
    }
}
