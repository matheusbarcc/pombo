package com.pruu.pombo.controller;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.dto.ComplaintDTO;
import com.pruu.pombo.model.entity.Complaint;
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

    @Operation(summary = "Creates a new complaint (USER ONLY)",
            description = "Creates a new complaint, the request must receive at least the user id that is making the complaint," +
                    " the publication id and the reason",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A new complaint has been successfully created."),
                    @ApiResponse(responseCode = "400", description = "The complaints publication doesnt exist."),
                    @ApiResponse(responseCode = "401", description = "Administrators cant create complaints."),
            })
    @PostMapping
    public Complaint create(@RequestBody Complaint complaint, Authentication auth) throws PomboException {
        Jwt jwt = (Jwt) auth.getPrincipal();

        String role = (String) jwt.getClaims().get("roles");

        String userId = (String) jwt.getClaims().get("userId");

        if (role.equalsIgnoreCase("user")) {
            complaint.getUser().setId(userId);

            return complaintService.create(complaint);
        } else {
            throw new PomboException("Administradores não podem fazer denúncias.", HttpStatus.UNAUTHORIZED);
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
    public List<Complaint> fetchWithFilter(@RequestBody ComplaintSelector selector, Authentication auth) throws PomboException {
        Jwt jwt = (Jwt) auth.getPrincipal();

        String role = (String) jwt.getClaims().get("roles");

        if (role.equalsIgnoreCase("admin")) {
            return complaintService.fetchWithFilter(selector);
        } else {
            throw new PomboException("Usuário não autorizado.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Fetches all complaints (ADMIN ONLY)",
            description = "Fetches all complaints, pagination also included. The admin id must be informed in the request params.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A list of all complaints is returned."),
                    @ApiResponse(responseCode = "401", description = "Unauthorized user")
            })
    @GetMapping("/admin/all")
    public List<Complaint> fetchAll(Authentication auth) throws PomboException {
        Jwt jwt = (Jwt) auth.getPrincipal();

        String role = (String) jwt.getClaims().get("roles");

        if (role.equalsIgnoreCase("admin")) {
            return complaintService.fetchAll();
        } else {
            throw new PomboException("Usuário não autorizado.", HttpStatus.UNAUTHORIZED);
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
    public Complaint findById(@PathVariable String id, Authentication auth) throws PomboException {
        Jwt jwt = (Jwt) auth.getPrincipal();

        String role = (String) jwt.getClaims().get("roles");

        if (role.equalsIgnoreCase("admin")) {
            return complaintService.findById(id);
        } else {
            throw new PomboException("Usuário não autorizado.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Fetches a report from a specific complaint through a DTO (ADMIN ONLY)",
            description = "Fetches a report from a specific complaint, each report contains: publication id, complaint amount," +
                    " pending complaint amount and analysed complaint amount",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The complaint information is returned"),
                    @ApiResponse(responseCode = "400", description = "Complaint not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized user")
            })
    @GetMapping("/admin/dto/{id}")
    public ComplaintDTO findDTOByPublicationId(Authentication auth, @PathVariable String id) throws PomboException {
        Jwt jwt = (Jwt) auth.getPrincipal();

        String role = (String) jwt.getClaims().get("roles");

        if (role.equalsIgnoreCase("admin")) {
            return complaintService.findDTOByPublicationId(id);
        } else {
            throw new PomboException("Usuário não autorizado.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Change the status from a specific complaint (ADMIN ONLY)",
            description = "Change the status from a specific complaint, if the status is PENDING it becomes ANALYSED and vice versa.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The complaint status is updated"),
                    @ApiResponse(responseCode = "400", description = "Complaint not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized user")
            })
    @PatchMapping("/admin/update-status/{id}")
    public ResponseEntity<Void> updateStatus(Authentication auth, @PathVariable String id) throws PomboException {
        Jwt jwt = (Jwt) auth.getPrincipal();

        String role = (String) jwt.getClaims().get("roles");

        if (role.equalsIgnoreCase("admin")) {
            complaintService.updateStatus(id);
            return ResponseEntity.ok().build();
        } else {
            throw new PomboException("Usuário não autorizado.", HttpStatus.UNAUTHORIZED);
        }
    }
}
