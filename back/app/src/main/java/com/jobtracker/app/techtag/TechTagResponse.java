package com.jobtracker.app.techtag;

public record TechTagResponse(Long id, String name, long usageCount) {

    public static TechTagResponse from(TechTag tag, long usageCount) {
        return new TechTagResponse(tag.getId(), tag.getName(), usageCount);
    }
}
