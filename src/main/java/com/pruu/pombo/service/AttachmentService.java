package com.pruu.pombo.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.entity.Attachment;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachmentService {

    @Autowired
    private AmazonS3 s3;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public String uploadAndCreateAttachment(MultipartFile file) throws AmazonClientException, PomboException, IOException {
        if (file.getContentType() == null || !file.getContentType().matches("image/(jpeg|jpg|png)")) {
            throw new PomboException("Tipo de arquivo inválido. Somente arquivos JPEG, JPG ou PNG são permitidos.", HttpStatus.BAD_REQUEST);
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());

        String uniqueFileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        s3.putObject(bucketName, uniqueFileName, file.getInputStream(), metadata);

        Attachment attachment = new Attachment();
        attachment.setUrl(uniqueFileName);

        attachmentRepository.save(attachment);

        return uniqueFileName;
    }

    public String getAttachmentUrl(String attachmentId) throws PomboException {
        Attachment attachment = attachmentRepository.findById(attachmentId)
                .orElse(null);

        if(attachment == null) {
            return "";
        }

        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000); // 1 hour

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, attachment.getUrl())
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);

        URL url = s3.generatePresignedUrl(generatePresignedUrlRequest);

        return url.toString();
    }

    public Attachment updateUserProfilePicture(User user) throws PomboException {
        Attachment attachment = attachmentRepository.findById(user.getProfilePicture().getId()).orElseThrow(() -> new PomboException("Anexo não encontrado.", HttpStatus.BAD_REQUEST));

        if(attachment.getUser() != null || attachment.getPublication() != null) {
            throw new PomboException("O anexo já está atribuído à uma publicação ou usuário.", HttpStatus.BAD_REQUEST);
        }

        attachment.setUser(user);

        return attachmentRepository.save(attachment);
    }
}
