package ru.jegensomme.homeaccountant.util;

import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.to.ExpenseEditTo;
import ru.jegensomme.homeaccountant.to.ExpenseTo;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Currency;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.jegensomme.homeaccountant.util.CommonUtilities.isBetweenHalfOpen;

@UtilityClass
public class ExpenseUtil {
    public static List<ExpenseTo> getTos(Collection<Expense> expenses, Currency currency) {
        return filter(expenses, e -> true, currency);
    }

    public static List<ExpenseTo> getTos(Currency currency, Expense... expenses) {
        return filter(List.of(expenses), e -> true, currency);
    }

    public static List<ExpenseTo> getFilteredTos(Collection<Expense> expenses,
                                                 @Nullable LocalTime startTime, @Nullable LocalTime endTime,
                                                 Currency currency) {
        return filter(expenses, e -> isBetweenHalfOpen(e.getTime(), startTime, endTime), currency);
    }

    public static List<ExpenseTo> filter(Collection<Expense> expenses, Predicate<Expense> predicate, Currency currency) {
        return expenses.stream().filter(predicate).map(e -> createTo(e, currency)).collect(Collectors.toList());
    }

    private static ExpenseTo createTo(Expense expense, Currency currency) {
        return new ExpenseTo(expense.getId(), expense.getCategory(), expense.getDateTime(), expense.getAmount(), currency, expense.getDescription());
    }

    public static Expense createNewFromTo(ExpenseEditTo expenseTo, @Nullable Category category) {
        return new Expense(null, category, expenseTo.getDateTime(), expenseTo.getAmount(), expenseTo.getDescription());
    }

    public static void updateFromTo(Expense expense, ExpenseEditTo expenseTo, @Nullable Category category) {
        expense.setCategory(category);
        expense.setDateTime(expenseTo.getDateTime());
        expense.setAmount(expenseTo.getAmount());
        expense.setDescription(expenseTo.getDescription());
    }
}
