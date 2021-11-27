package ru.jegensomme.homeaccountant.web.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.jegensomme.homeaccountant.Authenticatable;
import ru.jegensomme.homeaccountant.repository.UserRepository;
import ru.jegensomme.homeaccountant.web.GlobalExceptionHandler;

@Component
@RequiredArgsConstructor
public class UniqueMailValidator implements Validator {

    private final UserRepository repository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Authenticatable.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Authenticatable user = ((Authenticatable) target);
        if (StringUtils.hasText(user.getEmail())) {
            if (repository.getByEmail(user.getEmail().toLowerCase())
                    .filter(u -> !u.getId().equals(user.getId())).isPresent()) {
                errors.rejectValue("email", "", GlobalExceptionHandler.EXCEPTION_DUPLICATE_EMAIL);
            }
        }
    }
}
