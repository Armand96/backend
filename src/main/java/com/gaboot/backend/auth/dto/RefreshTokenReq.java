package com.gaboot.backend.auth.dto;

import lombok.Data;

@Data
public class RefreshTokenReq {
    private String refreshToken;
}
