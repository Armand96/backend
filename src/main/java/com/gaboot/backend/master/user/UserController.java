package com.gaboot.backend.master.user;

import com.gaboot.backend.common.dto.ResponseDto;
import com.gaboot.backend.master.user.dto.CreateUserDto;
import com.gaboot.backend.master.user.dto.UpdateUserDto;
import com.gaboot.backend.master.user.entity.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
@Validated
public class UserController {

    private UserServiceInterface userSvc;

    @GetMapping()
    public ResponseEntity<ResponseDto<User>> findAll(){
        return ResponseEntity.ok(userSvc.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<User>> findOne(@PathVariable UUID id){
        return ResponseEntity.ok(userSvc.findOne(id));
    }

    @PostMapping()
    public ResponseEntity<ResponseDto<User>> create(
            @Valid @RequestPart("user") CreateUserDto userDto,
            @RequestPart("file") MultipartFile file
    ) {
        if (file.isEmpty()) {
            try {
                throw new BadRequestException("Please select a file to upload.");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.ok(userSvc.create(userDto, file));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<User>> update(
            @PathVariable UUID id,
            @Valid @RequestPart("user") UpdateUserDto userDto,
            @RequestPart("file") MultipartFile file
    ) {
        return ResponseEntity.ok(userSvc.update(userDto, file, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<User>> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(userSvc.delete(id));
    }

    @GetMapping("/testpassword/{password}")
    public ResponseEntity<String> test(@PathVariable String password) {
        System.out.println(password);
        final String str = userSvc.password(password);
        return ResponseEntity.ok(str);
    }

    @PostMapping("/test-upload")
    public ResponseEntity<String> testUpload(
            @RequestPart("user") CreateUserDto userDto,
            @RequestPart("file") MultipartFile file) {
        System.out.println(userDto);
        return ResponseEntity.ok("File uploaded: " + file.getOriginalFilename());
    }
}

