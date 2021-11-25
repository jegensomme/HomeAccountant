package ru.jegensomme.homeaccountant.web.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.service.ExpenseService;
import ru.jegensomme.homeaccountant.to.ExpenseTo;
import ru.jegensomme.homeaccountant.web.AbstractExpenseController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@ApiIgnore
@RestController
@RequestMapping(value = "/profile/expenses", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpenseUIController extends AbstractExpenseController {
    public ExpenseUIController(ExpenseService service) {
        super(service);
    }

    @PostMapping
    public ResponseEntity<String> createOrUpdate(@Valid @ModelAttribute("expense") ExpenseTo expenseTo) {
        if (expenseTo.isNew()) {
            super.create(expenseTo);
        } else {
            super.update(expenseTo, expenseTo.id());
        }
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
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
