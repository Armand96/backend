package com.gaboot.backend.master.user.dto;

import com.gaboot.backend.master.user.entity.User;
import org.springframework.beans.BeanUtils;

public class UserMapper {

    // ============================ create
    public static User mapToUser(CreateUserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }

    public static void mapToUser(User user, CreateUserDto userDto) {
        BeanUtils.copyProperties(userDto, user);
    }

    // ============================ base
    public static User mapToUser(BaseUserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }

    public static void mapToUser(User user, BaseUserDto userDto) {
        BeanUtils.copyProperties(userDto, user);
    }

    // ============================ update
    public static User mapToUser(UpdateUserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }

    public static void mapToUser(User user, UpdateUserDto userDto) {
        BeanUtils.copyProperties(userDto, user);
    }

}
