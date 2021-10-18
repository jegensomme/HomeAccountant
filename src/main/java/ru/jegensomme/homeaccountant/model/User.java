package ru.jegensomme.homeaccountant.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class User extends NamedEntity {
    private @NotNull String email;

    private @NotNull String password;

    private @NotNull Date registered = new Date();

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
