package com.gaboot.backend.master.user.dto;

import lombok.Data;

@Data
public class UpdateUserDto extends BaseUserDto{
    private String password;
}
