package com.api.proveedores.controller;

import com.api.proveedores.dto.VentaDto;
import com.api.proveedores.model.Venta;
import com.api.proveedores.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public Flux<Venta> obtenerTodasLasVentas() {
        return ventaService.obtenerTodasLasVentas();
    }

    @GetMapping("/{id}")
    public Mono<Venta> obtenerVentaPorId(@PathVariable Long id) {
        return ventaService.obtenerVentaPorId(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Venta no encontrada")));
    }

    @GetMapping("/producto/{productoId}")
    public Flux<Venta> obtenerVentasPorProducto(@PathVariable Long productoId) {
        return ventaService.obtenerVentasPorProducto(productoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Venta> crearVenta(@Valid @RequestBody VentaDto ventaDto) {
        return ventaService.crearVenta(ventaDto);
    }

    @GetMapping("/periodo")
    public Flux<Venta> obtenerVentasEnPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        return ventaService.obtenerVentasEnPeriodo(fechaInicio, fechaFin);
    }

    @GetMapping("/total-periodo")
    public Mono<BigDecimal> calcularTotalVentasEnPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        return ventaService.calcularTotalVentasEnPeriodo(fechaInicio, fechaFin);
    }

    @GetMapping("/cliente")
    public Flux<Venta> buscarVentasPorCliente(@RequestParam String nombre) {
        return ventaService.buscarVentasPorCliente(nombre);
    }
}