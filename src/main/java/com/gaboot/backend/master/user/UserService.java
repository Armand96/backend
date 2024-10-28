package com.gaboot.backend.master.user;

import com.gaboot.backend.common.dto.ResponseDto;
import com.gaboot.backend.common.service.ImageService;
import com.gaboot.backend.common.service.MappingService;
import com.gaboot.backend.master.user.dto.CreateUserDto;
import com.gaboot.backend.master.user.dto.UpdateUserDto;
import com.gaboot.backend.master.user.dto.UserMapper;
import com.gaboot.backend.master.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserServiceInterface {

    private UserRepo userRepo;
    private MappingService<User> mapServ;
    private final BCryptPasswordEncoder passwordEncoder;
    private ImageService imgService;

    @Override
    public ResponseDto<User> findAll() {
        final List<User> users = userRepo.findAll();
        final ResponseDto<User> respDto = new ResponseDto<>();
        mapServ.mapResponseSuccess(respDto, users, "",1, users.size());
        return respDto;
    }

    @Override
    public ResponseDto<User> findOne(UUID id) {
        final User user = userRepo.findById(id).orElse(null);
        final ResponseDto<User> respDto = new ResponseDto<>();
        mapServ.mapResponseSuccess(respDto, user, "");
        return respDto;
    }

    @Override
    public ResponseDto<User> create(CreateUserDto userDto, MultipartFile file) {

        // upload image
        final String filename = userDto.getFirstname().toLowerCase().trim() + "_" + userDto.getLastname().toLowerCase().trim() + "_" + Instant.now().toEpochMilli();
        final String imagePath = imgService.uploadImage(file, filename);
        final String imagePathThumb = imgService.uploadImageThumb(file,filename);

        User user = UserMapper.mapToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

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
        User user = userRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        System.out.println(user.toString());

        if(userDto.getPassword() != null) userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        else userDto.setPassword(user.getPassword());

        UserMapper.mapToUser(user, userDto);

        System.out.println(user.toString());
        // upload image
        final String filename = userDto.getFirstname().toLowerCase().trim() + "_" + userDto.getLastname().toLowerCase().trim() + "_" + Instant.now().toEpochMilli();
        final String imagePath = imgService.uploadImage(file, filename);
        final String imagePathThumb = imgService.uploadImageThumb(file, filename);

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
