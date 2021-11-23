package ru.jegensomme.homeaccountant.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.jegensomme.homeaccountant.Authenticatable;
import ru.jegensomme.homeaccountant.web.validators.SafeHtml;

import javax.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

import static ru.jegensomme.homeaccountant.util.UserUtil.DEFAULT_CURRENCY;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class UserTo extends BaseTo implements Authenticatable, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    @SafeHtml
    private String name;

    @Email
    @NotBlank
    @Size(max = 100)
    @SafeHtml
    private String email;

    @NotBlank
    @Size(min = 5, max = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Min(0)
    private BigDecimal monthlyLimit = null;

    @NotNull
    private Currency currency = DEFAULT_CURRENCY;

    public UserTo(Integer id, String name, String email, String password) {
        this(id, name, email, password, null, DEFAULT_CURRENCY);
    }

    public UserTo(Integer id,
                  String name,
                  String email,
                  String password,
                  double monthlyLimit,
                  Currency currency) {
        this(id, name, email, password, BigDecimal.valueOf(monthlyLimit), currency);
    }

    public UserTo(Integer id,
                  String name,
                  String email,
                  String password,
                  BigDecimal monthlyLimit,
                  Currency currency) {
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
