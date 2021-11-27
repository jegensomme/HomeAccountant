package ru.jegensomme.homeaccountant.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import ru.jegensomme.homeaccountant.Authenticatable;
import ru.jegensomme.homeaccountant.web.validation.SafeHtml;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends NamedEntity implements Authenticatable {

    @Column(name = "email", nullable = false)
    @Email
    @NotBlank
    @Size(max = 100)
    @SafeHtml
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date registered = new Date();

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    @Column(name = "monthly_limit")
    @Min(0)
    private BigDecimal monthlyLimit;

    @Column(name = "currency", nullable = false)
    @NotNull
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") //https://stackoverflow.com/a/62848296/548473
    @OnDelete(action= OnDeleteAction.CASCADE)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Expense> expenses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Category> categories;

    public User(User user) {
        this(user.id, user.name, user.email, user.password, user.enabled, user.registered, user.monthlyLimit, user.currency, user.roles);
    }

    public User(Integer id,
                String name,
                String email,
                String password,
                Currency currency,
                Role role, Role... roles) {
        this(id, name, email, password, true, null, null, currency, EnumSet.of(role, roles));
    }

    public User(Integer id,
                String name,
                String email,
                String password,
                String monthlyLimit,
                Currency currency,
                Role role, Role... roles) {
        this(id, name, email, password, true, null, new BigDecimal(monthlyLimit), currency, EnumSet.of(role, roles));
    }

    public User(Integer id,
                String name,
                String email,
                String password,
                BigDecimal monthlyLimit,
                Currency currency,
                Role role, Role... roles) {
        this(id, name, email, password, true, null, monthlyLimit, currency, EnumSet.of(role, roles));
    }

    public User(Integer id,
                String name,
                String email,
                String password,
                boolean enabled,
                Date registered,
                BigDecimal monthlyLimit,
                Currency currency,
                Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = Objects.requireNonNullElse(registered, new Date());
        this.monthlyLimit = monthlyLimit;
        this.currency = currency;
        this.roles = roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles.isEmpty() ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public void setMonthlyLimit(BigDecimal monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public void setMonthlyLimit(String monthlyLimit) {
        this.monthlyLimit = new BigDecimal(monthlyLimit);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", registered=" + registered +
                ", enabled=" + enabled +
                ", monthlyLimit=" + monthlyLimit +
                ", currency=" + currency +
                ", roles=" + roles +
                '}';
    }
}
