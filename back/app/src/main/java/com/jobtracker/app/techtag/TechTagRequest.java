package com.jobtracker.app.techtag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TechTagRequest(@NotBlank @Size(max = 50) String name) {}
