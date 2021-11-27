package ru.jegensomme.homeaccountant.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import ru.jegensomme.homeaccountant.Identified;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.repository.ExpenseRepository;
import ru.jegensomme.homeaccountant.repository.UserRepository;
import ru.jegensomme.homeaccountant.util.UserUtil;
import ru.jegensomme.homeaccountant.web.validation.UniqueMailValidator;

import static ru.jegensomme.homeaccountant.util.ValidationUtil.assureIdConsistent;
import static ru.jegensomme.homeaccountant.util.ValidationUtil.checkSingleModification;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractUserController {
    protected final UserRepository repository;

    protected final ExpenseRepository expenseRepository;

    private final UniqueMailValidator emailValidator;

    @Autowired
    private LocalValidatorFactoryBean validator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        expenseRepository.deleteAllInBatch(expenseRepository.getAll(id));
        expenseRepository.flush();
        checkSingleModification(repository.delete(id), "User id=" + id + " not found");
    }

    public ResponseEntity<User> get(int id) {
        log.info("get {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    protected User prepareAndSave(User user) {
        return repository.save(UserUtil.prepareToSave(user, passwordEncoder));
    }

    protected void validateBeforeUpdate(@NonNull Identified user, int id) throws BindException {
        assureIdConsistent(user, id);
        DataBinder binder = new DataBinder(user);
        binder.addValidators(emailValidator, validator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }
}
