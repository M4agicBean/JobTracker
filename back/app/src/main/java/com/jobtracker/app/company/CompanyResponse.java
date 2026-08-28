package com.jobtracker.app.company;

public record CompanyResponse(
        Long id,
        String name,
        Industry industry,
        String website,
        String description,
        long applicationCount) {

    public static CompanyResponse from(Company company, long applicationCount) {
        return new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getIndustry(),
                company.getWebsite(),
                company.getDescription(),
                applicationCount);
    }

    public record Ref(Long id, String name, Industry industry) {
        public static Ref from(Company company) {
            return new Ref(company.getId(), company.getName(), company.getIndustry());
        }
    }
}
