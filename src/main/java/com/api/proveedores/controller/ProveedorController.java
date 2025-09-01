package com.api.proveedores.controller;

import com.api.proveedores.dto.ProveedorDto;
import com.api.proveedores.model.Proveedor;
import com.api.proveedores.service.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "*")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public Flux<Proveedor> obtenerTodosLosProveedores() {
        return proveedorService.obtenerTodosLosProveedores();
    }

    @GetMapping("/activos")
    public Flux<Proveedor> obtenerProveedoresActivos() {
        return proveedorService.obtenerProveedoresActivos();
    }

    @GetMapping("/{id}")
    public Mono<Proveedor> obtenerProveedorPorId(@PathVariable Long id) {
        return proveedorService.obtenerProveedorPorId(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Proveedor no encontrado")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Proveedor> crearProveedor(@Valid @RequestBody ProveedorDto proveedorDto) {
        return proveedorService.crearProveedor(proveedorDto);
    }

    @PutMapping("/{id}")
    public Mono<Proveedor> actualizarProveedor(@PathVariable Long id, @Valid @RequestBody ProveedorDto proveedorDto) {
        return proveedorService.actualizarProveedor(id, proveedorDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminarProveedor(@PathVariable Long id) {
        return proveedorService.eliminarProveedor(id);
    }

    @GetMapping("/buscar")
    public Flux<Proveedor> buscarProveedoresPorNombre(@RequestParam String nombre) {
        return proveedorService.buscarPorNombre(nombre);
    }
}