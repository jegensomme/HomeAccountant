package ru.jegensomme.homeaccountant.util.exception;

public class UpdateRestrictionException extends ApplicationException {
    public static final String EXCEPTION_UPDATE_RESTRICTION = "exception.user.updateRestriction";

    public UpdateRestrictionException() {
        super(ErrorType.VALIDATION_ERROR, EXCEPTION_UPDATE_RESTRICTION);
    }
}
