package org.example.expert.domain.common.image.service;

import org.example.expert.domain.common.image.dto.ImageResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ImageService {

    ImageResponse uploadImage(MultipartFile file);
}
