package ru.jegensomme.homeaccountant.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {
    private final @NonNull ErrorType type;
    private final @NonNull String msgCode;
}
