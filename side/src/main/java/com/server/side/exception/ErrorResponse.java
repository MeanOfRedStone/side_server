package com.server.side.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private String message;
    private long timestamp;

    @Builder
    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
}
