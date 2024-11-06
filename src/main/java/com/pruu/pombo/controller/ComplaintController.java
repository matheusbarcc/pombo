package com.pruu.pombo.controller;

import com.pruu.pombo.auth.AuthService;
import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.dto.ReportedPublicationDTO;
import com.pruu.pombo.model.entity.Complaint;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.enums.ComplaintStatus;
import com.pruu.pombo.model.enums.Role;
import com.pruu.pombo.model.selector.ComplaintSelector;
import com.pruu.pombo.service.ComplaintService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private AuthService authService;

    @Operation(summary = "Creates a new complaint (USER ONLY)",
            description = "Creates a new complaint, the request must receive at least the user id that is making the complaint," +
                    " the publication id and the reason",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A new complaint has been successfully created."),
                    @ApiResponse(responseCode = "400", description = "The complaints publication doesnt exist."),
                    @ApiResponse(responseCode = "401", description = "Administrators cant create complaints."),
            })
    @PostMapping
    public Complaint create(@RequestBody Complaint complaint) throws PomboException {
        User subject = authService.getAuthenticatedUser();

        if (subject.getRole() == Role.USER) {
            complaint.getUser().setId(subject.getId());

            return complaintService.create(complaint);
        } else {
            throw new PomboException("Administradores não podem fazer denúncias.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Finds a specific complaint (ADMIN ONLY)",
            description = "Finds a specific complaint, the complaint id and the admin id must be informed in the request path",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The complaint information is returned"),
                    @ApiResponse(responseCode = "400", description = "Complaint not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized user")
            })
    @GetMapping("/admin/{id}")
    public Complaint findById(@PathVariable String id) throws PomboException {
        User subject = authService.getAuthenticatedUser();

        if (subject.getRole() == Role.ADMIN) {
            return complaintService.findById(id);
        } else {
            throw new PomboException("Usuário não autorizado.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Fetches complaints with filters (ADMIN ONLY)",
            description = "Fetches a group of complaints based on a filter passed through the body, pagination also included." +
                    " The admin id must be informed in the request params.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A list of complaints is returned based on the filters and page"),
                    @ApiResponse(responseCode = "400", description = "Wrong or forgotten information on the body"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized user")
            })
    @PostMapping("/admin/filter")
    public List<Complaint> fetchWithFilter(@RequestBody ComplaintSelector selector) throws PomboException {
        User subject = authService.getAuthenticatedUser();

        if (subject.getRole() == Role.ADMIN) {
            return complaintService.fetchWithFilter(selector);
        } else {
            throw new PomboException("Usuário não autorizado.", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/admin/fetch-reported-publications")
    public List<ReportedPublicationDTO> fetchReportedPublications(@RequestBody ComplaintSelector selector) throws PomboException {
        User subject = authService.getAuthenticatedUser();

        if (subject.getRole() == Role.ADMIN) {
            return complaintService.fetchReportedPublications(selector);
        } else {
            throw new PomboException("Usuário não autorizado.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Change the status from a specific complaint (ADMIN ONLY)",
            description = "Change the status from a specific complaint, if the new status is ACCEPTED, the publication " +
                    "the complaint will be blocked, if changed back to REJECTED or PENDING, the publication is unblocked.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The complaint status is updated"),
                    @ApiResponse(responseCode = "400", description = "Complaint not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized user")
            })
    @PatchMapping("/admin/update-status/{publicationId}/{newStatusString}")
    public ResponseEntity<Void> updateStatus(@PathVariable String publicationId, @PathVariable String newStatusString) throws PomboException {
        User subject = authService.getAuthenticatedUser();
        ComplaintStatus newStatus = null;

        if(newStatusString.equalsIgnoreCase("accepted")){
            newStatus = ComplaintStatus.ACCEPTED;
        } else if (newStatusString.equalsIgnoreCase("rejected")){
            newStatus = ComplaintStatus.REJECTED;
        } else if (newStatusString.equalsIgnoreCase("pending")){
            newStatus = ComplaintStatus.PENDING;
        }

        if (subject.getRole() == Role.ADMIN) {
            complaintService.updateStatus(publicationId, newStatus);
            return ResponseEntity.ok().build();
        } else {
            throw new PomboException("Usuário não autorizado.", HttpStatus.UNAUTHORIZED);
        }
    }
}
