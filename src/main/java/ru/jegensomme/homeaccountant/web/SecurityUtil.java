package ru.jegensomme.homeaccountant.web;

import lombok.experimental.UtilityClass;
import ru.jegensomme.homeaccountant.model.BaseEntity;

@UtilityClass
public class SecurityUtil {
    private static int id = BaseEntity.START_SEQ;

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }
}