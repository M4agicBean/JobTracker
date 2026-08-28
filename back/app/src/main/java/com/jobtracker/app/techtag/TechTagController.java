package com.jobtracker.app.techtag;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/tech-tags")
public class TechTagController {

    private final TechTagService service;

    public TechTagController(TechTagService service) {
        this.service = service;
    }

    @GetMapping
    public List<TechTagResponse> list() {
        return service.list();
    }

    @PostMapping
    public ResponseEntity<TechTagResponse> create(
            @Valid @RequestBody TechTagRequest request,
            UriComponentsBuilder uriBuilder) {

        TechTagResponse created = service.create(request);
        return ResponseEntity
                .created(uriBuilder.path("/api/tech-tags/{id}").build(created.id()))
                .body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
