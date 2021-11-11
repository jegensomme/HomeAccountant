package ru.jegensomme.homeaccountant.to;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Currency;

import static ru.jegensomme.homeaccountant.util.UserUtil.DEFAULT_CURRENCY;

@Getter
@Setter
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
    private @Nullable Integer monthlyLimit;

    @NotNull
    private Currency currency;

    public UserTo(@Nullable Integer id, String name, String email, String password) {
        this(id, name, email, password, null, DEFAULT_CURRENCY);
    }

    public UserTo(@Nullable Integer id, String name, String email, String password, @Nullable Integer monthlyLimit, Currency currency) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.monthlyLimit = monthlyLimit;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", monthlyLimit=" + monthlyLimit +
                ", currency=" + currency +
                '}';
    }
}
