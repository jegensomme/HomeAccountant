package ru.jegensomme.homeaccountant.web.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import ru.jegensomme.homeaccountant.model.User;
import ru.jegensomme.homeaccountant.service.UserService;
import ru.jegensomme.homeaccountant.to.UserTo;
import ru.jegensomme.homeaccountant.web.AbstractUserController;
import ru.jegensomme.homeaccountant.web.validators.UniqueMailValidator;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@ApiIgnore
@RestController
@RequestMapping(value = "/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUIController extends AbstractUserController {
    public AdminUIController(UserService service, UniqueMailValidator emailValidator) {
        super(service, emailValidator);
    }

    @PostMapping
    public ResponseEntity<String> createOrUpdate(@Valid @ModelAttribute("user") UserTo userTo) {
        if (userTo.isNew()) {
            super.create(userTo);
        } else {
            super.update(userTo, userTo.id());
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
    public @NonNull User get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        super.enable(id, enabled);
    }
}
