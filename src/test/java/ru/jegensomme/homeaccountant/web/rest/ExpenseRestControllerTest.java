package ru.jegensomme.homeaccountant.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.service.ExpenseService;
import ru.jegensomme.homeaccountant.to.ExpenseTo;
import ru.jegensomme.homeaccountant.util.exception.NotFoundException;
import ru.jegensomme.homeaccountant.web.AbstractControllerTest;
import ru.jegensomme.homeaccountant.web.json.JsonUtil;

import java.time.Month;

import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.jegensomme.homeaccountant.testdata.CategoryTestData.USER_FOOD;
import static ru.jegensomme.homeaccountant.testdata.CategoryTestData.USER_HOUSEHOLD;
import static ru.jegensomme.homeaccountant.testdata.ExpenseTestData.*;
import static ru.jegensomme.homeaccountant.testdata.UserTestData.USER;
import static ru.jegensomme.homeaccountant.testdata.UserTestData.USER_ID;
import static ru.jegensomme.homeaccountant.util.ExpenseUtil.getTos;
import static ru.jegensomme.homeaccountant.util.TestUtil.*;

class ExpenseRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ExpenseRestController.REST_URL + '/';

    @Autowired
    private ExpenseService service;

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createWithLocation() throws Exception {
        Expense newExpense = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newExpense)))
                .andDo(print())
                .andExpect(status().isCreated());
        Expense created = readFromJson(action, Expense.class);
        int newId = created.id();
        newExpense.setId(newId);
        EXPENSE_MATCHER.assertMatch(created, newExpense);
        EXPENSE_MATCHER.assertMatch(service.get(newId, USER_ID), newExpense);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + EXPENSE1_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(EXPENSE1_ID, USER_ID));
    }

    @Test
    void update() throws Exception {
        ExpenseTo updated = new ExpenseTo(EXPENSE1_ID, USER_HOUSEHOLD.getName(), of(2021, Month.FEBRUARY, 15, 10, 0), 10000, "Updated");
        perform(MockMvcRequestBuilders.put(REST_URL + EXPENSE1_ID)
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        EXPENSE_MATCHER.assertMatch(getUpdated(), service.get(EXPENSE1_ID, USER_ID));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + EXPENSE1_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(EXPENSE_MATCHER.contentJson(EXPENSE1));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(EXPENSE_TO_MATCHER.contentJson(getTos(EXPENSE5, EXPENSE4, EXPENSE3, EXPENSE2, EXPENSE1)));
    }

    @Test
    void getByCategory() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .param("category", USER_FOOD.getName())
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(EXPENSE_TO_MATCHER.contentJson(getTos(EXPENSE3, EXPENSE2, EXPENSE1)));
    }

    @Test
    void getWithoutCategory() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .param("category", "")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(EXPENSE_TO_MATCHER.contentJson(getTos(EXPENSE5)));
    }

    @Test
    void getBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "between")
                .param("startDate", "2021-01-30").param("startTime", "10:00")
                .param("endDate", "2021-02-26").param("endTime", "14:00")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(EXPENSE_TO_MATCHER.contentJson(getTos(EXPENSE4, EXPENSE3, EXPENSE2, EXPENSE1)));
    }

    @Test
    void getBetweenByCategory() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "between")
                .param("category", USER_FOOD.getName())
                .param("startDate", "2021-02-01").param("startTime", "10:00")
                .param("endDate", "2021-02-26").param("endTime", "14:00")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(EXPENSE_TO_MATCHER.contentJson(getTos(EXPENSE3, EXPENSE2)));
    }

    @Test
    void getBetweenWithoutCategory() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "between")
                .param("category", "")
                .param("startDate", "2021-02-01").param("startTime", "10:00")
                .param("endDate", "2021-02-27").param("endTime", "15:00")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(EXPENSE_TO_MATCHER.contentJson(getTos(EXPENSE5)));
    }
}