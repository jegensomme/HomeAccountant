package ru.jegensomme.homeaccountant.service;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.jegensomme.homeaccountant.util.TimingRules;
import ru.jegensomme.homeaccountant.config.ApplicationConfig;

import static org.junit.Assert.assertThrows;
import static ru.jegensomme.homeaccountant.util.ValidationUtil.getRootCause;

@ContextConfiguration(classes = { ApplicationConfig.class })
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class ServiceTestBase {
    @ClassRule
    public static final ExternalResource SUMMARY = TimingRules.SUMMARY;

    @Rule
    public final Stopwatch stopwatch = TimingRules.STOPWATCH;

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
