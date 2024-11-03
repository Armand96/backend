package com.gaboot.backend.master.user;

import com.gaboot.backend.common.dto.ImageDto;
import com.gaboot.backend.common.dto.ResponseDto;
import com.gaboot.backend.master.user.dto.CreateUserDto;
import com.gaboot.backend.master.user.dto.FilterUserDto;
import com.gaboot.backend.master.user.dto.UpdateUserDto;
import com.gaboot.backend.master.user.entity.User;
// import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserServiceInterface {

    ResponseDto<User> findAll(FilterUserDto filter);
    ResponseDto<User> findOne(UUID id);
    ResponseDto<User> create(CreateUserDto userDto, MultipartFile file);
    ResponseDto<User> update(UpdateUserDto userDto, MultipartFile file, UUID id);
    ResponseDto<User> delete(UUID id);
    ImageDto getImage(String filename);
    String password(String password);

}
