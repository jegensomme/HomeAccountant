package ru.jegensomme.homeaccountant.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import ru.jegensomme.homeaccountant.model.Expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface ExpenseRepository extends BaseRepository<Expense> {

    @Query("SELECT e FROM Expense e WHERE e.id=:id and e.user.id=:userId")
    Optional<Expense> get(int id, int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Expense e WHERE e.id=:id AND e.user.id=:userId")
    int delete(int id, int userId);

    @Query("""
        SELECT e FROM Expense e LEFT JOIN FETCH e.category
        WHERE e.user.id=:userId
        ORDER BY e.dateTime DESC
        """)
    List<Expense> getAll(int userId);

    @Query("""
        SELECT e FROM Expense e
        WHERE e.user.id=:userId AND e.category IS NULL
        ORDER BY e.dateTime DESC
        """)
    List<Expense> getWithoutCategory(int userId);

    @Query("""
        SELECT e FROM Expense e LEFT JOIN FETCH e.category
        WHERE e.user.id=:userId AND e.dateTime>=:startInclusive AND e.dateTime<:endExclusive
        ORDER BY e.dateTime DESC
        """)
    List<Expense> getBetween(int userId, @NonNull LocalDateTime startInclusive, @NonNull LocalDateTime endExclusive);

    @Query("""
        SELECT e FROM Expense e LEFT JOIN FETCH e.category
        WHERE e.user.id=:userId AND e.category.name=:category
        ORDER BY e.dateTime DESC
        """)
    List<Expense> getByCategory(String category, int userId);

    @Query("""
        SELECT e FROM Expense e LEFT JOIN FETCH e.category
        WHERE e.user.id=:userId AND e.category.id=:categoryId
        ORDER BY e.dateTime DESC
        """)
    List<Expense> getByCategory(int categoryId, int userId);

    @Query("""
        SELECT e FROM Expense e LEFT JOIN FETCH e.category
        WHERE e.user.id=:userId AND e.category.name=:category
              AND e.dateTime>=:startInclusive AND e.dateTime<:endExclusive
        ORDER BY e.dateTime DESC
        """)
    List<Expense> getByCategoryBetween(String category, int userId,
                                       @NonNull LocalDateTime startInclusive, @NonNull LocalDateTime endExclusive);

    @Query("""
        SELECT e FROM Expense e LEFT JOIN FETCH e.category
        WHERE e.user.id=:userId AND e.category.id IS NULL
              AND e.dateTime>=:startInclusive AND e.dateTime<:endExclusive
        ORDER BY e.dateTime DESC
        """)
    List<Expense> getWithoutCategoryBetween(int userId, @NonNull LocalDateTime startInclusive, @NonNull LocalDateTime endExclusive);

    @Query(value = """
        SELECT sum(e.amount) FROM EXPENSES e
        WHERE user_id=:userId AND date_trunc('month', e.date_time) = date_trunc('month', CURRENT_DATE)
        """, nativeQuery = true)
    BigDecimal getTotalAmountForCurrentMonth(int userId);
}
