package com.medco.BlogApp.Service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String saveProfileImage(MultipartFile file);
    String savePostImage(MultipartFile file);
}
