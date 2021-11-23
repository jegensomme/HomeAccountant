package ru.jegensomme.homeaccountant.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "expenses", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "expenses_unique_user_datetime_idx")
})
public class Expense extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "date_time")
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "amount")
    @NotNull
    @Min(0)
    private Integer amount;

    @Column(name = "description")
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    public Expense(Integer id,
                   LocalDateTime dateTime,
                   Integer amount,
                   String description) {
        this(id, null, dateTime, amount, description);
    }

    public Expense(Integer id,
                   Category category,
                   LocalDateTime dateTime,
                   Integer amount,
                   String description) {
        super(id);
        this.category = category;
        this.dateTime = dateTime;
        this.amount = amount;
        this.description = description;
    }

    public @NonNull LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", category=" + (category == null ? "" : category.getName()) +
                ", dateTime=" + dateTime +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
