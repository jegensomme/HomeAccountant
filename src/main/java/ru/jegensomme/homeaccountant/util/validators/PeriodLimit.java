package ru.jegensomme.homeaccountant.util.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExpensePeriodValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PeriodLimit {
    String message() default "{ru.jegensomme.homeaccountant.util.validators.PeriodLimit.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
