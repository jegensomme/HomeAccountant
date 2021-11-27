package ru.jegensomme.homeaccountant.web.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.jegensomme.homeaccountant.web.AuthorizedUser;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.repository.CategoryRepository;
import ru.jegensomme.homeaccountant.repository.ExpenseRepository;
import ru.jegensomme.homeaccountant.repository.UserRepository;
import ru.jegensomme.homeaccountant.to.ExpenseTo;
import ru.jegensomme.homeaccountant.util.ExpenseUtil;
import ru.jegensomme.homeaccountant.error.NotFoundException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.jegensomme.homeaccountant.util.DateTimeUtil.atStartOfDayOrMin;
import static ru.jegensomme.homeaccountant.util.DateTimeUtil.atStartOfNextDayOrMax;
import static ru.jegensomme.homeaccountant.util.ExpenseUtil.filter;
import static ru.jegensomme.homeaccountant.util.ExpenseUtil.getTos;
import static ru.jegensomme.homeaccountant.util.ValidationUtil.*;
import static ru.jegensomme.homeaccountant.web.rest.ExpenseRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class ExpenseRestController {
    static final String REST_URL = "/rest/profile/expenses";

    private final ExpenseRepository repository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Expense> createWithLocation(@Valid @RequestBody ExpenseTo expenseTo, @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.id();
        log.info("create from to {} for user {}", expenseTo, userId);
        checkNew(expenseTo);
        Category category = getCategory(expenseTo.getCategory(), userId);
        Expense newExpense = ExpenseUtil.createNewFromTo(expenseTo, category);
        newExpense.setUser(userRepository.getById(userId));
        Expense created = repository.save(newExpense);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/expenses/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.id();
        log.info("delete {} for user {}", id, userId);
        checkSingleModification(repository.delete(id, userId), "Expense id=" + id + " not found");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody ExpenseTo expenseTo, @PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.id();
        log.info("update from to {} for user {}", expenseTo, userId);
        assureIdConsistent(expenseTo, id);
        Expense expense = checkNotFoundWithId(repository.get(id, authUser.id()), "Meal id=" + id + " doesn't belong to user id=" + userId);
        Category category = getCategory(expenseTo.getCategory(), userId);
        ExpenseUtil.updateFromTo(expense, expenseTo, category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> get(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.id();
        log.info("get {} for user {}", id, userId);
        return ResponseEntity.of(repository.get(id, userId));
    }

    @GetMapping
    public List<ExpenseTo> getAll(@RequestParam @Nullable String category, @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.id();
        log.info("getAll for user {}", userId);
        List<Expense> expenses = category == null ? repository.getAll(userId)
                : "".equals(category) ? repository.getWithoutCategory(userId)
                : repository.getByCategory(category, userId);
        return getTos(expenses);
    }

    @GetMapping("/between")
    public List<ExpenseTo> getBetween(
            @RequestParam @Nullable String category,
            @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
            @AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.id();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        List<Expense> expenses = category == null ? repository.getBetween(userId, atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate))
                : "".equals(category) ? repository.getWithoutCategoryBetween(userId, atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate))
                : repository.getByCategoryBetween(category, userId, atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate));
        return filter(expenses, startTime, endTime);
    }

    @GetMapping("/month-total")
    public BigDecimal getTotalAmountForCurrentMonth(@AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.id();
        log.info("getTotalAmountForCurrentMonth for user {}", userId);
        return repository.getTotalAmountForCurrentMonth(userId);
    }

    private Category getCategory(String categoryName, int userId) {
        return StringUtils.hasText(categoryName)
                ? categoryRepository.getByName(categoryName, userId).orElseThrow(() -> new NotFoundException("Not found category " + categoryName))
                : null;
    }
}
