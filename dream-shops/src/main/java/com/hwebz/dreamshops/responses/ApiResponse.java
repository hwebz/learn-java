package com.hwebz.dreamshops.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse {
    private boolean success = true;
    private String message;
    private Object data;
}
