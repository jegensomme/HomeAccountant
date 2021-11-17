package ru.jegensomme.homeaccountant.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.service.CategoryService;
import ru.jegensomme.homeaccountant.web.AbstractCategoryController;

import java.net.URI;
import java.util.List;

import static ru.jegensomme.homeaccountant.web.rest.CategoryRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryRestController extends AbstractCategoryController {
    static final String REST_URL = "/rest/profile/categories";

    public CategoryRestController(CategoryService service) {
        super(service);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> createWithLocation(@RequestBody Category category) {
        Category created = super.create(category);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Category category, @PathVariable int id) {
        super.update(category, id);
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

    @Override
    @GetMapping
    public List<Category> getAll() {
        return super.getAll();
    }
}
