package ru.jegensomme.homeaccountant.web.validation;

import ru.jegensomme.homeaccountant.model.Category;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LimitPeriodConsistencyValidator implements ConstraintValidator<LimitPeriodConsistent, Category> {
    @Override
    public void initialize(LimitPeriodConsistent constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Category category, ConstraintValidatorContext context) {
        return category.getLimit() != null && category.getPeriod() != null
                || category.getLimit() == null && category.getPeriod() == null ;
    }
}
