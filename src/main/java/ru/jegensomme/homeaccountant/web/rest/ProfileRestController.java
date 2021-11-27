package ru.jegensomme.homeaccountant.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.jegensomme.homeaccountant.repository.ExpenseRepository;
import ru.jegensomme.homeaccountant.web.AuthorizedUser;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.repository.UserRepository;
import ru.jegensomme.homeaccountant.to.UserTo;
import ru.jegensomme.homeaccountant.util.UserUtil;
import ru.jegensomme.homeaccountant.web.AbstractUserController;
import ru.jegensomme.homeaccountant.web.validation.UniqueMailValidator;

import javax.validation.Valid;
import java.net.URI;

import static ru.jegensomme.homeaccountant.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "users")
public class ProfileRestController extends AbstractUserController {
    static final String REST_URL = "/rest/profile";

    public ProfileRestController(UserRepository repository, ExpenseRepository expenseRepository, UniqueMailValidator emailValidator) {
        super(repository, expenseRepository, emailValidator);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        log.info("register {}", userTo);
        checkNew(userTo);
        User created = prepareAndSave(UserUtil.createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void delete(@AuthenticationPrincipal AuthorizedUser authUser) {
        super.delete(authUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(allEntries = true)
    public void update(@RequestBody UserTo userTo, @AuthenticationPrincipal AuthorizedUser authUser) throws BindException {
        validateBeforeUpdate(userTo, authUser.id());
        User user = repository.getExisted(userTo.id());
        prepareAndSave(UserUtil.updateFromTo(user, userTo));
    }

    @GetMapping
    public ResponseEntity<User> get(@AuthenticationPrincipal AuthorizedUser authUser) {
        return super.get(authUser.id());
    }
}
