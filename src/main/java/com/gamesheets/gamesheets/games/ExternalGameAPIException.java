package com.gamesheets.gamesheets.games;

public class ExternalGameAPIException extends RuntimeException {
    public ExternalGameAPIException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalGameAPIException(String message) {
        super(message);
    }
}
