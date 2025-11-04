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
        log.info("=== INICIO CREACIÓN USUARIO ===");
        log.info("Usuario DTO recibido: {}", userDto.toString());

        try {
            log.info("🔍 Validando campos...");

            // Log antes de cada validación
            log.info("Validando username: '{}'", userDto.getUsername());
            ValidationUtil.throwExceptionIfInvalidText("Usuario", userDto.getUsername(), true, 30);
            log.info("✅ Username válido");

            log.info("Validando name: '{}'", userDto.getName());
            ValidationUtil.throwExceptionIfInvalidText("Nombre", userDto.getName(), true, 60);
            log.info("✅ Name válido");

            log.info("Validando lastname: '{}'", userDto.getLastname());
            ValidationUtil.throwExceptionIfInvalidText("Apellidos", userDto.getLastname(), true, 60);
            log.info("✅ Lastname válido");

            log.info("Validando email: '{}'", userDto.getEmail());
            ValidationUtil.throwExceptionIfInvalidText("Correo", userDto.getEmail(), true, 50);
            log.info("✅ Email válido");

            log.info("Validando password: '{}'", userDto.getPassword() != null ? "***" : "null");
            ValidationUtil.throwExceptionIfInvalidText("Contraseña", userDto.getPassword(), true, 30);
            log.info("✅ Password válido");

            log.info("Validando phone: '{}'", userDto.getPhone());
            ValidationUtil.throwExceptionIfInvalidText("Teléfono", userDto.getPhone(), true, 15);
            log.info("✅ Phone válido");

            log.info("🎯 Todas las validaciones pasaron");

            // Procesamiento de datos
            log.info("Procesando username: '{}' -> '{}'", userDto.getUsername(), userDto.getUsername().toLowerCase().trim());
            String usernameLowerCase = userDto.getUsername().toLowerCase().trim();

            log.info("Procesando email: '{}' -> '{}'", userDto.getEmail(), userDto.getEmail().toLowerCase().trim());
            String emailLowerCase = userDto.getEmail().toLowerCase().trim();

            log.info("🔎 Verificando duplicados...");
            log.info("Buscando username: '{}'", usernameLowerCase);
            Optional<UsernameOrEmailDto> userOptional = this.userRepository.findByUsername(usernameLowerCase);
            if (userOptional.isPresent()) {
                log.warn("❌ Usuario duplicado encontrado: {}", usernameLowerCase);
                throw new OperationException(FormatUtil.yaRegistrado("Usuario", "Usuario", usernameLowerCase));
            }
            log.info("✅ Username disponible");

            log.info("Buscando email: '{}'", emailLowerCase);
            Optional<UsernameOrEmailDto> emailOptional = this.userRepository.findByEmail(emailLowerCase);
            if (emailOptional.isPresent()) {
                log.warn("❌ Email duplicado encontrado: {}", emailLowerCase);
                throw new OperationException(FormatUtil.yaRegistrado("Email", "Email", emailLowerCase));
            }
            log.info("✅ Email disponible");

            log.info("🏗️ Construyendo entidad User...");
            User user = User.builder()
                    .username(usernameLowerCase)
                    .name(userDto.getName().trim().toUpperCase())
                    .lastname(userDto.getLastname().trim().toUpperCase())
                    .email(emailLowerCase)
                    .password(userDto.getPassword())
                    .phone(userDto.getPhone())
                    .status(UserStatus.ACTIVO)
                    .build();
            log.info("Entidad User construida: {}", user.toString());

            log.info("💾 Guardando en base de datos...");
            User savedUser = this.userRepository.save(user);
            log.info("✅ Usuario guardado con ID: {}", savedUser.getId());

            log.info("🔄 Convirtiendo a DTO...");
            UserDto result = new UserDto(savedUser);
            log.info("=== USUARIO CREADO EXITOSAMENTE ===");

            return result;

        } catch (OperationException e) {
            log.error("❌ Error controlado en createUser: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("❌ Error genérico al crear usuario", e);
            throw e;
        }
    }


}
