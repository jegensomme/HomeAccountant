package ru.jegensomme.homeaccountant.model;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Currency;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "name"}, name = "expense_category_unique_idx")
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category extends NamedEntity {
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
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

    @Column(name = "currency")
    @NotNull
    @Convert(converter = CurrencyConvertor.class)
    private Currency currency;

    public Category(@Nullable Integer id, String name, Currency currency) {
        this(id, name, null, null, currency);
    }

    public Category(@Nullable Integer id,
                    String name,
                    @Nullable Integer limit,
                    @Nullable ExpensePeriod period,
                    Currency currency) {
        super(id, name);
        if (limit == null && period != null || limit != null && period == null) {
            throw new IllegalArgumentException("Limit and period must be both null or not null");
        }
        this.limit = limit;
        this.period = period;
        this.currency = currency;
    }
}
