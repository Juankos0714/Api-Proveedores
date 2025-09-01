package com.api.proveedores.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("productos")
public record Producto(
        @Id Long id,
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        String descripcion,
        @NotNull(message = "El precio es obligatorio") @DecimalMin(value = "0.0", message = "El precio debe ser mayor a 0") BigDecimal precio,
        @Min(value = 0, message = "El stock no puede ser negativo") Integer stock,
        @NotNull(message = "El proveedor es obligatorio") Long proveedorId,
        Boolean activo,
        LocalDateTime fechaRegistro
) {
    public Producto {
        if (stock == null) stock = 0;
        if (activo == null) activo = true;
        if (fechaRegistro == null) fechaRegistro = LocalDateTime.now();
    }

    public static Producto crear(String nombre, String descripcion, BigDecimal precio, Integer stock, Long proveedorId) {
        return new Producto(null, nombre, descripcion, precio, stock, proveedorId, true, LocalDateTime.now());
    }

    public Producto actualizar(String nombre, String descripcion, BigDecimal precio, Integer stock, Boolean activo) {
        return new Producto(this.id, nombre, descripcion, precio, stock, this.proveedorId, activo, this.fechaRegistro);
    }

    public Producto actualizarStock(Integer nuevoStock) {
        return new Producto(this.id, this.nombre, this.descripcion, this.precio, nuevoStock, this.proveedorId, this.activo, this.fechaRegistro);
    }
}