package com.pruu.pombo.controller;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.entity.Pruu;
import com.pruu.pombo.model.selector.PruuSelector;
import com.pruu.pombo.service.PruuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/pruu")
public class PruuController {

    @Autowired
    private PruuService pruuService;

    @PostMapping
    public Pruu create(@Valid @RequestBody Pruu pruu) throws PomboException {
        return ResponseEntity.ok(pruuService.create(pruu)).getBody();
    }

    @PostMapping("/filter")
    public List<Pruu> fetchWithFilter(@RequestBody PruuSelector selector) {
        return pruuService.fetchWithFilter(selector);
    }

    @PostMapping("/like")
    public ResponseEntity<Void> like(@RequestParam String userId, @RequestParam String pruuId) {
        pruuService.like(userId, pruuId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/block")
    public ResponseEntity<Void> block(@RequestParam String userId, @RequestParam String pruuId) throws PomboException {
        pruuService.block(userId, pruuId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Pruu> findAll() {
        return pruuService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pruu> findById(@PathVariable String id) throws PomboException {
        return ResponseEntity.ok(pruuService.findById(id));
    }

    @GetMapping("/user/{id}")
    public List<Pruu> fetchByUserId(@PathVariable String id) {
        return pruuService.fetchByUserId(id);
    }

}