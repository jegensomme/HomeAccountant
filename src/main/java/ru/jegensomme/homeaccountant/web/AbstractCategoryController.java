package ru.jegensomme.homeaccountant.web;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.service.CategoryService;

import java.util.List;

import static ru.jegensomme.homeaccountant.util.ValidationUtil.assureIdConsistent;
import static ru.jegensomme.homeaccountant.web.SecurityUtil.authUserId;

@RequiredArgsConstructor
public class AbstractCategoryController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final CategoryService service;

    public @NonNull Category create(Category category) {
        int userId = authUserId();
        log.info("create {} for user {}", category, userId);
        return service.create(category, userId);
    }

    public void delete(int id) {
        int userId = authUserId();
        log.info("delete {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public void update(Category category, int id) {
        int userId = authUserId();
        assureIdConsistent(category, id);
        log.info("update {} for user {}", category, userId);
        service.update(category, userId);
    }

    public @NonNull Category get(int id) {
        int userId = authUserId();
        log.info("get {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public List<Category> getAll() {
        int userId = authUserId();
        log.info("getAll categories for user {}", userId);
        return service.getAll(userId);
    }
}
