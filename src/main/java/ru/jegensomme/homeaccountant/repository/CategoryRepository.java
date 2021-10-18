package ru.jegensomme.homeaccountant.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.jegensomme.homeaccountant.model.Category;

import java.util.List;

public interface CategoryRepository {
    @Nullable Category save(@NotNull Category category, int userId);

    boolean delete(int id, int userId);

    @Nullable Category get(int id, int userId);

    List<Category> getAll(int userId);
}
