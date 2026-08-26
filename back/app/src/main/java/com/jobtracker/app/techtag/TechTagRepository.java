package com.jobtracker.app.techtag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechTagRepository extends JpaRepository<TechTag,Long> {
    Optional<TechTag> findByNameIgnoreCase(String name);
}
