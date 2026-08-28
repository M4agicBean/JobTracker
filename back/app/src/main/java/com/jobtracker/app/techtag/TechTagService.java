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

    public TechTagService(TechTagRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<TechTagResponse> list() {
        Map<Long, Long> counts = repository.findUsageCounts().stream()
                .collect(Collectors.toMap(
                        TechTagRepository.TagUsage::getTagId,
                        TechTagRepository.TagUsage::getUsageCount)
                );

        return repository.findAll(Sort.by("name")).stream()
                .map(tag -> TechTagResponse.from(tag, counts.getOrDefault(tag.getId(), 0L)))
                .toList();
    }

    @Transactional
    public TechTagResponse create(TechTagRequest request) {
        String name = request.name().trim();

        repository.findByNameIgnoreCase(name).ifPresent(existing -> {
            throw new ConflictException("Tag '%s' already exists".formatted(name));
        });

        return TechTagResponse.from(repository.save(new TechTag(name)), 0L);
    }

    @Transactional
    public void delete(Long id) {
        TechTag tag = repository.findById(id).orElseThrow(() -> new NotFoundException("Tech tag", id));

        repository.detachFromApplications(id);
        repository.delete(tag);
    }

    @Transactional
    public TechTag findOrCreate(String name) {
        String trimmed = name.trim();
        return repository.findByNameIgnoreCase(trimmed).orElseGet(() -> repository.save(new TechTag(trimmed)));
    }
}
