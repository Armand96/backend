package com.gaboot.backend.common.dto;

import lombok.*;
import org.springframework.core.io.Resource;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
    private Resource resource;
    private String contentType;
}
