package ru.jegensomme.homeaccountant.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.Assert;

@Data
@NoArgsConstructor
public abstract class BaseEntity {
    private @Nullable Integer id;

    public BaseEntity(@NotNull Integer id) {
        this.id = id;
    }

    // doesn't work for hibernate lazy proxy
    public int id() {
        Assert.notNull(id, "Entity must have id");
        return id;
    }

    public boolean isNew() {
        return id == null;
    }
}
