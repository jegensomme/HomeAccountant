package ru.jegensomme.homeaccountant.web.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import ru.jegensomme.homeaccountant.Authenticatable;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.service.UserService;
import ru.jegensomme.homeaccountant.util.exception.NotFoundException;
import ru.jegensomme.homeaccountant.web.ExceptionInfoHandler;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UniqueMailValidator implements org.springframework.validation.Validator {

    private final UserService service;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Authenticatable.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Authenticatable user = ((Authenticatable) target);
        if (StringUtils.hasText(user.getEmail())) {
            User dbUser;
            try {
                dbUser = service.getByEmail(user.getEmail().toLowerCase());
            } catch (NotFoundException ignored) {
                return;
            }
            if (dbUser.id() != (Objects.requireNonNullElse(user.getId(), -1))) {
                errors.rejectValue("email", ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL);
            }
        }
    }
}
