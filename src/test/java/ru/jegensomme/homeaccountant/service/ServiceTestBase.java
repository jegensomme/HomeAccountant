package ru.jegensomme.homeaccountant.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.jegensomme.homeaccountant.util.TimingExtension;
import ru.jegensomme.homeaccountant.config.ApplicationConfig;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.jegensomme.homeaccountant.Profiles.POSTGRES;
import static ru.jegensomme.homeaccountant.util.ValidationUtil.getRootCause;

@SpringJUnitConfig(ApplicationConfig.class)
@ExtendWith(TimingExtension.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(POSTGRES)
public abstract class ServiceTestBase {
    //  Check root cause in JUnit: https://github.com/junit-team/junit4/pull/778
    public <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> rootExceptionClass) {
        assertThrows(rootExceptionClass, () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw getRootCause(e);
            }
        });
    }
}
