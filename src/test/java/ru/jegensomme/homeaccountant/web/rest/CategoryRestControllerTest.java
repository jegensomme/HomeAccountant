package ru.jegensomme.homeaccountant.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.service.CategoryService;
import ru.jegensomme.homeaccountant.util.exception.NotFoundException;
import ru.jegensomme.homeaccountant.web.AbstractControllerTest;
import ru.jegensomme.homeaccountant.web.json.JsonUtil;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.jegensomme.homeaccountant.testdata.CategoryTestData.*;
import static ru.jegensomme.homeaccountant.testdata.UserTestData.USER_ID;
import static ru.jegensomme.homeaccountant.testdata.UserTestData.USER;
import static ru.jegensomme.homeaccountant.testdata.UserTestData.ADMIN_ID;
import static ru.jegensomme.homeaccountant.testdata.UserTestData.ADMIN;
import static ru.jegensomme.homeaccountant.util.TestUtil.*;
import static ru.jegensomme.homeaccountant.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.jegensomme.homeaccountant.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_CATEGORY;

class CategoryRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = CategoryRestController.REST_URL + '/';

    @Autowired
    private CategoryService service;

    @Test
    void createWithLocation() throws Exception {
        Category newCategory = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newCategory)))
                .andDo(print())
                .andExpect(status().isCreated());
        Category created = readFromJson(action, Category.class);
        int newId = created.id();
        newCategory.setId(newId);
        CATEGORY_MATCHER.assertMatch(created, newCategory);
        CATEGORY_MATCHER.assertMatch(service.get(newId, USER_ID), newCategory);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER_FOOD_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(USER_FOOD_ID, USER_ID));
    }

    @Test
    void update() throws Exception {
        Category updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + USER_FOOD_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isNoContent());
        CATEGORY_MATCHER.assertMatch(updated, service.get(USER_FOOD_ID, USER_ID));
    }

    @Test
    void createInvalid() throws Exception {
        Category invalid = new Category(null, "");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateInvalid() throws Exception {
        Category invalid = new Category(ADMIN_FOOD_ID, "updated", new BigDecimal("1000.00"), null);
        perform(MockMvcRequestBuilders.put(REST_URL + ADMIN_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_FOOD_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(CATEGORY_MATCHER.contentJson(USER_FOOD));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_FOOD_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getByName() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by")
                .param("name", "Food")
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(CATEGORY_MATCHER.contentJson(USER_FOOD));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(CATEGORY_MATCHER.contentJson(USER_FOOD, USER_HOUSEHOLD));
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        Category updated = new Category(USER_FOOD_ID, "<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Category updated = new Category(USER_FOOD_ID, USER_HOUSEHOLD.getName());
        perform(MockMvcRequestBuilders.put(REST_URL + USER_FOOD_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_CATEGORY));
    }
}