package com.api.proveedores.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Table("proveedores")
public record Proveedor(
        @Id Long id,
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @Email(message = "El email debe ser válido") @NotBlank(message = "El email es obligatorio") String email,
        String telefono,
        String direccion,
        Boolean activo,
        LocalDateTime fechaRegistro
) {
    public Proveedor {
        if (activo == null) activo = true;
        if (fechaRegistro == null) fechaRegistro = LocalDateTime.now();
    }

    public static Proveedor crear(String nombre, String email, String telefono, String direccion) {
        return new Proveedor(null, nombre, email, telefono, direccion, true, LocalDateTime.now());
    }

    public Proveedor actualizar(String nombre, String email, String telefono, String direccion, Boolean activo) {
        return new Proveedor(this.id, nombre, email, telefono, direccion, activo, this.fechaRegistro);
    }
}