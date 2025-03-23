package org.example.expert.domain.common.image.dto;

import lombok.Getter;

@Getter
public class ImageResponse {


    private final String imageUrl;

    public ImageResponse(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
