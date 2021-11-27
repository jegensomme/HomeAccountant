package ru.jegensomme.homeaccountant;

import org.springframework.util.Assert;

public interface Identified {
    Integer getId();

    void setId(Integer id);

    default boolean isNew() {
        return getId() == null;
    }

    // doesn't work for hibernate lazy proxy
    default int id() {
        Assert.notNull(getId(), "Id must not be null");
        return getId();
    }
}
