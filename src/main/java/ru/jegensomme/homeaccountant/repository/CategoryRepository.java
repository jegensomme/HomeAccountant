package ru.jegensomme.homeaccountant.repository;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.jegensomme.homeaccountant.model.Category;

import java.util.List;

public interface CategoryRepository {
    @Nullable Category save(@NonNull Category category, int userId);

    boolean delete(int id, int userId);

    @Nullable Category get(int id, int userId);

    List<Category> getAll(int userId);
}
