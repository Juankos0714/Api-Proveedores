package com.api.proveedores.repository;

import com.api.proveedores.model.Proveedor;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProveedorRepository extends ReactiveCrudRepository<Proveedor, Long> {
    
    Flux<Proveedor> findByActivoTrue();
    
    Mono<Proveedor> findByEmail(String email);
    
    @Query("SELECT * FROM proveedores WHERE nombre LIKE CONCAT('%', :nombre, '%') AND activo = true")
    Flux<Proveedor> findByNombreContaining(String nombre);
}