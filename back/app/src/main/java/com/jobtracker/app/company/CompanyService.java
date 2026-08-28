package com.jobtracker.app.company;

import com.jobtracker.app.common.ConflictException;
import com.jobtracker.app.common.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository repository;

    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<CompanyResponse> list() {
        Map<Long, Long> counts = counts();
        return repository.findAll(Sort.by("name")).stream()
                .map(c -> CompanyResponse.from(c, counts.getOrDefault(c.getId(), 0L)))
                .toList();
    }

    @Transactional(readOnly = true)
    public CompanyResponse get(Long id) {
        return CompanyResponse.from(findOrThrow(id), counts().getOrDefault(id, 0L));
    }

    @Transactional
    public CompanyResponse create(CompanyRequest request) {
        String name = request.name().trim();
        repository.findByNameIgnoreCase(name).ifPresent(existing -> {
            throw new ConflictException("Company '%s' already exists".formatted(name));
        });

        Company company = new Company(name);
        apply(company, request);
        return CompanyResponse.from(repository.save(company), 0L);
    }

    @Transactional
    public CompanyResponse update(Long id, CompanyRequest request) {
        Company company = findOrThrow(id);
        String name = request.name().trim();

        repository.findByNameIgnoreCase(name)
                .filter(other -> !other.getId().equals(id))
                .ifPresent(other -> {
                    throw new ConflictException("Company '%s' already exists".formatted(name));
                });

        company.setName(name);
        apply(company, request);

        return CompanyResponse.from(company, counts().getOrDefault(id, 0L));
    }

    @Transactional
    public void delete(Long id) {
        Company company = findOrThrow(id);
        long used = counts().getOrDefault(id, 0L);
        if (used > 0) {
            throw new ConflictException(
                    "%s still has %d application(s). Remove those first.".formatted(company.getName(), used)
            );
        }
        repository.delete(company);
    }

    @Transactional
    public Company findOrCreate(String name) {
        String trimmed = name.trim();
        return repository.findByNameIgnoreCase(trimmed).orElseGet(() -> repository.save(new Company(trimmed)));
    }

    @Transactional(readOnly = true)
    public Company getEntity(Long id) {
        return findOrThrow(id);
    }


    private Company findOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Company", id));
    }

    private void apply(Company company, CompanyRequest request) {
        company.setIndustry(request.industry());
        company.setWebsite(trimToNull(request.website()));
        company.setDescription(trimToNull(request.description()));
    }

    private Map<Long, Long> counts() {
        return repository.findApplicationCounts().stream()
                .collect(Collectors.toMap(
                        CompanyRepository.CompanyUsage::getCompanyId,
                        CompanyRepository.CompanyUsage::getApplicationCount));
    }

    private static String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
