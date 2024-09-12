package com.pruu.pombo.controller;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.selector.PublicationSelector;
import com.pruu.pombo.service.PublicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/publication")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @PostMapping
    public Publication create(@Valid @RequestBody Publication publication) throws PomboException {
        return ResponseEntity.ok(publicationService.create(publication)).getBody();
    }

    @PostMapping("/filter")
    public List<Publication> fetchWithFilter(@RequestBody PublicationSelector selector) {
        return publicationService.fetchWithFilter(selector);
    }

    @PostMapping("/like")
    public ResponseEntity<Void> like(@RequestParam String userId, @RequestParam String publicationId) throws PomboException {
        publicationService.like(userId, publicationId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/block")
    public ResponseEntity<Void> block(@RequestParam String userId, @RequestParam String publicationId) throws PomboException {
        publicationService.block(userId, publicationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Publication> findAll() {
        return publicationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publication> findById(@PathVariable String id) throws PomboException {
        return ResponseEntity.ok(publicationService.findById(id));
    }
}