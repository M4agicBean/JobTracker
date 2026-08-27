package com.jobtracker.app.common;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String entity, Object id) {
        super("%s %s not found".formatted(entity, id));
    }
}
