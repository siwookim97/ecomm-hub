package com.likelion.ecommhub.service;

import com.likelion.ecommhub.domain.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ImageServiceTest {

    @Autowired
    ImageService imageService;

    @Autowired
    ProductService productService;

    @BeforeEach
    void init() throws Exception {
        final String fileName = "testImage"; //파일명
        final String contentType = "png"; //파일타입
        final String filePath = "src/main/resources/static/img/" + fileName + "." + contentType; //파일경로
        FileInputStream fileInputStream = new FileInputStream(filePath);

        List<MultipartFile> multipartFiles = new ArrayList<>();

        MockMultipartFile mockMultipartFile1 = new MockMultipartFile(
                "image",
                fileName + "." + contentType,
                contentType,
                fileInputStream
        );

        multipartFiles.add(mockMultipartFile1);

        imageService.uploadImages(multipartFiles);
    }

    @Test
    @DisplayName("Image List 저장 테스트")
    //@Rollback(value = false)
    void uploadImages() {
        // when
        List<Image> findImages = imageService.findImages();

        // then
        assertThat(findImages.size()).isEqualTo(1);
    }
}