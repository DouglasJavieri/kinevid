package com.kinevid.backend.ModUser.Repository;

import com.kinevid.backend.ModUser.Dto.UserDto;
import com.kinevid.backend.ModUser.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT new com.kinevid.backend.ModUser.Dto.UserDto(u) " +
            "FROM User u " +
            "WHERE u.deleted = false ")
    List<UserDto> listAllUsers();

    @Query("SELECT new com.kinevid.backend.ModUser.Dto.UserDto(u) " +
            "FROM User u " +
            "WHERE u.deleted = false " +
            "AND u.status = com.kinevid.backend.ModUser.Enums.UserStatus.ACTIVO ")
    List<UserDto> listAllUsersActive();
}
