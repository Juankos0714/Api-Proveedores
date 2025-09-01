package com.api.proveedores.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductoDto(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        String descripcion,
        @NotNull(message = "El precio es obligatorio") @DecimalMin(value = "0.0", message = "El precio debe ser mayor a 0") BigDecimal precio,
        @Min(value = 0, message = "El stock no puede ser negativo") Integer stock,
        @NotNull(message = "El proveedor es obligatorio") Long proveedorId
) {}