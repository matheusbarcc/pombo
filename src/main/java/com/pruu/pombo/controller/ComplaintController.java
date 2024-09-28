package com.pruu.pombo.controller;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.dto.ComplaintDTO;
import com.pruu.pombo.model.entity.Complaint;
import com.pruu.pombo.model.selector.ComplaintSelector;
import com.pruu.pombo.service.ComplaintService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Operation(summary = "Creates a new complaint",
            description = "Creates a new complaint, the request must receive at least the user id that is making the complaint," +
                    " the publication id and the reason",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A new complaint has been successfully created"),
                    // TODO - Treat error if publication or user is wrong or miss informed
            })
    @PostMapping
    public Complaint create(@RequestBody Complaint complaint) throws PomboException {
        return complaintService.create(complaint);
    }

    @Operation(summary = "Fetches complaints with filters (ADMIN ONLY)",
            description = "Fetches a group of complaints based on a filter passed through the body, pagination also included." +
                    " The admin id must be informed in the request params.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A list of complaints is returned based on the filters and page"),
                    @ApiResponse(responseCode = "400", description = "Wrong or forgotten information on the body"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized user")
            })
    @PostMapping("/filter")
    public List<Complaint> fetchWithFilter(@RequestBody ComplaintSelector selector, @RequestParam String adminId) throws PomboException {
        return complaintService.fetchWithFilter(selector, adminId);
    }

    @Operation(summary = "Fetches all complaints (ADMIN ONLY)",
            description = "Fetches all complaints, pagination also included. The admin id must be informed in the request params.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A list of all complaints is returned."),
                    @ApiResponse(responseCode = "401", description = "Unauthorized user")
            })
    @GetMapping
    public List<Complaint> findAll(@RequestParam String adminId) throws PomboException {
        return complaintService.findAll(adminId);
    }

    @Operation(summary = "Finds a specific complaint (ADMIN ONLY)",
            description = "Finds a specific complaint, the complaint id and the admin id must be informed in the request path",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The complaint information is returned"),
                    @ApiResponse(responseCode = "400", description = "Complaint not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized user")
            })
    @GetMapping("/{id}")
    public Complaint findById(@PathVariable String id, @RequestParam String adminId) throws PomboException {
        return complaintService.findById(id, adminId);
    }

    @Operation(summary = "Fetches a report from a specific complaint through a DTO (ADMIN ONLY)",
            description = "Fetches a report from a specific complaint, each report contains: publication id, complaint amount," +
                    " pending complaint amount and analysed complaint amount",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The complaint information is returned"),
                    @ApiResponse(responseCode = "400", description = "Complaint not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized user")
            })
    @GetMapping("/dto")
    public ComplaintDTO findDTOByPublicationId(@RequestParam String adminId, @RequestParam String publicationId) throws PomboException {
        return complaintService.findDTOByPublicationId(adminId, publicationId);
    }

    @Operation(summary = "Deletes a complaint",
            description = "Deletes a complaint, the complaint id and the user id must be informed in the request params," +
                    " only the user who created the complaint has the permission to delete it",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The complaint has been successfully deleted"),
                    @ApiResponse(responseCode = "400", description = "Complaint not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized user")
            })
    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable String id) {
        // TODO validate permission
        return complaintService.delete(id);
    }

}
