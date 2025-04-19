package com.server.side.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(FileStorageException.class)
    public String handleFileStorageException(FileStorageException ex, Locale locale) {
        return messageSource.getMessage(ex.getMessage(), null, locale);
    }

    @ExceptionHandler(FileValidationException.class)
    public String handleFileValidationException(FileValidationException ex, Locale locale) {
        return messageSource.getMessage(ex.getMessage(), null, locale);
    }
}
