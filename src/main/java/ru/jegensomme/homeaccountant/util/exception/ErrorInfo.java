package ru.jegensomme.homeaccountant.util.exception;

import org.springframework.lang.NonNull;

public record ErrorInfo(
        @NonNull String url,
        @NonNull ErrorType type,
        @NonNull String typeMessage,
        @NonNull String[] details
) {
    public static ErrorInfo of(@NonNull String url, @NonNull ErrorType type, @NonNull String typeMessage, String... details) {
        return new ErrorInfo(url, type, typeMessage, details);
    }
}