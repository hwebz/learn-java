package com.example.shopapp.controllers;

import com.example.shopapp.dtos.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @GetMapping()
    public ResponseEntity<String> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok("Get Products");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProduct(
            @PathVariable("id") String productId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body("Get Product with id = " + productId);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProduct(
            @Valid @ModelAttribute ProductDTO productDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            List<MultipartFile> files = productDTO.getFiles();
            files = files == null ? new ArrayList<>() : files;
            for (MultipartFile file: files) {
                if (file != null) {
                    // Ignore the case of there is no files passed
                    if (file.getSize() == 0) {
                        continue;
                    }
                    // > 10MB
                    if (file.getSize() > 10 * 1024 * 1024) {
                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large! Maximum size is 10MB");
                    }
                    String contentType = file.getContentType();
                    if (contentType == null || !contentType.startsWith("image/")) {
                        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File is not an image");
                    }
                    try {
                        String fileName = uploadFile(file);
                    } catch (IOException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
                    }
                }
            }

            return ResponseEntity.ok("Product created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private String uploadFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString() + "_" + filename;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(), newFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return newFileName;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @RequestParam("id") String productId
    ) {
        return ResponseEntity.ok("Delete Product with id = " + productId);
    }
}