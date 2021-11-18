package ru.jegensomme.homeaccountant;

import org.springframework.lang.NonNull;

public interface Authenticatable extends Identified {
    @NonNull String getEmail();

    @NonNull String getPassword();
}
