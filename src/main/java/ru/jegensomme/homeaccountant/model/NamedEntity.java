package ru.jegensomme.homeaccountant.model;

import lombok.*;
import ru.jegensomme.homeaccountant.web.View;
import ru.jegensomme.homeaccountant.web.validators.SafeHtml;

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
    @Column(name = "name", nullable = false)
    @SafeHtml(groups = View.Web.class)
    protected String name;

    public NamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
