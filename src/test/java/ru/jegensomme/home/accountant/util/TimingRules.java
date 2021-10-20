package ru.jegensomme.home.accountant.util;

import lombok.experimental.UtilityClass;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@UtilityClass
public class TimingRules {
    private static final Logger log = LoggerFactory.getLogger("result");

    private static final StringBuilder results = new StringBuilder();

    public static final Stopwatch STOPWATCH = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("%-95s %7d", description.getDisplayName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result).append('\n');
            log.info(result + " ms\n");
        }
    };

    public static final ExternalResource SUMMARY = new ExternalResource() {
        @Override
        protected void before() {
            results.setLength(0);
        }

        @Override
        protected void after() {
            log.info("""
                -------------------------------------------------------------------------------------------------------
                Test                                                                                       Duration, ms
                -------------------------------------------------------------------------------------------------------
                %s
                -------------------------------------------------------------------------------------------------------
                               
                """.formatted(results));
        }
    };
}
