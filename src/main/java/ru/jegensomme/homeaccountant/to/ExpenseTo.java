package ru.jegensomme.homeaccountant.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import ru.jegensomme.homeaccountant.util.DateTimeUtil;
import ru.jegensomme.homeaccountant.web.validators.SafeHtml;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExpenseTo extends BaseTo {

    @NotNull
    private String categoryName;

    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime dateTime;

    @NotNull
    @Min(0)
    private Integer amount;

    @NotBlank
    @Size(min = 2, max = 120)
    @SafeHtml
    private String description;

    public ExpenseTo(@Nullable Integer id,
                     String categoryName,
                     LocalDateTime dateTime,
                     Integer amount,
                     String description) {
        super(id);
        this.categoryName = categoryName;
        this.dateTime = dateTime;
        this.amount = amount;
        this.description = description;
    }

    @Override
    public String toString() {
        return "ExpenseTo{" +
                "id=" + id +
                ", categoryName=" + categoryName +
                ", dateTime=" + dateTime +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
