package com.gaboot.backend.master.user.dto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateUserDto extends BaseUserDto{

    @NotEmpty
    private String password;

}
