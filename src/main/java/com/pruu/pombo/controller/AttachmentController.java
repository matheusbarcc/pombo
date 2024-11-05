package com.pruu.pombo.controller;

import com.amazonaws.AmazonClientException;
import com.pruu.pombo.auth.AuthService;
import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.entity.Attachment;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    AuthService authService;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws AmazonClientException, PomboException, IOException {
        return attachmentService.uploadAndCreateAttachment(file);
    }

    @GetMapping("/{id}")
    public String getAttachmentUrl(@PathVariable String id) throws PomboException {
            return attachmentService.getAttachmentUrl(id);
    }

    @PatchMapping("/update-profile-pic")
    public Attachment updateUserProfilePicture(@RequestBody User user) throws PomboException {
        User subject = authService.getAuthenticatedUser();

        user.setId(subject.getId());

        return attachmentService.updateUserProfilePicture(user);
    }
}
