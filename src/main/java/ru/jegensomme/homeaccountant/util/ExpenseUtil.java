package ru.jegensomme.homeaccountant.util;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.to.ExpenseTo;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.jegensomme.homeaccountant.util.CommonUtilities.isBetweenHalfOpen;

@UtilityClass
public class ExpenseUtil {
    public static List<ExpenseTo> getTos(Collection<Expense> expenses) {
        return filter(expenses, e -> true);
    }

    public static List<ExpenseTo> getTos(Expense... expenses) {
        return filter(List.of(expenses), e -> true);
    }

    public static List<ExpenseTo> getFilteredTos(@NonNull Collection<Expense> expenses,
                                                 @Nullable LocalTime startTime, @Nullable LocalTime endTime) {
        return filter(expenses, e -> isBetweenHalfOpen(e.getTime(), startTime, endTime));
    }

    public static List<ExpenseTo> filter(@NonNull Collection<Expense> expenses, @NonNull Predicate<Expense> predicate) {
        return expenses.stream().filter(predicate).map(ExpenseUtil::asTo).collect(Collectors.toList());
    }

    public static ExpenseTo asTo(Expense expense) {
        return new ExpenseTo(expense.getId(), expense.getDateTime(), expense.getAmount(), expense.getCurrency(), expense.getDescription());
    }
}
