package ru.jegensomme.homeaccountant.util;

import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;
import ru.jegensomme.homeaccountant.model.Category;
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

    public static List<ExpenseTo> getTos(Expense... expenses) {
        return getTos(List.of(expenses));
    }

    public static List<ExpenseTo> getTos(Collection<Expense> expenses) {
        return filter(expenses, e -> true);
    }

    public static List<ExpenseTo> filter(Collection<Expense> expenses,
                                       @Nullable LocalTime startTime, @Nullable LocalTime endTime) {
        return filter(expenses, e -> isBetweenHalfOpen(e.getTime(), startTime, endTime));
    }

    public static List<ExpenseTo> filter(Collection<Expense> expenses, Predicate<Expense> predicate) {
        return expenses.stream().filter(predicate).map(ExpenseUtil::asTo).collect(Collectors.toList());
    }

    public static ExpenseTo asTo(Expense expense) {
        return new ExpenseTo(expense.getId(), expense.getCategory() == null ? "" : expense.getCategory().getName(),
                expense.getDateTime(), expense.getAmount(), expense.getDescription());
    }

    public static Expense createNewFromTo(ExpenseTo expenseTo, @Nullable Category category) {
        return new Expense(null, category, expenseTo.getDateTime(), expenseTo.getAmount(), expenseTo.getDescription());
    }

    public static void updateFromTo(Expense expense, ExpenseTo expenseTo, @Nullable Category category) {
        expense.setCategory(category);
        expense.setDateTime(expenseTo.getDateTime());
        expense.setAmount(expenseTo.getAmount());
        expense.setDescription(expenseTo.getDescription());
    }
}
