package ru.jegensomme.homeaccountant.web.json;

import org.junit.jupiter.api.Test;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.testdata.UserTestData;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void writeOnlyAccess() {
        String json = JsonUtil.writeValue(UserTestData.USER);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = UserTestData.jsonWithPassword(UserTestData.USER, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}