package com.likelion.ecommhub.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.domain.Image;
import com.likelion.ecommhub.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public String saveFile(List<MultipartFile> multipartFiles, Product product) throws IOException {
        List<Image> images = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            String originalFilename = multipartFile.getOriginalFilename();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

            amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);

            Image uploadImage = Image.builder()
                    .bucketObjectUrl(amazonS3.getUrl(bucket, originalFilename).toString())
                    .build();
            uploadImage.setProduct(product);
            images.add(uploadImage);
        }

        if (!images.isEmpty()) {
            imageRepository.saveAll(images);
        }

        return "saveFile 실행 완료";
    }

    public void deleteImage(String originalFilename)  {
        amazonS3.deleteObject(bucket, originalFilename);
    }
}
