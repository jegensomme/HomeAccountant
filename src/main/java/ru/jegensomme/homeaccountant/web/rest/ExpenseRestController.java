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
import ru.jegensomme.homeaccountant.to.ExpenseTo;
import ru.jegensomme.homeaccountant.web.AbstractExpenseController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.jegensomme.homeaccountant.web.rest.ExpenseRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpenseRestController extends AbstractExpenseController {
    static final String REST_URL = "/rest/profile/expenses";

    public ExpenseRestController(ExpenseService service) {
        super(service);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Expense> createWithLocation(@Valid @RequestBody ExpenseTo expenseTo) {
        Expense created = super.create(expenseTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/expenses/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody ExpenseTo expenseTo, @PathVariable int id) {
        super.update(expenseTo, id);
    }

    @Override
    @GetMapping("/{id}")
    public @NonNull
    Expense get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping
    public List<ExpenseTo> getAll(@RequestParam @Nullable String category) {
        return category == null ? super.getAll()
                : "".equals(category) ? super.getWithoutCategory()
                : super.getByCategory(category);
    }

    @GetMapping("/between")
    public List<ExpenseTo> getBetween(
            @RequestParam @Nullable String category,
            @RequestParam @Nullable LocalDate startDate,
            @RequestParam @Nullable LocalTime startTime,
            @RequestParam @Nullable LocalDate endDate,
            @RequestParam @Nullable LocalTime endTime) {
        return category == null ? super.getBetween(startDate, startTime, endDate, endTime)
                : "".equals(category) ? super.getWithoutCategoryBetween(startDate, startTime, endDate, endTime)
                : super.getByCategoryBetween(category, startDate, startTime, endDate, endTime);
    }

    @GetMapping("/month-total")
    public BigDecimal getTotalAmountForCurrentMonth() {
        return super.getTotalAmountForCurrentMonth();
    }
}
