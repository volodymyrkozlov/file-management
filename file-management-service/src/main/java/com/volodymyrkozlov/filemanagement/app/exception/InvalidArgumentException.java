package com.volodymyrkozlov.filemanagement.app.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(final String message) {
        super(message);
    }

    public InvalidArgumentException(final Throwable throwable) {
        super(throwable);
    }
}
