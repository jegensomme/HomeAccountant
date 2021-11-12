package ru.jegensomme.homeaccountant.util;

import lombok.experimental.UtilityClass;
import ru.jegensomme.homeaccountant.model.Category;
import ru.jegensomme.homeaccountant.to.CategoryEditTo;

@UtilityClass
public class CategoryUtil {
    public static Category createNewFromTo(CategoryEditTo categoryTo) {
        return new Category(null, categoryTo.getName(), categoryTo.getLimit(), categoryTo.getPeriod());
    }

    public static void updateFromTo(Category category, CategoryEditTo categoryTo) {
        category.setName(categoryTo.getName());
        category.setLimit(categoryTo.getLimit());
        category.setPeriod(categoryTo.getPeriod());
    }
}
