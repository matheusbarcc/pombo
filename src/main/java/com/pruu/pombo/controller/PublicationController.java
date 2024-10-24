package com.pruu.pombo.controller;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.dto.PublicationDTO;
import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.selector.PublicationSelector;
import com.pruu.pombo.service.PublicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Creates a new publication",
            description = "Creates a new publication, the request must receive at least the user id that is creating " +
                    "and the content of the publication",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A new publication has been successfully created"),
                    @ApiResponse(responseCode = "400", description = "Wrong or forgotten information on the body")
            })
    @PostMapping
    public Publication create(@Valid @RequestBody Publication publication) throws PomboException {
        return ResponseEntity.ok(publicationService.create(publication)).getBody();
    }

    @Operation(summary = "Fetches publications with filters",
            description = "Fetches a group of publications based on a filter passed through the body, pagination also included",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A list of publications is returned based on the filters and page"),
                    @ApiResponse(responseCode = "400", description = "Wrong or forgotten information on the body")
            })
    @PostMapping("/filter")
    public List<Publication> fetchWithFilter(@RequestBody PublicationSelector selector) {
        return publicationService.fetchWithFilter(selector);
    }

    @Operation(summary = "Likes/Dislikes a publication",
            description = "Likes/Dislikes a publication, the user id and the publication id must be informed through the request params",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The publication has been successfully liked or disliked"),
                    @ApiResponse(responseCode = "400", description = "User or publication not found")
            })
    @PostMapping("/like")
    public ResponseEntity<Void> like(@RequestParam String userId, @RequestParam String publicationId) throws PomboException {
        publicationService.like(userId, publicationId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Blocks/Unblocks a publication (ADMIN ONLY)",
            description = "Blocks/Unblocks a publication, the admin id and the publication id must be informed through the request params",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The publication has been successfully blocked or unblocked"),
                    @ApiResponse(responseCode = "400", description = "User or publication not found"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized user")
            })
    @PatchMapping("/block")
    public ResponseEntity<Void> block(@RequestParam String adminId, @RequestParam String publicationId) throws PomboException {
        publicationService.block(adminId, publicationId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Fetches all publications",
            description = "Fetches all publications",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A list of all publications is returned"),
            })
    @GetMapping
    public List<Publication> findAll() {
        return publicationService.findAll();
    }

    @Operation(summary = "Fetches all likes from a specific publication",
            description = "Fetches all likes from a specific publication, the publication id must be informed in the request params",
            responses = {
                    @ApiResponse(responseCode = "200", description = "A list of all likes of the publication is returned"),
                    @ApiResponse(responseCode = "400", description = "Publication not found"),
            })
    @GetMapping("/likes")
    public List<User> fetchPublicationLikes(@RequestParam String publicationId) throws PomboException {
        return publicationService.fetchPublicationLikes(publicationId);
    }

    @Operation(summary = "Finds a specific publication",
            description = "Finds a specific publication, the publication id must be informed in the request path",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The publication information is returned"),
                    @ApiResponse(responseCode = "400", description = "Publication not found"),
            })
    @GetMapping("/{id}")
    public ResponseEntity<Publication> findById(@PathVariable String id) throws PomboException {
        return ResponseEntity.ok(publicationService.findById(id));
    }

    @Operation(summary = "Fetches reports from all publications through a DTO",
            description = "Fetches reports from all publications, each report contains: publication id, content, user name and id" +
                    "amount of likes and amount of complaints",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The publication information is returned"),
                    @ApiResponse(responseCode = "400", description = "Publication not found"),
            })
    @GetMapping("/dto")
    public List<PublicationDTO> fetchDTO() throws PomboException {
        return publicationService.fetchDTOs();
    }

//    @DeleteMapping("/{id}")
//    public boolean delete(@PathVariable String id){
//        // TODO validate permission
//        return publicationService.delete(id);
//    }
}