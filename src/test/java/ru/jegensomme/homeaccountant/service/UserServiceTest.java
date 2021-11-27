package ru.jegensomme.homeaccountant.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.jegensomme.homeaccountant.model.Role;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.jegensomme.homeaccountant.testdata.UserTestData.*;

public class UserServiceTest extends ServiceTestBase {
    @Autowired
    private UserService service;

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
        assertThrows(DataAccessException.class, () -> service.create(getDuplicateEmail()));
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
        USER_MATCHER.assertMatch(ADMIN, service.get(ADMIN_ID));
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
    void enable() {
        service.enable(USER_ID, false);
        assertFalse(service.get(USER_ID).isEnabled());
        service.enable(USER_ID, true);
        assertTrue(service.get(USER_ID).isEnabled());
    }

    @Test
    public void createWithException() {
        validateRootCause(() -> service.create(new User(null, "  ", "mail@yandex.ru", "password", "10000.00", RUB, Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "   ", "password", "10000.00", RUB, Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail", "password", "10000.00", RUB, Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "User", "mail@yandex.ru", "password", "-10", RUB, Role.USER)), ConstraintViolationException.class);
    }
}
