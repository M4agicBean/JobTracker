package com.jobtracker.app.techtag;

import com.jobtracker.app.common.ConflictException;
import com.jobtracker.app.common.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TechTagService {

    private final TechTagRepository repository;

    // Single constructor -> Spring injects it, no @Autowired needed.
    public TechTagService(TechTagRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<TechTagResponse> list() {
        Map<Long, Long> counts = repository.findUsageCounts().stream()
                .collect(Collectors.toMap(
                        TechTagRepository.TagUsage::getTagId,
                        TechTagRepository.TagUsage::getUsageCount));

        return repository.findAll(Sort.by("name")).stream()
                .map(tag -> TechTagResponse.from(tag, counts.getOrDefault(tag.getId(), 0L)))
                .toList();
    }

    @Transactional
    public TechTagResponse create(TechTagRequest request) {
        String name = request.name().trim();

        // Check explicitly so the client gets a real message, instead of
        // leaning on the unique constraint and a generic 409.
        repository.findByNameIgnoreCase(name).ifPresent(existing -> {
            throw new ConflictException("Tag '%s' already exists".formatted(name));
        });

        return TechTagResponse.from(repository.save(new TechTag(name)), 0L);
    }

    @Transactional
    public void delete(Long id) {
        TechTag tag = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tech tag", id));

        // Both statements in one transaction: either both happen or neither.
        repository.detachFromApplications(id);
        repository.delete(tag);
    }

    /**
     * Used by JobApplicationService. orElseGet, NOT orElse - orElse would
     * evaluate the save() eagerly even when the tag was found.
     */
    @Transactional
    public TechTag findOrCreate(String name) {
        String trimmed = name.trim();
        return repository.findByNameIgnoreCase(trimmed)
                .orElseGet(() -> repository.save(new TechTag(trimmed)));
    }
}
