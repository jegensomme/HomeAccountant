package ru.jegensomme.homeaccountant.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@NoArgsConstructor
public abstract class BaseEntity {
    private @Nullable Integer id;

    public BaseEntity(@NotNull Integer id) {
        this.id = id;
    }
}
