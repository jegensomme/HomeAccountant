package ru.jegensomme.homeaccountant.web.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.service.CategoryService;
import ru.jegensomme.homeaccountant.web.AbstractCategoryController;
import ru.jegensomme.homeaccountant.web.View;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@ApiIgnore
@RestController
@RequestMapping(value = "/profile/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryUIController extends AbstractCategoryController {
    public CategoryUIController(CategoryService service) {
        super(service);
    }

    @PostMapping
    public ResponseEntity<String> createOrUpdate(@Validated(View.Web.class) Category category) {
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

    @Override
    @GetMapping("/by")
    public @NonNull
    Category getByName(@RequestParam String name) {
        return super.getByName(name);
    }

    @GetMapping
    public List<Category> getAll() {
        return super.getAll();
    }
}
