package com.server.side.exception;

public class FileStorageException extends RuntimeException {
    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
