package com.jobtracker.app.company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CompanyRequest(
        @NotBlank @Size(max = 100) String name,
        Industry industry,
        @Size(max = 255) String website,
        @Size(max = 500) String description) {}
