package ru.jegensomme.homeaccountant.web;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.service.UserService;

import java.util.List;

import static ru.jegensomme.homeaccountant.util.ValidationUtil.assureIdConsistent;
import static ru.jegensomme.homeaccountant.util.ValidationUtil.checkNew;

@RequiredArgsConstructor
public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService service;

    public @NonNull User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
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
}
