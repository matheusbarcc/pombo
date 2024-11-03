package com.pruu.pombo.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.pruu.pombo.exception.PomboException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class AttachmentService {

    @Autowired
    private AmazonS3 s3;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public String upload(MultipartFile file) throws AmazonClientException, PomboException, IOException {
        if (file.getContentType() == null || !file.getContentType().matches("image/(jpeg|jpg|png)")) {
            throw new PomboException("Tipo de arquivo inválido. Somente arquivos JPEG, JPG ou PNG são permitidos.", HttpStatus.BAD_REQUEST);
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());

        String uniqueFileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

        s3.putObject(bucketName, uniqueFileName, file.getInputStream(), metadata);

        return uniqueFileName;
    }
}
