package ru.jegensomme.homeaccountant.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import ru.jegensomme.homeaccountant.web.validation.LimitPeriodConsistent;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

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
    @JoinColumn(name = "user_id", nullable = false)
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Expense> expenses;

    public Category(Integer id, String name) {
        this(id, name, null, null);
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
