package ru.jegensomme.homeaccountant.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ru.jegensomme.homeaccountant.util.converter.DateTimeFormatters;
import ru.jegensomme.homeaccountant.web.json.JacksonObjectMapper;

import java.util.List;
import java.util.Locale;

@EnableWebMvc
@ComponentScan("ru.jegensomme.homeaccountant.web")
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/view/");
        resolver.setSuffix(".jsp");
        registry.viewResolver(resolver);
    }

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**", "/webjars/**").addResourceLocations("/resources/", "classpath:/META-INF/resources/webjars/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateTimeFormatters.LocalDateFormatter());
        registry.addFormatter(new DateTimeFormatters.LocalTimeFormatter());
    }

    @Override
    public void configureMessageConverters(@NonNull List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(JacksonObjectMapper.getMapper()));
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setSupportedMediaTypes(List.of(
                MediaType.valueOf("text/plain;charset=UTF-8"),
                MediaType.valueOf("text/html;charset=UTF-8")));
        converters.add(stringHttpMessageConverter);
    }

    @Value("file:///#{systemEnvironment[HOME_ACCOUNTANT_ROOT]}/config/messages/app")
    private String resourceBundlePath;

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setCacheSeconds(5);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename(resourceBundlePath);
        return messageSource;
    }
}
