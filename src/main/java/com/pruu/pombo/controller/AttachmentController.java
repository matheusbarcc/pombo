package com.pruu.pombo.controller;

import com.amazonaws.AmazonClientException;
import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws AmazonClientException, PomboException, IOException {
        return attachmentService.uploadAndCreateAttachment(file);
    }
}
