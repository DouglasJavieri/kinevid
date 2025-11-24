package com.kinevid.backend.ModUser.Service.ServiceImpl;

import com.kinevid.backend.ModUser.Dto.UserDto;
import com.kinevid.backend.ModUser.Dto.UsernameOrEmailDto;
import com.kinevid.backend.ModUser.Entity.User;
import com.kinevid.backend.ModUser.Enums.UserStatus;
import com.kinevid.backend.ModUser.Repository.UserRepository;
import com.kinevid.backend.ModUser.Service.UserService;
import com.kinevid.backend.common.exceptions.OperationException;
import com.kinevid.backend.common.util.FormatUtil;
import com.kinevid.backend.common.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        log.info("Usuario DTO recibido: {}", userDto.toString());
        try {
            ValidationUtil.throwExceptionIfInvalidText("Usuario", userDto.getUsername(), true, 30);
            ValidationUtil.throwExceptionIfInvalidText("Nombre", userDto.getName(), true, 60);
            ValidationUtil.throwExceptionIfInvalidText("Apellidos", userDto.getLastname(), true, 60);
            ValidationUtil.throwExceptionIfInvalidText("Correo", userDto.getEmail(), true, 50);
            ValidationUtil.throwExceptionIfInvalidText("Contraseña", userDto.getPassword(), true, 30);
            ValidationUtil.throwExceptionIfInvalidText("Teléfono", userDto.getPhone(), true, 15);
            log.info("Todas las validaciones pasaron");

            String usernameLowerCase = userDto.getUsername().toLowerCase().trim();
            String emailLowerCase = userDto.getEmail().toLowerCase().trim();

            Optional<UsernameOrEmailDto> userOptional = this.userRepository.findByUsername(usernameLowerCase);
            if (userOptional.isPresent()) {
                throw new OperationException(FormatUtil.yaRegistrado("Usuario", "Usuario", usernameLowerCase));
            }
            Optional<UsernameOrEmailDto> emailOptional = this.userRepository.findByEmail(emailLowerCase);
            if (emailOptional.isPresent()) {
                throw new OperationException(FormatUtil.yaRegistrado("Email", "Email", emailLowerCase));
            }

            User user = User.builder()
                    .username(usernameLowerCase)
                    .name(userDto.getName().trim().toUpperCase())
                    .lastname(userDto.getLastname().trim().toUpperCase())
                    .email(emailLowerCase)
                    .password(userDto.getPassword())
                    .phone(userDto.getPhone())
                    .status(UserStatus.ACTIVO)
                    .build();

            User savedUser = this.userRepository.save(user);

            UserDto result = new UserDto(savedUser);
            log.info("=== USUARIO CREADO EXITOSAMENTE ===");
            return result;
        } catch (OperationException e) {
            log.error("Error controlado en createUser: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error genérico al crear usuario", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public User updateUser(UserDto userDto, Long userId) {
        log.info("Actualizando usuario Id: {} con datos: {}", userId, userDto.toString());
        ValidationUtil.throwExceptionIfInvalidText("Nombre", userDto.getName(), true, 60);
        ValidationUtil.throwExceptionIfInvalidText("Apellidos", userDto.getLastname(), true, 60);
        ValidationUtil.throwExceptionIfInvalidText("Correo", userDto.getEmail(), true, 50);
        ValidationUtil.throwExceptionIfInvalidText("Teléfono", userDto.getPhone(), true, 15);
        try {
            User user = this.userRepository.findById(userId).orElseThrow(() ->
                    new OperationException(FormatUtil.noRegistrado("Usuario", userId)));

            String usernameLowerCase = userDto.getUsername().toLowerCase().trim();
            String emailLowerCase = userDto.getEmail().toLowerCase().trim();

            Optional<UsernameOrEmailDto> userOptional = this.userRepository.findByUsername(usernameLowerCase);
            if (userOptional.isPresent() && !userOptional.get().getId().equals(user.getId())) {
                throw new OperationException(FormatUtil.yaRegistrado("Usuario", "Usuario", usernameLowerCase));
            }
            Optional<UsernameOrEmailDto> emailOptional = this.userRepository.findByEmail(emailLowerCase);
            if (userOptional.isPresent() && !userOptional.get().getId().equals(user.getId())) {
                throw new OperationException(FormatUtil.yaRegistrado("Email", "Email", emailLowerCase));
            }

            user.setUsername(usernameLowerCase);
            user.setName(userDto.getName().trim().toUpperCase());
            user.setLastname(userDto.getLastname().trim().toUpperCase());
            user.setPhone(userDto.getPhone());
            user.setPassword(userDto.getPassword());

            // Actualizar email si es diferente
            if (!user.getEmail().equalsIgnoreCase(userDto.getEmail())) {
                user.setEmail(emailLowerCase);
            }

            // Actualizar estado si se proporciona
            if (userDto.getStatus() != null) {
                user.setStatus(UserStatus.valueOf(userDto.getStatus().toUpperCase()));
            }

            return this.userRepository.save(user);
        }catch (OperationException e) {
            log.error("Error controlado en updateUser: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error genérico al actualizar usuario ID: {}", userId, e);
            throw e;
        }

    }

    @Override
    @Transactional
    public User deleteUser(Long userId) {
        try {
            User user = this.userRepository.findById(userId)
                    .orElseThrow(() -> new OperationException(FormatUtil.noRegistrado("Usuario", userId)));
            log.info("Usuario encontrado: {}", user.getUsername());
            if (user.isDeleted()) {
                throw new OperationException("El usuario ya fue eliminado anteriormente.");
            }
            user.setDeleted(true);
            user.setStatus(UserStatus.ELIMINADO);
            log.info("Usuario ID {} eliminado lógicamente. Con username {}: ", userId, user.getUsername());

            return this.userRepository.save(user);

        }catch (OperationException e) {
            log.error("Error controlado eliminando usuario {}: {}", userId, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al eliminar usuario {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("Error interno al eliminar usuario.");
        }
    }
}
