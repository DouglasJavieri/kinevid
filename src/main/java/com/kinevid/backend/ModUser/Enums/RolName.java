package com.kinevid.backend.ModUser.Enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RolName {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_FISIOTERAPEUTA("ROLE_FISIOTERAPEUTA"),
    ROLE_PACIENTE("ROLE_PACIENTE"),
    ROLE_ASISTENTE("ROLE_ASISTENTE"),
    ROLE_RECEPCION("ROLE_RECEPCION");

    private final String value;

    @Override
    public String toString() {
        return value;
    }
}
