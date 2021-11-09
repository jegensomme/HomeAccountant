package ru.jegensomme.homeaccountant.model;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;

import javax.persistence.*;

import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")
})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends NamedEntity {
    @Column(name = "email")
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @Column(name = "password")
    @NotBlank
    @Size(min = 5, max = 100)
    @ToString.Exclude
    private String password;

    @Column(name = "registered")
    @javax.validation.constraints.NotNull
    private Date registered = new Date();

    private boolean enabled = true;

    @Column(name = "monthly_limit")
    @Min(0)
    private @Nullable Integer monthlyLimit;

    @Column(name = "default_currency")
    @NotNull
    @Convert(converter = CurrencyConvertor.class)
    private Currency defaultCurrency;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Role> roles;

    public User(User user) {
        this(user.id, user.name, user.email, user.password, user.enabled, user.registered, user.monthlyLimit, user.defaultCurrency, user.roles);
    }

    public User(@Nullable Integer id,
                String name,
                String email,
                String password,
                boolean enabled,
                Currency defaultCurrency,
                Role role, Role... roles) {
        this(id, name, email, password, enabled, null, defaultCurrency, role, roles);
    }

    public User(@Nullable Integer id,
                String name,
                String email,
                String password,
                boolean enabled,
                @Nullable Integer monthlyLimit,
                Currency defaultCurrency,
                Role role, Role... roles) {
        this(id, name, email, password, enabled, null, monthlyLimit, defaultCurrency, EnumSet.of(role, roles));
    }

    public User(@Nullable Integer id,
                String name,
                String email,
                String password,
                boolean enabled,
                @Nullable Date registered,
                @Nullable Integer monthlyLimit,
                Currency defaultCurrency,
                Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = Objects.requireNonNullElse(registered, new Date());
        this.monthlyLimit = monthlyLimit;
        this.defaultCurrency = defaultCurrency;
        this.roles = roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles.isEmpty() ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }
}
