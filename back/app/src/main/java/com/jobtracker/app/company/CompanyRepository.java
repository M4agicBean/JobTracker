package com.jobtracker.app.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByNameIgnoreCase(String name);

    interface CompanyUsage {
        Long getCompanyId();
        long getApplicationCount();
    }

    @Query(value = """
            select c.id as companyId, count(a.id) as applicationCount
            from company c
            left join job_application a on a.company_id = c.id
            group by c.id
            """, nativeQuery = true)
    List<CompanyUsage> findApplicationCounts();
}
