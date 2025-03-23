package org.example.expert.domain.common.image.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.common.image.dto.ImageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ImageService implements ImageService{

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Override
    public ImageResponse uploadImage(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String storedFilename = UUID.randomUUID() + extension;
        String key = "images/" + storedFilename;

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(file.getContentType())
                .build();
        try {
            s3Client.putObject(putRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        }catch (IOException e) {
            log.error("[IOException] 파일 업로드 실패: {}", e.getMessage());
            throw new RuntimeException("이미지 업로드 실패했습니다.", e);

        } catch (S3Exception e) {
            log.error("[S3Exception] S3 업로드 실패: {}", e.awsErrorDetails().errorMessage());
            throw new RuntimeException("S3 업로드 중 오류 발생", e);

        } catch (SdkClientException e) {
            log.error("[SdkClientException] S3 연결 실패: {}", e.getMessage());
            throw new RuntimeException("S3 연결 실패", e);
        }

        return new ImageResponse(key);
    }

    private String getFileExtension(String originalFilename) {
        if (originalFilename == null || originalFilename.lastIndexOf(".") == -1) {
            return ""; // 확장자가 없는 경우
        }
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }
}
