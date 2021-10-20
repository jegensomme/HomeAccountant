package ru.jegensomme.homeaccountant.repository.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.jegensomme.homeaccountant.model.Expense;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudExpenseRepository extends JpaRepository<Expense, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Expense e WHERE e.id=:id AND e.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query("SELECT e FROM Expense e WHERE e.user.id=:userId ORDER BY e.dateTime ASC")
    List<Expense> getAll(@Param("userId") int userId);

    @Query("""
        SELECT e FROM Expense e
        WHERE e.user.id=:userId AND e.dateTime>=:start AND e.dateTime<:end
        ORDER BY e.dateTime ASC
        """)
    List<Expense> getBetween(@Param("userId") int userId,
                             @Param("start") LocalDateTime startInclusive, @Param("end") LocalDateTime endExclusive);

    @Query("""
        SELECT e FROM Expense e
        WHERE e.user.id=:userId AND e.category.id=:categoryId
        ORDER BY e.dateTime ASC
        """)
    List<Expense> getByCategory(@Param("categoryId") int categoryId, @Param("userId") int userId);

    @Query("""
        SELECT e FROM Expense e
        WHERE e.user.id=:userId AND e.category.id=:categoryId
              AND e.dateTime>=:start AND e.dateTime<:end
        ORDER BY e.dateTime ASC
        """)
    List<Expense> getByCategoryBetween(int categoryId, int userId,
                                       @NotNull LocalDateTime startInclusive, @NotNull LocalDateTime endExclusive);
}
