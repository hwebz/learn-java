package com.hwebz.dreamshops.services.image;

import com.hwebz.dreamshops.dtos.ImageDTO;
import com.hwebz.dreamshops.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDTO> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
