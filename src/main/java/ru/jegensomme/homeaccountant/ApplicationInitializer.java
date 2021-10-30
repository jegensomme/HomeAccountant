package ru.jegensomme.homeaccountant;

import org.springframework.lang.NonNull;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;
import ru.jegensomme.homeaccountant.config.ApplicationConfig;
import ru.jegensomme.homeaccountant.web.WebConfig;

import javax.servlet.Filter;

public class ApplicationInitializer extends AbstractDispatcherServletInitializer {
    @Override
    protected WebApplicationContext createRootApplicationContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.getEnvironment().setActiveProfiles("postgres");
        context.register(ApplicationConfig.class);
        return context;
    }

    @Override
    protected @NonNull WebApplicationContext createServletApplicationContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);
        return context;
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] { new CharacterEncodingFilter("UTF-8", true) };
    }

    @Override
    protected @NonNull String [] getServletMappings() {
        return new String[] { "/" };
    }
}
