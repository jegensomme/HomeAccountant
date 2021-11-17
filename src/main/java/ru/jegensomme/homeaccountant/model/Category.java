package ru.jegensomme.homeaccountant.model;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;
import ru.jegensomme.homeaccountant.web.View;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "name"}, name = "categories_unique_user_name_idx")
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull(groups = View.Persist.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private @Nullable User user;

    @Column(name = "\"limit\"")
    @Min(1)
    private @Nullable Integer limit;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "number", column = @Column(name = "period_number")),
            @AttributeOverride( name = "unit", column = @Column(name = "period_unit"))
    })
    private @Nullable ExpensePeriod period;

    public Category(@Nullable Integer id, String name) {
        this(id, name, null, null);
    }

    public Category(@Nullable Integer id,
                    String name,
                    @Nullable Integer limit,
                    @Nullable ExpensePeriod period) {
        super(id, name);
        if (limit == null && period != null || limit != null && period == null) {
            throw new IllegalArgumentException("Limit and period must be both null or not null");
        }
        this.limit = limit;
        this.period = period;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", limit=" + limit +
                ", period=" + period +
                ", name='" + name + '\'' +
                '}';
    }
}
