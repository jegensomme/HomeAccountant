package ru.jegensomme.homeaccountant.web.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.jegensomme.homeaccountant.repository.ExpenseRepository;
import ru.jegensomme.homeaccountant.web.AuthorizedUser;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.repository.CategoryRepository;
import ru.jegensomme.homeaccountant.repository.UserRepository;
import ru.jegensomme.homeaccountant.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.jegensomme.homeaccountant.util.ValidationUtil.assureIdConsistent;
import static ru.jegensomme.homeaccountant.util.ValidationUtil.checkNotFoundWithId;
import static ru.jegensomme.homeaccountant.web.rest.CategoryRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class CategoryRestController {
    static final String REST_URL = "/rest/profile/categories";

    private final CategoryRepository repository;

    private final UserRepository userRepository;

    private final ExpenseRepository expenseRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Category> createWithLocation(@Valid @RequestBody Category category, @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.id();
        log.info("create {} for user {}", category, userId);
        category.setUser(userRepository.getById(userId));
        Category created = repository.save(category);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.id();
        log.info("delete {} for user {}", id, userId);
        expenseRepository.getByCategory(id, userId).forEach(e -> e.setCategory(null));
        expenseRepository.flush();
        ValidationUtil.checkSingleModification(repository.delete(userId, id), "Expense id=" + id + " not found");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Category category, @PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.id();
        checkNotFoundWithId(repository.get(id, authUser.id()), "Category id=" + id + " doesn't belong to user id=" + userId);
        log.info("update {} for user {}", category, userId);
        assureIdConsistent(category, id);
        category.setUser(userRepository.getById(userId));
        repository.save(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> get(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.id();
        log.info("get {} for user {}", id, userId);
        return ResponseEntity.of(repository.get(userId, id));
    }

    @GetMapping("/by")
    public ResponseEntity<Category> getByName(@RequestParam String name, @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.id();
        log.info("getByName {} for user {}", name, userId);
        return ResponseEntity.of(repository.getByName(name, userId));
    }

    @GetMapping
    public List<Category> getAll(@AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.id();
        log.info("getAll categories for user {}", userId);
        return repository.getAll(userId);
    }
}
