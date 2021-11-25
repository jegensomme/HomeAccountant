package ru.jegensomme.homeaccountant.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import ru.jegensomme.homeaccountant.Identified;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.service.UserService;
import ru.jegensomme.homeaccountant.to.UserTo;
import ru.jegensomme.homeaccountant.util.UserUtil;
import ru.jegensomme.homeaccountant.web.validators.UniqueMailValidator;

import java.util.List;

import static ru.jegensomme.homeaccountant.util.ValidationUtil.assureIdConsistent;
import static ru.jegensomme.homeaccountant.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected final UserService service;

    private Validator validator;

    private final UniqueMailValidator emailValidator;

    public AbstractUserController(UserService service, UniqueMailValidator emailValidator) {
        this.service = service;
        this.emailValidator = emailValidator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public User create(UserTo userTo) {
        log.info("create from to {}", userTo);
        return service.create(UserUtil.createNewFromTo(userTo));
    }

    public @NonNull User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(UserTo userTo, int id) {
        log.info("update from to {} with id={}", userTo, id);
        service.update(userTo);
    }

    public @NonNull User get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public @NonNull User getByMail(String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        service.enable(id, enabled);
    }

    protected void validateBeforeUpdate(@NonNull Identified user, int id) throws BindException {
        assureIdConsistent(user, id);
        DataBinder binder = new DataBinder(user);
        binder.addValidators(emailValidator, validator);
        binder.validate(View.Web.class);
        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }

    @Autowired
    @Qualifier("defaultValidator")
    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}
