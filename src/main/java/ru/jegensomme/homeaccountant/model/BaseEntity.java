package ru.jegensomme.homeaccountant.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.util.Assert;
import ru.jegensomme.homeaccountant.Identified;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class BaseEntity implements Identified {
    public static final int  START_SEQ = 100000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    protected Integer id;

    public BaseEntity (Integer id) {
        this.id = id;
    }

    // doesn't work for hibernate lazy proxy
    public int id() {
        Assert.notNull(id, "Entity must have id");
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseEntity that = (BaseEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }
}
