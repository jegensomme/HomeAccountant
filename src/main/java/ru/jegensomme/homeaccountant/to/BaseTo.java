package ru.jegensomme.homeaccountant.to;

import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import ru.jegensomme.homeaccountant.Identified;

@NoArgsConstructor
public abstract class BaseTo implements Identified {
    private @Nullable Integer id;

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
}
