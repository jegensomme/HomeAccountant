package ru.jegensomme.homeaccountant.web;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.service.UserService;
import ru.jegensomme.homeaccountant.to.UserTo;
import ru.jegensomme.homeaccountant.util.UserUtil;
import ru.jegensomme.homeaccountant.web.validators.UniqueMailValidator;

import java.util.List;

import static ru.jegensomme.homeaccountant.util.ValidationUtil.assureIdConsistent;
import static ru.jegensomme.homeaccountant.util.ValidationUtil.checkNew;

@RequiredArgsConstructor
public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService service;

    private final UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public @NonNull User create(UserTo userTo) {
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
        assureIdConsistent(userTo, id);
        service.update(userTo);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
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
}
