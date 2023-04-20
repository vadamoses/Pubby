package com.vada.contollers.dto;

import lombok.Data;

@Data
public class ValidationResponse {
    private long userId;
    private String name;
    private boolean isValid;
}
