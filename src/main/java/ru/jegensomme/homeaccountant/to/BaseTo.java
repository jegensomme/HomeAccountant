package ru.jegensomme.homeaccountant.to;

import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import ru.jegensomme.homeaccountant.Identified;

import java.util.Objects;

@NoArgsConstructor
public abstract class BaseTo implements Identified {
    protected @Nullable Integer id;

    public BaseTo(@Nullable Integer id) {
        this.id = id;
    }

    @Override
    public @Nullable Integer getId() {
        return id;
    }

    @Override
    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseTo baseTo = (BaseTo) o;
        return Objects.equals(id, baseTo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
