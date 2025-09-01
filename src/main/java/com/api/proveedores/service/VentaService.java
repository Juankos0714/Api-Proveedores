package com.api.proveedores.service;

import com.api.proveedores.dto.VentaDto;
import com.api.proveedores.model.Venta;
import com.api.proveedores.repository.VentaRepository;
import com.api.proveedores.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoService productoService;

    public Flux<Venta> obtenerTodasLasVentas() {
        return ventaRepository.findAll();
    }

    public Mono<Venta> obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public Flux<Venta> obtenerVentasPorProducto(Long productoId) {
        return ventaRepository.findByProductoId(productoId);
    }

    public Mono<Venta> crearVenta(VentaDto ventaDto) {
        return productoRepository.findById(ventaDto.productoId())
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado")))
                .flatMap(producto -> {
                    if (!producto.activo()) {
                        return Mono.error(new RuntimeException("El producto no está activo"));
                    }
                    if (producto.stock() < ventaDto.cantidad()) {
                        return Mono.error(new RuntimeException("Stock insuficiente. Stock disponible: " + producto.stock()));
                    }

                    Venta nuevaVenta = Venta.crear(
                            ventaDto.productoId(),
                            ventaDto.cantidad(),
                            ventaDto.precioUnitario(),
                            ventaDto.clienteNombre()
                    );

                    return ventaRepository.save(nuevaVenta)
                            .flatMap(ventaGuardada -> {
                                // Actualizar stock del producto
                                Integer nuevoStock = producto.stock() - ventaDto.cantidad();
                                return productoService.actualizarStock(producto.id(), nuevoStock)
                                        .thenReturn(ventaGuardada);
                            });
                });
    }

    public Flux<Venta> obtenerVentasEnPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ventaRepository.findByFechaBetween(fechaInicio, fechaFin);
    }

    public Mono<BigDecimal> calcularTotalVentasEnPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ventaRepository.calcularTotalVentasEnPeriodo(fechaInicio, fechaFin)
                .defaultIfEmpty(BigDecimal.ZERO);
    }

    public Flux<Venta> buscarVentasPorCliente(String cliente) {
        return ventaRepository.findByClienteNombreContaining(cliente);
    }
}