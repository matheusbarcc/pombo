package com.pruu.pombo.controller;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.entity.Pruu;
import com.pruu.pombo.service.PruuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/pruu")
public class PruuController {

    @Autowired
    private PruuService pruuService;

    @PostMapping
    public Pruu create(@Valid @RequestBody Pruu pruu) throws PomboException {
        return ResponseEntity.ok(pruuService.create(pruu)).getBody();
    }
}