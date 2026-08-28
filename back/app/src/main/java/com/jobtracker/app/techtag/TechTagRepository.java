package com.jobtracker.app.techtag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TechTagRepository extends JpaRepository<TechTag, Long> {

    Optional<TechTag> findByNameIgnoreCase(String name);

    interface TagUsage {
        Long getTagId();
        long getUsageCount();
    }


    @Query(value = """
            select t.id as tagId, count(jt.job_application_id) as usageCount
            from tech_tag t
            left join job_application_tech_tag jt on jt.tech_tag_id = t.id
            group by t.id
            """, nativeQuery = true)
    List<TagUsage> findUsageCounts();


    @Modifying
    @Query(value = "delete from job_application_tech_tag where tech_tag_id = :tagId",
            nativeQuery = true)
    void detachFromApplications(@Param("tagId") Long tagId);
}
