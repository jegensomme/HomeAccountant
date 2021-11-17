package ru.jegensomme.homeaccountant.util.validators;

import ru.jegensomme.homeaccountant.to.CategoryTo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExpensePeriodValidator implements ConstraintValidator<PeriodLimit, CategoryTo> {
    @Override
    public void initialize(PeriodLimit constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CategoryTo category, ConstraintValidatorContext context) {
        return category.getLimit() != null && category.getPeriod() != null
                || category.getLimit() == null && category.getPeriod() == null ;
    }
}
