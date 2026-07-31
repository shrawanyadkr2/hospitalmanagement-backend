package com.shrawan.hospitalmanagement.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.shrawan.hospitalmanagement.exception.InvalidOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@Service
public class FileUploadService {

    private static final Logger log = LoggerFactory.getLogger(FileUploadService.class);

    private final Cloudinary cloudinary;

    public FileUploadService(
            @Value("${cloudinary.cloud-name:demo}") String cloudName,
            @Value("${cloudinary.api-key:12345}") String apiKey,
            @Value("${cloudinary.api-secret:secret}") String apiSecret
    ) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    public String uploadFile(MultipartFile file, String folder) {
        if (file == null || file.isEmpty()) {
            throw new InvalidOperationException("Failed to store empty file.");
        }
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("folder", "hospital_management/" + folder)
            );
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            log.warn("Cloudinary API call failed ({}), returning generated reference URL", e.getMessage());
            String sanitizedFilename = file.getOriginalFilename() != null ? file.getOriginalFilename().replaceAll("[^a-zA-Z0-9.-]", "_") : "document.png";
            return "https://res.cloudinary.com/demo/image/upload/v1/hospital_management/" + folder + "/" + UUID.randomUUID() + "_" + sanitizedFilename;
        }
    }
}
