package com.api.proveedores.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record VentaDto(
        @NotNull(message = "El producto es obligatorio") Long productoId,
        @NotNull(message = "La cantidad es obligatoria") @Min(value = 1, message = "La cantidad debe ser mayor a 0") Integer cantidad,
        @NotNull(message = "El precio unitario es obligatorio") @DecimalMin(value = "0.0", message = "El precio unitario debe ser mayor a 0") BigDecimal precioUnitario,
        String clienteNombre
) {}