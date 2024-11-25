package com.pruu.pombo.controller;

import com.pruu.pombo.auth.AuthService;
import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.dto.PublicationDTO;
import com.pruu.pombo.model.dto.UserDTO;
import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.enums.Role;
import com.pruu.pombo.model.selector.PublicationSelector;
import com.pruu.pombo.service.PublicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/publication")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private AuthService authService;

    @Operation(summary = "Creates a new publication",
            description = "Creates a new publication, the request must receive at least the user id that is creating " +
                    "and the content of the publication",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A new publication has been successfully created"),
                    @ApiResponse(responseCode = "400", description = "Wrong or forgotten information on the body")
            })
    @PostMapping
    public Publication create(@Valid @RequestBody Publication publication) throws PomboException {
        User subject = authService.getAuthenticatedUser();

        publication.getUser().setId(subject.getId());

        return publicationService.create(publication);
    }

    @Operation(summary = "Finds a specific publication",
            description = "Finds a specific publication, the publication id must be informed in the request path",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The publication information is returned"),
                    @ApiResponse(responseCode = "400", description = "Publication not found"),
            })
    @GetMapping("/{id}")
    public PublicationDTO findById(@PathVariable String id) throws PomboException {
        return publicationService.findById(id);
    }

    @Operation(summary = "Fetches publications with filters",
            description = "Fetches a group of publications based on a filter passed through the body, pagination also included",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A list of publications is returned based on the filters and page"),
                    @ApiResponse(responseCode = "400", description = "Wrong or forgotten information on the body")
            })
    @PostMapping("/filter")
    public List<PublicationDTO> fetchWithFilter(@RequestBody PublicationSelector selector) throws PomboException {
        User subject = authService.getAuthenticatedUser();

        return publicationService.fetchWithFilter(selector, subject.getId());
    }

    @Operation(summary = "Likes/Dislikes a publication",
            description = "Likes/Dislikes a publication, the user id and the publication id must be informed through the request params",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The publication has been successfully liked or disliked"),
                    @ApiResponse(responseCode = "400", description = "User or publication not found")
            })
    @PostMapping("/like/{publicationId}")
    public ResponseEntity<Void> like(@PathVariable String publicationId) throws PomboException {
        User subject = authService.getAuthenticatedUser();

        if (subject.getRole() == Role.USER) {
            publicationService.like(subject.getId(), publicationId);
            return ResponseEntity.ok().build();
        } else {
            throw new PomboException("Administradores não podem dar like.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Fetches all likes from a specific publication",
            description = "Fetches all likes from a specific publication, the publication id must be informed in the request params",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A list of all likes of the publication is returned"),
                    @ApiResponse(responseCode = "400", description = "Publication not found"),
            })
    @GetMapping("/likes/{publicationId}")
    public List<UserDTO> fetchPublicationLikes(@PathVariable String publicationId) throws PomboException {
        return publicationService.fetchPublicationLikes(publicationId);
    }

    @DeleteMapping("/{publicationId}")
    public ResponseEntity<Void> delete(@PathVariable String publicationId) throws PomboException {
        User subject = authService.getAuthenticatedUser();

        publicationService.delete(publicationId, subject.getId());

        return ResponseEntity.ok().build();
    }
}