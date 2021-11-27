package ru.jegensomme.homeaccountant.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.model.Expense;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CategoryRepository extends BaseRepository<Category> {

    @Query("SELECT c FROM Category c WHERE c.id=:id and c.user.id=:userId")
    Optional<Category> get(int id, int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Category c WHERE c.id=:id AND c.user.id=:userId")
    int delete(int id, int userId);

    @Query("SELECT c FROM Category c WHERE c.user.id=:userId AND c.name=:name ORDER BY c.name ASC")
    Optional<Category> getByName(String name, int userId);

    @Query("SELECT c FROM Category c WHERE c.user.id=:userId ORDER BY c.name ASC")
    List<Category> getAll(int userId);
}
