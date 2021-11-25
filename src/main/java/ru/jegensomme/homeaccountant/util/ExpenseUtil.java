package ru.jegensomme.homeaccountant.util;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.model.Period;
import ru.jegensomme.homeaccountant.to.ExpenseTo;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;
import static ru.jegensomme.homeaccountant.util.CommonUtilities.isBetweenHalfOpen;

@UtilityClass
public class ExpenseUtil {

    public static List<ExpenseTo> getTos(Expense... expenses) {
        return getTos(List.of(expenses));
    }

    public static List<ExpenseTo> getTos(@NonNull Collection<Expense> expenses) {
        return filter(expenses, e -> true);
    }

    public static List<ExpenseTo> filter(@NonNull Collection<Expense> expenses,
                                         @Nullable LocalTime startTime, @Nullable LocalTime endTime) {
        return filter(expenses, e -> isBetweenHalfOpen(e.getTime(), startTime, endTime));
    }

    public static List<ExpenseTo> filter(@NonNull Collection<Expense> expenses, @NonNull Predicate<Expense> predicate) {
        List<ExpenseTo> result = new ArrayList<>(expenses.size());
        result.addAll(expenses.stream().filter(e -> e.getCategory() == null).map(ExpenseUtil::asTo).collect(toList()));
        Map<Category, List<Expense>> groupedByCategories = expenses.stream()
                .filter(e -> e.getCategory() != null)
                .collect(groupingBy(Expense::getCategory));
        for (Category category : groupedByCategories.keySet()) {
            Period period = category.getPeriod();
            BigDecimal limit = category.getLimit();
            ChronoField chronoField = Period.DAY.equals(period) ? ChronoField.DAY_OF_YEAR
                    : Period.WEEK.equals(period) ? ChronoField.ALIGNED_WEEK_OF_YEAR
                    : Period.MONTH.equals(period) ? ChronoField.MONTH_OF_YEAR
                    : Period.YEAR.equals(period) ? ChronoField.YEAR
                    : null;
            List<Expense> inCategory = groupedByCategories.get(category);
            if (chronoField == null) {
                result.addAll(inCategory.stream().map(ExpenseUtil::asTo).collect(toList()));
                continue;
            }
            Map<Integer, List<Expense>> groupedByYears = inCategory.stream().collect(groupingBy(e -> e.getDateTime().getYear()));
            for (List<Expense> inYear : groupedByYears.values()) {
                Map<Integer, BigDecimal> groupedByPeriod = inYear.stream().collect(
                        groupingBy(e -> e.getDateTime().get(chronoField),
                                reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add)));
                List<ExpenseTo> tos = inYear.stream()
                        .filter(predicate)
                        .map(e -> asTo(e, groupedByPeriod.get(e.getDateTime().get(chronoField)).compareTo(limit) > 0))
                        .collect(toList());
                result.addAll(tos);
            }
        }
        return result;
    }

    private static ExpenseTo asTo(Expense expense) {
        return asTo(expense, null);
    }

    private static ExpenseTo asTo(Expense expense, Boolean excess) {
        return new ExpenseTo(expense.getId(), expense.getCategory() == null ? "" : expense.getCategory().getName(),
                expense.getDateTime(), expense.getAmount(), expense.getDescription(), excess);
    }

    public static Expense createNewFromTo(@NonNull ExpenseTo expenseTo, @Nullable Category category) {
        return new Expense(null, category, expenseTo.getDateTime(), expenseTo.getAmount(), expenseTo.getDescription());
    }

    public static void updateFromTo(@NonNull Expense expense, @NonNull ExpenseTo expenseTo, @Nullable Category category) {
        expense.setCategory(category);
        expense.setDateTime(expenseTo.getDateTime());
        expense.setAmount(expenseTo.getAmount());
        expense.setDescription(expenseTo.getDescription());
    }
}
