package ru.jegensomme.homeaccountant.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public abstract class NamedEntity extends BaseEntity {
    private @NotNull String name;

    public NamedEntity(@NotNull Integer id, @NotNull String name) {
        super(id);
        this.name = name;
    }
}
