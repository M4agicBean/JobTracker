package com.jobtracker.app.common;

import com.jobtracker.app.jobapplication.ApplicationStatus;
import lombok.Getter;

@Getter
public class InvalidTransitionException  extends RuntimeException {

    private final ApplicationStatus from;
    private final ApplicationStatus to;

    public InvalidTransitionException(ApplicationStatus from, ApplicationStatus to) {
        super("Invalid transition from %s to %s".formatted(from, to));
        this.from = from;
        this.to = to;
    }

}
