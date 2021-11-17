package ru.jegensomme.homeaccountant.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.service.ExpenseService;
import ru.jegensomme.homeaccountant.web.AbstractExpenseController;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.jegensomme.homeaccountant.web.rest.ExpenseRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpenseRestController extends AbstractExpenseController {
    static final String REST_URL = "/rest/profile";

    public ExpenseRestController(ExpenseService service) {
        super(service);
    }

    @PostMapping(value = "/expenses", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Expense> createWithLocation(@RequestBody Expense expense) {
        Expense created = super.create(expense);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/expenses/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/expenses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/expenses/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Expense expense, @PathVariable int id) {
        super.update(expense, id);
    }

    @Override
    @GetMapping("/expenses/{id}")
    public @NonNull
    Expense get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping("/expenses")
    public List<Expense> getAll() {
        return super.getAll();
    }

    @GetMapping("/categories/{categoryId}/expenses")
    public List<Expense> getByCategory(@PathVariable int categoryId) {
        return categoryId > 0 ? super.getByCategory(categoryId) : super.getWithoutCategory();
    }

    @GetMapping("/expenses/between")
    public List<Expense> getBetween(
            @RequestParam @Nullable LocalDate startDate,
            @RequestParam @Nullable LocalTime startTime,
            @RequestParam @Nullable LocalDate endDate,
            @RequestParam @Nullable LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }

    @GetMapping("/categories/{categoryId}/expenses/between")
    public List<Expense> getByCategoryBetween(
            @PathVariable int categoryId,
            @RequestParam @Nullable LocalDate startDate,
            @RequestParam @Nullable LocalTime startTime,
            @RequestParam @Nullable LocalDate endDate,
            @RequestParam @Nullable LocalTime endTime) {
        return categoryId > 0
                ? super.getByCategoryBetween(categoryId, startDate, startTime, endDate, endTime)
                : super.getWithoutCategoryBetween(startDate, startTime, endDate, endTime);
    }
}
