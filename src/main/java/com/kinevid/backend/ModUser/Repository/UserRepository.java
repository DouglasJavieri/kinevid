package com.kinevid.backend.ModUser.Repository;

import com.kinevid.backend.ModUser.Dto.UserDto;
import com.kinevid.backend.ModUser.Dto.UsernameOrEmailDto;
import com.kinevid.backend.ModUser.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


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

    @Query("SELECT new com.kinevid.backend.ModUser.Dto.UserDto(u) " +
            "FROM User u " +
            "WHERE u.deleted = false " +
            "AND u.status <> com.kinevid.backend.ModUser.Enums.UserStatus.ELIMINADO ")
    List<UserDto> listAllUsersNotDeleted();

    Optional<UsernameOrEmailDto> findByUsername(String username);

    Optional<UsernameOrEmailDto> findByEmail(String email);
}
