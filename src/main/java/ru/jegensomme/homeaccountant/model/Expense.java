package ru.jegensomme.homeaccountant.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "expenses", indexes = {
        @Index(name = "expenses_user_id_date_time_idx", columnList = "user_id, date_time")
})
public class Expense extends BaseEntity {
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @javax.validation.constraints.NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private @NotNull User user;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private @Nullable Category category;

    @Column(name = "date_time")
    @javax.validation.constraints.NotNull
    private @NotNull LocalDateTime dateTime;

    @Column(name = "amount")
    @Min(0)
    private int amount;

    @Column(name = "description")
    @Size(min = 2, max = 120)
    private @Nullable String description;

    public Expense(@NotNull Integer id, @NotNull User user, @NotNull LocalDateTime dateTime, int amount) {
        this(id, user, null, dateTime, amount, null);
    }

    public Expense(@NotNull Integer id, @NotNull User user,
                   @NotNull LocalDateTime dateTime, int amount, @Nullable String description) {
        this(id, user, null, dateTime, amount, description);
    }

    public Expense(@NotNull Integer id, @NotNull User user, @Nullable Category category,
                   @NotNull LocalDateTime dateTime, int amount) {
        this(id, user, category, dateTime, amount, null);
    }

    public Expense(@NotNull Integer id, @NotNull User user, @Nullable Category category,
                   @NotNull LocalDateTime dateTime, int amount, @Nullable String description) {
        super(id);
        this.user = user;
        this.category = category;
        this.dateTime = dateTime;
        this.amount = amount;
        this.description = description;
    }
}
