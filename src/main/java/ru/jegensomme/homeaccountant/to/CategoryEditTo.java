package ru.jegensomme.homeaccountant.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import ru.jegensomme.homeaccountant.model.ExpensePeriod;
import ru.jegensomme.homeaccountant.util.validators.PeriodLimit;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@PeriodLimit
public class CategoryEditTo extends BaseTo {

    private String name;

    private @Nullable Integer limit;

    private @Nullable ExpensePeriod period;

    public CategoryEditTo(@Nullable Integer id, String name, @Nullable Integer limit, @Nullable ExpensePeriod period) {
        super(id);
        this.name = name;
        this.limit = limit;
        this.period = period;
    }

    @Override
    public String toString() {
        return "CategoryTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", limit=" + limit +
                ", period=" + period +
                '}';
    }
}
