package ru.jegensomme.homeaccountant.repository.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.jegensomme.homeaccountant.model.Category;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudCategoryRepository extends JpaRepository<Category, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Category c WHERE c.id=:id AND c.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT c FROM Category c WHERE c.user.id=:userId AND c.name=:name ORDER BY c.name ASC")
    Category getByName(@Param("name") String name, @Param("userId") int userId);

    @Query("SELECT c FROM Category c WHERE c.user.id=:userId ORDER BY c.name ASC")
    List<Category> getAll(@Param("userId") int userId);
}
