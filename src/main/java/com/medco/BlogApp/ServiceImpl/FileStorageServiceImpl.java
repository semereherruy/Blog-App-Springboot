package com.medco.BlogApp.ServiceImpl;

import com.medco.BlogApp.Service.FileStorageService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final String profileDir = "uploads/profile-pictures";
    private final String postDir    = "uploads/post-images";

    //Allowed MIME types
    private static final Set<String> ALLOWED_TYPES = Set.of(
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE
    );

    //Allowed file extensions
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
            ".jpg", ".jpeg", ".png"
    );

    @Override
    public String saveProfileImage(MultipartFile file) {
        validateImageFile(file);
        return store(file, profileDir, "/profile-pictures/");
    }

    @Override
    public String savePostImage(MultipartFile file) {
        validateImageFile(file);
        return store(file, postDir, "/post-images/");
    }

    private void validateImageFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload empty file.");
        }
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("Invalid file type. Only JPG, PNG are allowed.");
        }
        if (file.getSize() > 5 * 1024 * 1024) { // 5MB limit
            throw new IllegalArgumentException("File size exceeds the limit of 5MB.");
        }
        String original = file.getOriginalFilename();
        if (original != null) {
            String lower = original.toLowerCase();
            if (!(lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png"))) {
                throw new IllegalArgumentException("File extension not allowed. Only JPG, PNG are allowed.");
            }
        }
    }

    private String store(MultipartFile file, String uploadDir, String webPathPrefix) {
        try {
            // generate unique filename
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path target = Paths.get(uploadDir, filename);
            Files.createDirectories(target.getParent());
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            // return the URL part your frontend will use to fetch it
            return webPathPrefix + filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }
}
