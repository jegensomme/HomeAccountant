package ru.jegensomme.homeaccountant.util;

import lombok.experimental.UtilityClass;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.to.CategoryTo;

@UtilityClass
public class CategoryUtil {
    public static Category createNewFromTo(CategoryTo categoryTo) {
        return new Category(null, categoryTo.getName(), categoryTo.getLimit(), categoryTo.getPeriod());
    }

    public static void updateFromTo(Category category, CategoryTo categoryTo) {
        category.setName(categoryTo.getName());
        category.setLimit(categoryTo.getLimit());
        category.setPeriod(categoryTo.getPeriod());
    }
}
