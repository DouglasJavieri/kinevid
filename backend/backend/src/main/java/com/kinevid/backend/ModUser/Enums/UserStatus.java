package com.kinevid.backend.ModUser.Enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    ACTIVO("ACTIVO"),
    INACTIVO("INACTIVO"),
    BLOQUEADO("BLOQUEADO"),
    ELIMINADO("ELIMINADO");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
