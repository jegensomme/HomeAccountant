package ru.jegensomme.homeaccountant;

public interface Authenticatable extends Identified {
    String getEmail();

    String getPassword();
}
