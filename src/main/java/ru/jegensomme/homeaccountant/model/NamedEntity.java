package ru.jegensomme.homeaccountant.model;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
@MappedSuperclass
public abstract class NamedEntity extends BaseEntity {
    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name")
    protected String name;

    public NamedEntity(@Nullable Integer id, String name) {
        super(id);
        this.name = name;
    }
}
