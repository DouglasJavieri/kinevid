package com.kinevid.backend.ModUser.Service;

import com.kinevid.backend.ModUser.Dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> listAllUsers();

    UserDto createUser(UserDto userDto);
}
