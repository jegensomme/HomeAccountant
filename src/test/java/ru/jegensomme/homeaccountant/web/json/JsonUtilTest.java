package ru.jegensomme.homeaccountant.web.json;

import org.junit.jupiter.api.Test;
import ru.jegensomme.homeaccountant.model.Expense;

import java.util.List;

import static ru.jegensomme.homeaccountant.testdata.ExpenseTestData.*;

class JsonUtilTest {

    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(EXPENSE1);
        System.out.println(json);
        Expense expense = JsonUtil.readValue(json, Expense.class);
        EXPENSE_MATCHER.assertMatch(expense, EXPENSE1);
    }

    @Test
    void readWriteValues() {
        String json = JsonUtil.writeValue(List.of(EXPENSE1, EXPENSE2, EXPENSE3));
        System.out.println(json);
        List<Expense> expenses = JsonUtil.readValues(json, Expense.class);
        EXPENSE_MATCHER.assertMatch(expenses, EXPENSE1, EXPENSE2, EXPENSE3);
    }
}