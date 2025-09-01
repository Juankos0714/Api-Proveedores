package com.api.proveedores.repository;

import com.api.proveedores.model.Venta;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Repository
public interface VentaRepository extends ReactiveCrudRepository<Venta, Long> {
    
    Flux<Venta> findByProductoId(Long productoId);
    
    @Query("SELECT * FROM ventas WHERE fecha_venta BETWEEN :fechaInicio AND :fechaFin ORDER BY fecha_venta DESC")
    Flux<Venta> findByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    @Query("SELECT SUM(total) FROM ventas WHERE fecha_venta BETWEEN :fechaInicio AND :fechaFin")
    Mono<BigDecimal> calcularTotalVentasEnPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    @Query("SELECT * FROM ventas WHERE cliente_nombre LIKE CONCAT('%', :cliente, '%')")
    Flux<Venta> findByClienteNombreContaining(String cliente);
}