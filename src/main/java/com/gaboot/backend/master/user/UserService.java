package com.gaboot.backend.master.user;

import com.gaboot.backend.common.constant.Storage;
import com.gaboot.backend.common.dto.ImageDto;
import com.gaboot.backend.common.dto.ResponseDto;
import com.gaboot.backend.common.exception.ResourceNotFoundException;
import com.gaboot.backend.common.service.ImageService;
import com.gaboot.backend.common.service.MappingService;
import com.gaboot.backend.master.role.RoleRepo;
import com.gaboot.backend.master.role.entity.Role;
import com.gaboot.backend.master.user.dto.*;
import com.gaboot.backend.master.user.entity.User;
// import jakarta.annotation.Resource;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
// import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

// import java.nio.file.Path;
// import java.nio.file.Paths;
import java.time.Instant;
// import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements UserServiceInterface {

    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private MappingService<User> mapServ;
    private final BCryptPasswordEncoder passwordEncoder;
    private ImageService imgService;

    @Override
    public ResponseDto<User> findAll(FilterUserDto filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
        Specification<User> spec = UserSpec.filter(filter);

        final Page<User> users = userRepo.findAllWithRoles(spec, pageable);
        final ResponseDto<User> respDto = new ResponseDto<>();
        mapServ.mapResponseSuccess(respDto, users.getContent(), "",users.getTotalPages(), ((int) users.getTotalElements()));
        return respDto;
    }

    @Override
    public ResponseDto<User> findOne(UUID id) {
        final User user = userRepo.findByIdWithRole(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found with given id: " + id)
        );
        // Hibernate.initialize(user.getRole());
        final ResponseDto<User> respDto = new ResponseDto<>();
        mapServ.mapResponseSuccess(respDto, user, "");
        return respDto;
    }

    @Override
    public ResponseDto<User> create(CreateUserDto userDto, MultipartFile file) {
        Role role = roleRepo.findById(userDto.getRoleId()).orElseThrow(
                () -> new ResourceNotFoundException("Role id not found with given id :" + userDto.getRoleId())
        );

        // upload image
        final String filename = userDto.getFirstname().toLowerCase().trim() + "_" + userDto.getLastname().toLowerCase().trim() + "_" + Instant.now().toEpochMilli();
        final String imagePath = imgService.uploadImage(file, filename, Storage.USER_DIR);
        final String imagePathThumb = imgService.uploadImageThumb(file,filename, Storage.USER_DIR_THUMB);

        User user = UserMapper.mapToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);
        System.out.println("user after assign: "+user.toString());

        if(!imagePath.isEmpty()) {
            user.setImagePath(imagePath);
            user.setThumbnailPath(imagePathThumb);
        }

        user = userRepo.save(user);
        final ResponseDto<User> respDto = new ResponseDto<>();
        mapServ.mapResponseSuccess(respDto, user, "Success Create");
        return respDto;
    }

    @Override
    public ResponseDto<User> update(UpdateUserDto userDto, MultipartFile file, UUID id) {
        Role role = roleRepo.findById(userDto.getRoleId()).orElseThrow(
                () -> new ResourceNotFoundException("Role id not found with given id :" + userDto.getRoleId())
        );
        User user = userRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found with given id: " + id)
        );
        // System.out.println(user.toString());

        if(userDto.getPassword() != null) userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        else userDto.setPassword(user.getPassword());

        UserMapper.mapToUser(user, userDto);
        user.setRole(role);

        // System.out.println(user.toString());
        // upload image
        final String filename = userDto.getFirstname().toLowerCase().trim() + "_" + userDto.getLastname().toLowerCase().trim() + "_" + Instant.now().toEpochMilli();
        final String imagePath = imgService.uploadImage(file, filename, Storage.USER_DIR);
        final String imagePathThumb = imgService.uploadImageThumb(file, filename, Storage.USER_DIR_THUMB);

        if(!imagePath.isEmpty()) {
            imgService.deleteFile(user.getImagePath());
            imgService.deleteFile(user.getThumbnailPath());
            user.setImagePath(imagePath);
            user.setThumbnailPath(imagePathThumb);
        }

        user = userRepo.save(user);
        final ResponseDto<User> respDto = new ResponseDto<>();
        mapServ.mapResponseSuccess(respDto, user, "Success update");
        return respDto;
    }

    @Override
    public ResponseDto<User> delete(UUID id) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        userRepo.deleteById(id);
        if(!user.getImagePath().isEmpty()) {
            imgService.deleteFile(user.getImagePath());
            imgService.deleteFile(user.getThumbnailPath());
        }
        final ResponseDto<User> respDto = new ResponseDto<>();
        mapServ.mapResponseSuccess(respDto, (User) null, "Success delete");
        return respDto;
    }

    @Override
    public ImageDto getImage(String filename) {
        return imgService.getImage(false, filename, Storage.USER_DIR);
    }

    @Override
    public String password(String password){
        final String hashPassword = passwordEncoder.encode(password);
        System.out.println("PARAM PASSWORD: " + hashPassword);
        User user = userRepo.findById(UUID.fromString("d27f18b0-dea0-4a56-8e16-3666c8b90fa7")).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        System.out.println("User Password: " + user.getPassword());
        System.out.println("Compared Hash Result: " + passwordEncoder.matches(password, user.getPassword()));
        return user.getPassword();
    }
}
