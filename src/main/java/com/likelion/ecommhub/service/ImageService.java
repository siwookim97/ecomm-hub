package com.likelion.ecommhub.service;

import com.likelion.ecommhub.util.ImageStore;
import com.likelion.ecommhub.domain.Image;
import com.likelion.ecommhub.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageStore imageStore;

    @Transactional
    public List<Image> uploadImages(List<MultipartFile> multipartFiles) throws IOException {
        List<Image> images = imageStore.storeFiles(multipartFiles);
        imageRepository.saveAll(images);

        return images;
    }

    public List<Image> findImages() {
        List<Image> images = imageRepository.findAll();

        return images;
    }
}
