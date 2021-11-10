package ru.jegensomme.homeaccountant.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.jegensomme.homeaccountant.util.converter.CurrencyConvertor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Currency;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "expenses", indexes = {
        @Index(name = "expenses_user_id_date_time_idx", columnList = "user_id, date_time")
})
public class Expense extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private @Nullable User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private @Nullable Category category;

    @Column(name = "date_time")
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "amount")
    @Min(0)
    private int amount;

    @Column(name = "currency")
    @NotNull
    @Convert(converter = CurrencyConvertor.class)
    private Currency currency;

    @Column(name = "description")
    @Size(min = 2, max = 120)
    private @Nullable String description;

    public Expense(@Nullable Integer id,
                   LocalDateTime dateTime,
                   int amount,
                   Currency currency) {
        this(id, null, dateTime, amount, currency, null);
    }

    public Expense(@Nullable Integer id,
                   LocalDateTime dateTime,
                   int amount,
                   Currency currency,
                   @Nullable String description) {
        this(id, null, dateTime, amount, currency, description);
    }

    public Expense(@Nullable Integer id,
                   @Nullable Category category,
                   LocalDateTime dateTime,
                   Currency currency,
                   int amount) {
        this(id, category, dateTime, amount, currency, null);
    }

    public Expense(@Nullable Integer id,
                   @Nullable Category category,
                   LocalDateTime dateTime,
                   int amount,
                   Currency currency,
                   @Nullable String description) {
        super(id);
        this.category = category;
        this.dateTime = dateTime;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
    }

    public @NonNull LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public @NonNull LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}
