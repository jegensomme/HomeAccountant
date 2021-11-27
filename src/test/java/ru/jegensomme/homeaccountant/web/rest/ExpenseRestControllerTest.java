package ru.jegensomme.homeaccountant.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.repository.ExpenseRepository;
import ru.jegensomme.homeaccountant.to.ExpenseTo;
import ru.jegensomme.homeaccountant.TestUtil;
import ru.jegensomme.homeaccountant.util.ExpenseUtil;
import ru.jegensomme.homeaccountant.web.AbstractControllerTest;
import ru.jegensomme.homeaccountant.util.JsonUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static java.time.LocalDateTime.of;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.jegensomme.homeaccountant.CategoryTestData.USER_FOOD;
import static ru.jegensomme.homeaccountant.CategoryTestData.USER_HOUSEHOLD;
import static ru.jegensomme.homeaccountant.ExpenseTestData.*;
import static ru.jegensomme.homeaccountant.UserTestData.USER;
import static ru.jegensomme.homeaccountant.UserTestData.USER_ID;
import static ru.jegensomme.homeaccountant.UserTestData.ADMIN;
import static ru.jegensomme.homeaccountant.UserTestData.ADMIN_ID;
import static ru.jegensomme.homeaccountant.util.ExpenseUtil.createNewFromTo;
import static ru.jegensomme.homeaccountant.util.ExpenseUtil.getTos;
import static ru.jegensomme.homeaccountant.TestUtil.*;
import static ru.jegensomme.homeaccountant.web.GlobalExceptionHandler.EXCEPTION_DUPLICATE_DATETIME;

class ExpenseRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ExpenseRestController.REST_URL + '/';

    @Autowired
    private ExpenseRepository repository;

    @Test
    void createWithLocation() throws Exception {
        ExpenseTo newExpenseTo = new ExpenseTo(null, USER_FOOD.getName(), of(2021, Month.JANUARY, 25, 10, 0), "10000", "New");
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newExpenseTo)))
                .andDo(print())
                .andExpect(status().isCreated());
        Expense created = readFromJson(action, Expense.class);
        int newId = created.id();
        Expense newExpense = createNewFromTo(newExpenseTo, USER_FOOD);
        newExpense.setId(newId);
        EXPENSE_MATCHER.assertMatch(created, newExpense);
        EXPENSE_MATCHER.assertMatch(repository.get(newId, USER_ID).orElse(null), newExpense);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + EXPENSE1_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.get(EXPENSE1_ID, USER_ID).isPresent());
    }

    @Test
    void update() throws Exception {
        ExpenseTo updatedTo = new ExpenseTo(EXPENSE1_ID, USER_HOUSEHOLD.getName(), of(2021, Month.FEBRUARY, 15, 10, 0), "10000", "Updated");
        perform(MockMvcRequestBuilders.put(REST_URL + EXPENSE1_ID)
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Expense updated = new Expense(EXPENSE1_ID, USER_FOOD, EXPENSE1.getDateTime(), EXPENSE1.getAmount(), EXPENSE1.getDescription());
        ExpenseUtil.updateFromTo(updated, updatedTo, USER_HOUSEHOLD);
        EXPENSE_MATCHER.assertMatch(repository.get(EXPENSE1_ID, USER_ID).orElse(null), updated);
    }

    @Test
    void createInvalid() throws Exception {
        ExpenseTo invalid = new ExpenseTo(null, null, null, null, "", null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateInvalid() throws Exception {
        ExpenseTo invalid = new ExpenseTo(ADMIN_EXPENSE1_ID, "", null, "-10", "");
        perform(MockMvcRequestBuilders.put(REST_URL + ADMIN_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
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
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND)
                .with(userHttpBasic(USER)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(EXPENSE_TO_MATCHER.contentJson(getTos(EXPENSE6, EXPENSE5, EXPENSE4, EXPENSE3, EXPENSE2, EXPENSE1)));
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
                .andExpect(EXPENSE_TO_MATCHER.contentJson(getTos(EXPENSE6)));
    }

    @Test
    void getBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "between")
                .param("startDate", "2021-01-30").param("startTime", "10:00")
                .param("endDate", "2021-02-26").param("endTime", "17:00")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(EXPENSE_TO_MATCHER.contentJson(getTos(EXPENSE5, EXPENSE4, EXPENSE3, EXPENSE2, EXPENSE1)));
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
                .andExpect(EXPENSE_TO_MATCHER.contentJson(getTos(EXPENSE6)));
    }

    @Test
    public void testTotalAmountForCurrentMonth() throws Exception {
        LocalDate now = LocalDate.now();
        LocalDateTime start = LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0);
        repository.save(new Expense(null, USER, USER_FOOD, start, new BigDecimal("1000.00"), "new"));
        repository.save(new Expense(null, USER, USER_FOOD, start.plusDays(1), new BigDecimal("2000.00"), "new"));
        repository.save(new Expense(null, USER, USER_FOOD, start.plusDays(2), new BigDecimal("3000.00"), "new"));
        perform(MockMvcRequestBuilders.get(REST_URL + "month-total")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertEquals(new BigDecimal("6000.00"), TestUtil.readFromJsonMvcResult(result, BigDecimal.class)));
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        ExpenseTo updated = new ExpenseTo(EXPENSE1_ID, USER_FOOD.getName(), EXPENSE1.getDateTime(), "1000", "<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        ExpenseTo updatedTo = new ExpenseTo(EXPENSE1_ID, USER_FOOD.getName(), EXPENSE3.getDateTime(), "1000", "new");
        perform(MockMvcRequestBuilders.put(REST_URL + EXPENSE1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_DATETIME)));
    }
}