package ru.jegensomme.homeaccountant.repository.impl;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.repository.CategoryRepository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final CrudUserRepository crudUserRepository;
    private final CrudCategoryRepository crudRepository;

    @Transactional
    @Override
    public @Nullable Category save(@NotNull Category category, int userId) {
        if (!category.isNew() && get(category.id(), userId) == null) {
            return null;
        }
        category.setUser(crudUserRepository.getById(userId));
        return crudRepository.save(category);
    }

    @Transactional
    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) > 0;
    }

    @Override
    public @Nullable Category get(int id, int userId) {
        return crudRepository.findById(id)
                .filter(c -> Objects.equals(c.getUser().getId(), userId))
                .orElse(null);
    }

    @Override
    public List<Category> getAll(int userId) {
        return crudRepository.getAll(userId);
    }
}
