package com.kinevid.backend.ModUser.Service;

import com.kinevid.backend.ModUser.Dto.UserDto;
import com.kinevid.backend.ModUser.Entity.User;

import java.util.List;

public interface UserService {

    List<UserDto> listAllUsers();

    UserDto createUser(UserDto userDto);

    User updateUser(UserDto userDto, Long userId);

    User deleteUser(Long userId);

}
