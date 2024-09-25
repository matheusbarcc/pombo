package com.pruu.pombo.controller;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.dto.ComplaintDTO;
import com.pruu.pombo.model.entity.Complaint;
import com.pruu.pombo.model.selector.ComplaintSelector;
import com.pruu.pombo.service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping
    public Complaint create(@Valid @RequestBody Complaint complaint) {
        return complaintService.create(complaint);
    }

    @PostMapping("/filter")
    public List<Complaint> fetchWithFilter(@RequestBody ComplaintSelector selector) {
        return complaintService.fetchWithFilter(selector);
    }

    @GetMapping
    public List<Complaint> findAll(@RequestParam String userId) throws PomboException {
        return complaintService.findAll(userId);
    }

    @GetMapping("/{id}")
    public Complaint findById(@PathVariable String id, @RequestParam String userId) throws PomboException {
        return complaintService.findById(id, userId);
    }

    @GetMapping("/dto")
    public ComplaintDTO findDTOByPublicationId(@RequestParam String userId, @RequestParam String publicationId) throws PomboException {
        return complaintService.findDTOByPublicationId(userId, publicationId);
    }

    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable String id) {
        // TODO validate permission
        return complaintService.delete(id);
    }

}
