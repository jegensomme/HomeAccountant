package ru.jegensomme.homeaccountant.web.ajax;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.service.CategoryService;
import ru.jegensomme.homeaccountant.web.AbstractCategoryController;

import javax.validation.Valid;
import java.util.List;

import static ru.jegensomme.homeaccountant.util.ValidationUtil.getErrorResponse;

@RestController
@RequestMapping(value = "/profile/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryUIController extends AbstractCategoryController {
    public CategoryUIController(CategoryService service) {
        super(service);
    }

    @PostMapping
    public ResponseEntity<String> createOrUpdate(@Valid Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getErrorResponse(bindingResult);
        }
        if (category.isNew()) {
            super.create(category);
        } else {
            super.update(category, category.id());
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
    Category get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping
    public List<Category> getAll() {
        return super.getAll();
    }
}
