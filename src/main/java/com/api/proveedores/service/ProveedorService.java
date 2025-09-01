package com.api.proveedores.service;

import com.api.proveedores.dto.ProveedorDto;
import com.api.proveedores.model.Proveedor;
import com.api.proveedores.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public Flux<Proveedor> obtenerTodosLosProveedores() {
        return proveedorRepository.findAll();
    }

    public Flux<Proveedor> obtenerProveedoresActivos() {
        return proveedorRepository.findByActivoTrue();
    }

    public Mono<Proveedor> obtenerProveedorPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    public Mono<Proveedor> crearProveedor(ProveedorDto proveedorDto) {
        return proveedorRepository.findByEmail(proveedorDto.email())
                .hasElement()
                .flatMap(existe -> {
                    if (existe) {
                        return Mono.error(new RuntimeException("Ya existe un proveedor con este email"));
                    }
                    Proveedor nuevoProveedor = Proveedor.crear(
                            proveedorDto.nombre(),
                            proveedorDto.email(),
                            proveedorDto.telefono(),
                            proveedorDto.direccion()
                    );
                    return proveedorRepository.save(nuevoProveedor);
                });
    }

    public Mono<Proveedor> actualizarProveedor(Long id, ProveedorDto proveedorDto) {
        return proveedorRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Proveedor no encontrado")))
                .flatMap(proveedorExistente -> {
                    Proveedor proveedorActualizado = proveedorExistente.actualizar(
                            proveedorDto.nombre(),
                            proveedorDto.email(),
                            proveedorDto.telefono(),
                            proveedorDto.direccion(),
                            proveedorExistente.activo()
                    );
                    return proveedorRepository.save(proveedorActualizado);
                });
    }

    public Mono<Void> eliminarProveedor(Long id) {
        return proveedorRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Proveedor no encontrado")))
                .flatMap(proveedor -> {
                    Proveedor proveedorDesactivado = proveedor.actualizar(
                            proveedor.nombre(),
                            proveedor.email(),
                            proveedor.telefono(),
                            proveedor.direccion(),
                            false
                    );
                    return proveedorRepository.save(proveedorDesactivado);
                })
                .then();
    }

    public Flux<Proveedor> buscarPorNombre(String nombre) {
        return proveedorRepository.findByNombreContaining(nombre);
    }
}