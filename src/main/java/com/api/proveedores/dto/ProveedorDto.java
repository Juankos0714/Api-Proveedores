package com.api.proveedores.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProveedorDto(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @Email(message = "El email debe ser válido") @NotBlank(message = "El email es obligatorio") String email,
        String telefono,
        String direccion
) {}