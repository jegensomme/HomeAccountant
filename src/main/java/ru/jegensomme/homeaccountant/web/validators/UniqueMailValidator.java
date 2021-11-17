package ru.jegensomme.homeaccountant.web.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import ru.jegensomme.homeaccountant.HasEmail;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.service.UserService;
import ru.jegensomme.homeaccountant.util.exception.NotFoundException;
import ru.jegensomme.homeaccountant.web.ExceptionInfoHandler;

@Component
@RequiredArgsConstructor
public class UniqueMailValidator implements org.springframework.validation.Validator {

    private final UserService service;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return HasEmail.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        HasEmail user = ((HasEmail) target);
        if (StringUtils.hasText(user.getEmail())) {
            User dbUser;
            try {
                dbUser = service.getByEmail(user.getEmail().toLowerCase());
            } catch (NotFoundException ignored) {
                return;
            }
            if (!dbUser.getId().equals(user.getId())) {
                errors.rejectValue("email", ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL);
            }
        }
    }
}
