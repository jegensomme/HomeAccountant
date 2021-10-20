package ru.jegensomme.homeaccountant.model;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")
})
public class User extends NamedEntity {
    @Column(name = "email")
    @Email
    @NotBlank
    @Size(max = 100)
    private @NotNull String email;

    @Column(name = "password")
    @NotBlank
    @Size(min = 5, max = 100)
    private @NotNull String password;

    @Column(name = "registered")
    @javax.validation.constraints.NotNull
    private @NotNull Date registered = new Date();

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private @NotNull Set<Role> roles;

    public User(@NotNull Integer id,
                @NotNull String name,
                @NotNull String email,
                @NotNull String password,
                @NotNull Role role,
                @NotNull Role... roles) {
        this(id, name, email, password, EnumSet.of(role, roles));
    }

    public User(@NotNull Integer id,
                @NotNull String name,
                @NotNull String email,
                @NotNull String password,
                @NotNull Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
