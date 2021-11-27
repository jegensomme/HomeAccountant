package ru.jegensomme.homeaccountant.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.jegensomme.homeaccountant.util.DateTimeUtil;
import ru.jegensomme.homeaccountant.web.validation.SafeHtml;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExpenseTo extends BaseTo {

    @NotNull
    private String category;

    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime dateTime;

    @NotNull
    @Min(0)
    private BigDecimal amount;

    @NotBlank
    @Size(min = 2, max = 120)
    @SafeHtml
    private String description;

    private Boolean excess;

    public ExpenseTo(Integer id, String category, LocalDateTime dateTime, String amount, String description) {
        this(id, category, dateTime, new BigDecimal(amount), description);
    }

    public ExpenseTo(Integer id, String category, LocalDateTime dateTime, BigDecimal amount, String description) {
        this(id, category, dateTime, amount, description, null);
    }

    public ExpenseTo(Integer id, String category, LocalDateTime dateTime, BigDecimal amount, String description, Boolean excess) {
        super(id);
        this.category = category;
        this.dateTime = dateTime;
        this.amount = amount;
        this.description = description;
        this.excess = excess;
    }

    @Override
    public String toString() {
        return "ExpenseTo{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", dateTime=" + dateTime +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", excess=" + excess +
                '}';
    }
}
