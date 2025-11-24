package com.kinevid.backend.ModUser.Enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionRol {
    USUARIO_CREAR("USUARIO_CREAR"),
    USUARIO_EDITAR("USUARIO_EDITAR"),
    USUARIO_VER("USUARIO_VER"),
    USUARIO_ELIMINAR("USUARIO_ELIMINAR"),

    CITA_CREAR("CITA_CREAR"),
    CITA_EDITAR("CITA_EDITAR"),
    CITA_VER("CITA_VER"),
    CITA_ELIMINAR("CITA_ELIMINAR");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
