package com.kinevid.backend.ModUser.Controller;



import com.kinevid.backend.ModUser.Dto.UserDto;
import com.kinevid.backend.ModUser.Entity.User;
import com.kinevid.backend.ModUser.Service.UserService;
import com.kinevid.backend.common.constants.ApiConstants;
import com.kinevid.backend.common.exceptions.ApiResponseException;
import com.kinevid.backend.common.exceptions.OperationException;
import com.kinevid.backend.common.response.ResponseBody;
import com.kinevid.backend.common.util.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list-all")
    @Operation(summary = "Obtener todos los usuarios",
            description = "Retorna todos los usuarios sin paginación",
            tags = {"users"},
            responses = {
                    @ApiResponse(description = "Operación exitosa", responseCode = "200", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Acceso Denegado", content = @Content(schema = @Schema(hidden = true))),
            })
    public ResponseEntity<ResponseBody<List<UserDto>>> listAllUsers() {
        try {
            List<UserDto> users = this.userService.listAllUsers();
            return ok(ApiUtil.buildResponseWithDefaults(users));
        } catch (OperationException e) {
            log.error("Error: Se produjo un error controlado al ejecutar listAllUsers, Mensaje: {}", e.getMessage());
            throw ApiResponseException.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error: Se produjo un error genérico al ejecutar listAllUsers: ", e);
            throw ApiResponseException.serverError(ApiConstants.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create-user")
    @Operation(summary = "Crear nuevo usuario",
            description = "Crea un nuevo usuario en el sistema",
            tags = {"users"},
            responses = {
                    @ApiResponse(description = "Operación satisfactorio", responseCode = "200", content = @Content(mediaType = "application/json")),
                    @ApiResponse(description = "Usuario creado", responseCode = "201", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Acceso Denegado", content = @Content(schema = @Schema(hidden = true))),
            })
    public ResponseEntity<ResponseBody<UserDto>> createUser(@RequestBody UserDto userDto) {
        try {
            log.info("**************** {}", userDto);
            UserDto createdUser = this.userService.createUser(userDto);
            return ok(ApiUtil.buildResponseWithDefaults(createdUser));
        } catch (OperationException e) {
            log.error("Error: Se produjo un error controlado al ejecutar createUser, Mensaje: {}", e.getMessage());
            throw ApiResponseException.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error: Se produjo un error genérico al ejecutar createUser: ", e);
            throw ApiResponseException.serverError(ApiConstants.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-user/{userId}")
    @Operation(summary = "Actualizar usuario",
            description = "Actualiza un usuario existente en el sistema",
            tags = {"users"},
            responses = {
                    @ApiResponse(description = "Operación satisfactorio", responseCode = "200", content = @Content(mediaType = "application/json")),
                    @ApiResponse(description = "Usuario Actualizado", responseCode = "201", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Fallo de autentificación", content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "403", description = "Acceso Denegado", content = @Content(schema = @Schema(hidden = true))),
            })
    public ResponseEntity<ResponseBody<User>> updateUser(@RequestBody UserDto userDto,
                                                            @PathVariable("userId") Long userId) {
        try {
            User updatedUser = this.userService.updateUser(userDto, userId);
            return ok(ApiUtil.buildResponseWithDefaults(updatedUser));
        } catch (OperationException e) {
            log.error("Error controlado en updateUser: {}", e.getMessage());
            throw ApiResponseException.badRequest(e.getMessage());
        } catch (Exception e) {
            log.error("Error genérico en updateUser: ", e);
            throw ApiResponseException.serverError(ApiConstants.INTERNAL_SERVER_ERROR);
        }
    }

}
