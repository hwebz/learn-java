package com.hwebz.dreamshops.responses;

import lombok.Data;

@Data
public class ProductImageResponse {
    private Long id;
    private String fileName;
    private String fileType;
    private String downloadUrl;
}