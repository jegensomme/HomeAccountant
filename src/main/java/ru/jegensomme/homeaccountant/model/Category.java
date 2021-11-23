package ru.jegensomme.homeaccountant.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.jegensomme.homeaccountant.web.validators.LimitPeriodConsistent;
import ru.jegensomme.homeaccountant.web.View;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "name"}, name = "categories_unique_user_name_idx")
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@LimitPeriodConsistent
public class Category extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull(groups = View.Persist.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "\"limit\"")
    @Min(1)
    @JsonInclude
    private BigDecimal limit;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "number", column = @Column(name = "period_number")),
            @AttributeOverride( name = "unit", column = @Column(name = "period_unit"))
    })
    @JsonInclude
    private Period period;

    public Category(Integer id, String name) {
        this(id, name, null, null);
    }

    public Category(Integer id,
                    String name,
                    double limit,
                    Period period) {
        this(id, name, BigDecimal.valueOf(limit), period);
    }

    public Category(Integer id,
                    String name,
                    BigDecimal limit,
                    Period period) {
        super(id, name);
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
