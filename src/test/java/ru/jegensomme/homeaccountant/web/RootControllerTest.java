package ru.jegensomme.homeaccountant.web;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.jegensomme.homeaccountant.testdata.UserTestData.ADMIN;
import static ru.jegensomme.homeaccountant.testdata.UserTestData.USER;
import static ru.jegensomme.homeaccountant.util.TestUtil.userAuth;

public class RootControllerTest extends AbstractControllerTest {

    @Test
    public void getUsers() throws Exception {
        perform(get("/users")
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/view/users.jsp"));
    }

    @Test
    public void getExpenses() throws Exception {
        perform(get("/expenses")
                .with(userAuth(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("expenses"))
                .andExpect(forwardedUrl("/WEB-INF/view/expenses.jsp"));
    }

    @Test
    public void getCategories() throws Exception {
        perform(get("/categories")
                .with(userAuth(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("categories"))
                .andExpect(forwardedUrl("/WEB-INF/view/categories.jsp"));
    }
}
