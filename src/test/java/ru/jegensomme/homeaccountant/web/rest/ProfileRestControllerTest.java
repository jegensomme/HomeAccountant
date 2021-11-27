package ru.jegensomme.homeaccountant.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.repository.UserRepository;
import ru.jegensomme.homeaccountant.to.UserTo;
import ru.jegensomme.homeaccountant.util.UserUtil;
import ru.jegensomme.homeaccountant.web.AbstractControllerTest;

import java.math.BigDecimal;
import java.util.Objects;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.jegensomme.homeaccountant.UserTestData.*;
import static ru.jegensomme.homeaccountant.TestUtil.readFromJson;
import static ru.jegensomme.homeaccountant.TestUtil.userHttpBasic;
import static ru.jegensomme.homeaccountant.web.GlobalExceptionHandler.EXCEPTION_DUPLICATE_EMAIL;
import static ru.jegensomme.homeaccountant.web.rest.ProfileRestController.REST_URL;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserRepository repository;

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(repository.findAll(), ADMIN);
    }

    @Test
    void register() throws Exception {
        UserTo newTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");
        User newUser = UserUtil.createNewFromTo(newTo);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(newTo)))
                .andDo(print())
                .andExpect(status().isCreated());
        User created = readFromJson(action, User.class);
        int newId = Objects.requireNonNull(created.getId());
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(repository.getExisted(newId), newUser);
    }

    @Test
    void update() throws Exception {
        UserTo updated = new UserTo(null, "updatedName", "updated@yandex.ru", "updatedPassword", new BigDecimal("1000"), USD);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(jsonWithPassword(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(repository.getExisted(USER_ID), UserUtil.updateFromTo(new User(USER), updated));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(USER));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void registerInvalid() throws Exception {
        UserTo newTo = new UserTo(null, "", "foo.ru", "", new BigDecimal("-10"), null);
        perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(newTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateInvalid() throws Exception {
        UserTo updatedTo = new UserTo(null, null, null, "", new BigDecimal("1000"), null);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(jsonWithPassword(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateHtmlUnsafe() throws Exception {
        UserTo updated = UserUtil.asTo(USER);
        updated.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(jsonWithPassword(updated, "password")))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        UserTo updatedTo = UserUtil.asTo(USER);
        updatedTo.setEmail("admin@gmail.com");
        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(jsonWithPassword(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(EXCEPTION_DUPLICATE_EMAIL)));
    }
}