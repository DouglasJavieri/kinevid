package com.kinevid.backend.ModUser.Service.ServiceImpl;

import com.kinevid.backend.ModUser.Dto.UserDto;
import com.kinevid.backend.ModUser.Repository.UserRepository;
import com.kinevid.backend.ModUser.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> listAllUsers() {
        try {
            return userRepository.listAllUsers();
        } catch (Exception e) {
            log.error("Error en listAllUsers - Error: {}", e.getMessage(), e);
            throw new RuntimeException("No se pudo obtener la lista de usuarios", e);
        }
    }
}
