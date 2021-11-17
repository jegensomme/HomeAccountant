package ru.jegensomme.homeaccountant.web.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.jegensomme.homeaccountant.model.Expense;
import ru.jegensomme.homeaccountant.service.ExpenseService;
import ru.jegensomme.homeaccountant.to.ExpenseTo;
import ru.jegensomme.homeaccountant.web.AbstractExpenseController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.jegensomme.homeaccountant.util.ValidationUtil.getErrorResponse;

@RestController
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpenseUIController extends AbstractExpenseController {
    public ExpenseUIController(ExpenseService service) {
        super(service);
    }

    @PostMapping("/expenses")
    public ResponseEntity<String> createOrUpdate(@Valid ExpenseTo expense, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getErrorResponse(bindingResult);
        }
        if (expense.isNew()) {
            super.create(expense);
        } else {
            super.update(expense, expense.id());
        }
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("/expenses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
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
