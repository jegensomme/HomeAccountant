package ru.jegensomme.homeaccountant.repository.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import ru.jegensomme.homeaccountant.model.Expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudExpenseRepository extends JpaRepository<Expense, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Expense e WHERE e.id=:id AND e.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Query("""
        SELECT e FROM Expense e LEFT JOIN FETCH e.category
        WHERE e.user.id=:userId
        ORDER BY e.dateTime DESC
        """)
    List<Expense> getAll(@Param("userId") int userId);

    @Query("""
        SELECT e FROM Expense e
        WHERE e.user.id=:userId AND e.category IS NULL
        ORDER BY e.dateTime DESC
        """)
    List<Expense> getWithoutCategory(@Param("userId") int userId);

    @Query("""
        SELECT e FROM Expense e LEFT JOIN FETCH e.category
        WHERE e.user.id=:userId AND e.dateTime>=:start AND e.dateTime<:end
        ORDER BY e.dateTime DESC
        """)
    List<Expense> getBetween(@Param("userId") int userId,
                             @Param("start") LocalDateTime startInclusive, @Param("end") LocalDateTime endExclusive);

    @Query("""
        SELECT e FROM Expense e LEFT JOIN FETCH e.category
        WHERE e.user.id=:userId AND e.category.name=:category
        ORDER BY e.dateTime DESC
        """)
    List<Expense> getByCategory(@Param("category") String category, @Param("userId") int userId);

    @Query("""
        SELECT e FROM Expense e LEFT JOIN FETCH e.category
        WHERE e.user.id=:userId AND e.category.name=:category
              AND e.dateTime>=:start AND e.dateTime<:end
        ORDER BY e.dateTime DESC
        """)
    List<Expense> getByCategoryBetween(@Param("category") String category, @Param("userId") int userId,
                                       @Param("start") @NonNull LocalDateTime startInclusive,
                                       @Param("end") @NonNull LocalDateTime endExclusive);

    @Query("""
        SELECT e FROM Expense e LEFT JOIN FETCH e.category
        WHERE e.user.id=:userId AND e.category.id IS NULL
              AND e.dateTime>=:start AND e.dateTime<:end
        ORDER BY e.dateTime DESC
        """)
    List<Expense> getWithoutCategoryBetween(@Param("userId") int userId,
                                            @Param("start") @NonNull LocalDateTime startInclusive,
                                            @Param("end") @NonNull LocalDateTime endExclusive);

    @Query(value = """
        SELECT sum(e.amount) FROM EXPENSES e
        WHERE user_id=:userId AND date_trunc('month', e.date_time) = date_trunc('month', CURRENT_DATE)
        """, nativeQuery = true)
    BigDecimal getTotalAmountForCurrentMonth(@Param("userId") int userId);
}
