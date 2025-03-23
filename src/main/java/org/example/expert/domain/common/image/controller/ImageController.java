package org.example.expert.domain.common.image.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.image.dto.ImageResponse;
import org.example.expert.domain.common.image.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/images")
    public ResponseEntity<ImageResponse> uploadImage(@RequestPart MultipartFile file) {

        return ResponseEntity.ok(imageService.uploadImage(file));
    }
}
