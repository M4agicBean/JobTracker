package com.jobtracker.app.jobapplication;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public enum ApplicationStatus {
    WISHLIST, APPLIED, SCREENING, INTERVIEW, OFFER, REJECTED, GHOSTED;

    private static final Map<ApplicationStatus, Set<ApplicationStatus>> ALLOWED = new EnumMap<>(ApplicationStatus.class);

    static {
        ALLOWED.put(WISHLIST, EnumSet.of(APPLIED));
        ALLOWED.put(APPLIED, EnumSet.of(SCREENING, INTERVIEW, REJECTED, GHOSTED));
        ALLOWED.put(SCREENING, EnumSet.of(INTERVIEW, REJECTED, GHOSTED));
        ALLOWED.put(INTERVIEW, EnumSet.of(OFFER, REJECTED, GHOSTED));
        ALLOWED.put(REJECTED, EnumSet.noneOf(ApplicationStatus.class));
        ALLOWED.put(OFFER, EnumSet.noneOf(ApplicationStatus.class));
        ALLOWED.put(GHOSTED, EnumSet.of(APPLIED));
    }

    public boolean canMoveTo(ApplicationStatus target) {
        return ALLOWED.get(this).contains(target);
    }

    public Set<ApplicationStatus> allowedTargets() {
        return Set.copyOf(ALLOWED.get(this));
    }

    public boolean isTerminal() {
        return ALLOWED.get(this).isEmpty();
    }
}
