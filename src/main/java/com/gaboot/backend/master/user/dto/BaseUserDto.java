package com.gaboot.backend.master.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class BaseUserDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String email;

    @NotEmpty
    private String firstname;

    @NotEmpty
    private String lastname;

    @NotNull
    private UUID roleId;

    private boolean isActive;
}
