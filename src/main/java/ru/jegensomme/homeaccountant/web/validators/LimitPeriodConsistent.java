package ru.jegensomme.homeaccountant.web.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LimitPeriodConsistencyValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitPeriodConsistent {
    String message() default "{ru.jegensomme.homeaccountant.web.validators.LimitPeriodConsistent.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
