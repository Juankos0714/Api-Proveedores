package com.api.proveedores.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("ventas")
public record Venta(
        @Id Long id,
        @NotNull(message = "El producto es obligatorio") Long productoId,
        @NotNull(message = "La cantidad es obligatoria") @Min(value = 1, message = "La cantidad debe ser mayor a 0") Integer cantidad,
        @NotNull(message = "El precio unitario es obligatorio") @DecimalMin(value = "0.0", message = "El precio unitario debe ser mayor a 0") BigDecimal precioUnitario,
        BigDecimal total,
        LocalDateTime fechaVenta,
        String clienteNombre
) {
    public Venta {
        if (total == null && precioUnitario != null && cantidad != null) {
            total = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        }
        if (fechaVenta == null) fechaVenta = LocalDateTime.now();
    }

    public static Venta crear(Long productoId, Integer cantidad, BigDecimal precioUnitario, String clienteNombre) {
        BigDecimal total = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        return new Venta(null, productoId, cantidad, precioUnitario, total, LocalDateTime.now(), clienteNombre);
    }
}