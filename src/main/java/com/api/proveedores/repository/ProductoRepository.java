package com.api.proveedores.repository;

import com.api.proveedores.model.Producto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductoRepository extends ReactiveCrudRepository<Producto, Long> {
    
    Flux<Producto> findByActivoTrue();
    
    Flux<Producto> findByProveedorIdAndActivoTrue(Long proveedorId);
    
    @Query("SELECT * FROM productos WHERE nombre LIKE CONCAT('%', :nombre, '%') AND activo = true")
    Flux<Producto> findByNombreContaining(String nombre);
    
    @Query("SELECT * FROM productos WHERE stock < :minimoStock AND activo = true")
    Flux<Producto> findByStockBajo(Integer minimoStock);
}