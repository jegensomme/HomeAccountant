package ru.jegensomme.homeaccountant.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Currency;

import static ru.jegensomme.homeaccountant.util.UserUtil.DEFAULT_CURRENCY;
import static ru.jegensomme.homeaccountant.util.UserUtil.DEFAULT_MONTHLY_LIMIT;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class UserTo extends BaseTo implements Serializable {
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 5, max = 100)
    @ToString.Exclude
    private String password;

    @Min(0)
    private @Nullable Integer monthlyLimit = DEFAULT_MONTHLY_LIMIT;

    @NotNull
    private Currency defaultCurrency = DEFAULT_CURRENCY;

    public UserTo(@Nullable Integer id, String name, String email, String password, @Nullable Integer monthlyLimit, Currency defaultCurrency) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.monthlyLimit = monthlyLimit;
        this.defaultCurrency = defaultCurrency;
    }
}
