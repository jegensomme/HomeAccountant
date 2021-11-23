package ru.jegensomme.homeaccountant.to;

import lombok.NoArgsConstructor;
import ru.jegensomme.homeaccountant.Identified;

import java.util.Objects;

@NoArgsConstructor
public abstract class BaseTo implements Identified {
    protected Integer id;

    public BaseTo(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
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
