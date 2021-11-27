package ru.jegensomme.homeaccountant.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SafeHtmlValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SafeHtml {
    String message() default "may have unsafe html content";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
