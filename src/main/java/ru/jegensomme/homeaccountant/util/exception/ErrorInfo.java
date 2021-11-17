package ru.jegensomme.homeaccountant.util.exception;

import org.springframework.lang.NonNull;

import java.beans.ConstructorProperties;

public record ErrorInfo(
        @NonNull String url,
        @NonNull ErrorType type,
        @NonNull String typeMessage,
        @NonNull String[] details
) {
    public static ErrorInfo of(@NonNull CharSequence url, @NonNull ErrorType type, @NonNull String typeMessage, String... details) {
        return new ErrorInfo(url.toString(), type, typeMessage, details);
    }

    @ConstructorProperties({"url", "type", "typeMessage", "details"})
    public ErrorInfo(@NonNull String url, @NonNull ErrorType type, @NonNull String typeMessage, @NonNull String[] details) {
        this.url = url;
        this.type = type;
        this.typeMessage = typeMessage;
        this.details = details;
    }
}