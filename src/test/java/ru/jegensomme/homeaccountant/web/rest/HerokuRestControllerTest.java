package ru.jegensomme.homeaccountant.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.jegensomme.homeaccountant.util.exception.ErrorType;
import ru.jegensomme.homeaccountant.web.AbstractControllerTest;

import java.io.IOException;
import java.util.Objects;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.jegensomme.homeaccountant.Profiles.HEROKU;
import static ru.jegensomme.homeaccountant.testdata.UserTestData.*;
import static ru.jegensomme.homeaccountant.util.TestUtil.userHttpBasic;
import static ru.jegensomme.homeaccountant.util.exception.UpdateRestrictionException.EXCEPTION_UPDATE_RESTRICTION;

@ActiveProfiles(HEROKU)
class HerokuRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + '/';

    // Set DATABASE_URL environment for heroku profile
    static {
        Resource resource = new ClassPathResource("db/postgres.properties");
        try {
            ResourcePropertySource propertySource = new ResourcePropertySource(resource);
            String herokuDbUrl = String.format("postgres://%s:%s@%s",
                    propertySource.getProperty("database.username"),
                    propertySource.getProperty("database.password"),
                    ((String) Objects.requireNonNull(propertySource.getProperty("database.url"))).substring(18));
            System.out.println(herokuDbUrl);

            System.setProperty("DATABASE_URL", herokuDbUrl);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_UPDATE_RESTRICTION))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(jsonWithPassword(USER)))
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_UPDATE_RESTRICTION))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void disable() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + USER_ID)
                .with(userHttpBasic(ADMIN))
                .param("enabled", "false")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_UPDATE_RESTRICTION))
                .andExpect(status().isUnprocessableEntity());
    }
}
