package com.pruu.pombo.controller;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.entity.Complaint;
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

    @GetMapping
    public List<Complaint> findAll(@RequestParam String userId) throws PomboException {
        return complaintService.findAll(userId);
    }

    @GetMapping("/{id}")
    public Complaint findById(@PathVariable String id, @RequestParam String userId) throws PomboException {
        return complaintService.findById(id, userId);
    }


}
