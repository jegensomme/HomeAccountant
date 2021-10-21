package ru.jegensomme.home.accountant.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.jegensomme.homeaccountant.model.Role;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.service.UserService;
import ru.jegensomme.homeaccountant.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

import static org.junit.Assert.assertThrows;
import static ru.jegensomme.home.accountant.testdata.UserTestData.*;

public class UserServiceTest extends ServiceTestBase {
    @Autowired
    private UserService service;

    @Before
    public void setUp() {
        Objects.requireNonNull(cacheManager.getCache("users")).clear();
    }

    @Test
    public void create() {
        User created = service.create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    public void duplicateMailCreate() {
        Assert.assertThrows(DataAccessException.class, () -> service.create(getDuplicateEmail()));
    }

    @Test
    public void delete() {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    public void get() {
        USER_MATCHER.assertMatch(USER, service.get(USER_ID));
        service.get(USER_ID);
        service.get(ADMIN_ID);
        service.get(USER_ID);
        service.get(ADMIN_ID);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    public void getByEmail() {
        USER_MATCHER.assertMatch(USER, service.getByEmail(USER.getEmail()));
    }

    @Test
    public void update() {
        service.update(getUpdated());
        USER_MATCHER.assertMatch(service.get(USER_ID), getUpdated());
    }

    @Test
    public void getAll() {
        USER_MATCHER.assertMatch(service.getAll(), ADMIN, USER);
    }

    @Test
    public void createWithException() {
        validateRootCause(() -> service.create(new User(null, "  ", "mail@yandex.ru", "password", 10000, Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "   ", "password", 10000, Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail", "password", 10000, Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "pswd", 10000, Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "password", -10, Role.USER)), ConstraintViolationException.class);
    }
}
