package com.jobtracker.app.techtag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TechTagRepository extends JpaRepository<TechTag, Long> {

    Optional<TechTag> findByNameIgnoreCase(String name);

    /** Projection interface - Spring builds the implementation from getter names. */
    interface TagUsage {
        Long getTagId();
        long getUsageCount();
    }

    /**
     * One grouped query for every count, rather than one count per tag.
     * Native SQL because the association is unidirectional: TechTag holds no
     * collection of applications for JPQL to join through.
     * LEFT JOIN matters - an inner join would drop unused tags.
     */
    @Query(value = """
            select t.id as tagId, count(jt.job_application_id) as usageCount
            from tech_tag t
            left join job_application_tech_tag jt on jt.tech_tag_id = t.id
            group by t.id
            """, nativeQuery = true)
    List<TagUsage> findUsageCounts();

    /** The join-table FK blocks deleting a tag that is still attached. */
    @Modifying
    @Query(value = "delete from job_application_tech_tag where tech_tag_id = :tagId",
            nativeQuery = true)
    void detachFromApplications(@Param("tagId") Long tagId);
}
