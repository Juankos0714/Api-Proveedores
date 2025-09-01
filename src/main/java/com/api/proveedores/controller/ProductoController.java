package com.api.proveedores.controller;

import com.api.proveedores.dto.ProductoDto;
import com.api.proveedores.model.Producto;
import com.api.proveedores.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public Flux<Producto> obtenerTodosLosProductos() {
        return productoService.obtenerTodosLosProductos();
    }

    @GetMapping("/activos")
    public Flux<Producto> obtenerProductosActivos() {
        return productoService.obtenerProductosActivos();
    }

    @GetMapping("/{id}")
    public Mono<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado")));
    }

    @GetMapping("/proveedor/{proveedorId}")
    public Flux<Producto> obtenerProductosPorProveedor(@PathVariable Long proveedorId) {
        return productoService.obtenerProductosPorProveedor(proveedorId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Producto> crearProducto(@Valid @RequestBody ProductoDto productoDto) {
        return productoService.crearProducto(productoDto);
    }

    @PutMapping("/{id}")
    public Mono<Producto> actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductoDto productoDto) {
        return productoService.actualizarProducto(id, productoDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminarProducto(@PathVariable Long id) {
        return productoService.eliminarProducto(id);
    }

    @GetMapping("/buscar")
    public Flux<Producto> buscarProductosPorNombre(@RequestParam String nombre) {
        return productoService.buscarPorNombre(nombre);
    }

    @GetMapping("/stock-bajo")
    public Flux<Producto> obtenerProductosConStockBajo(@RequestParam(defaultValue = "10") Integer minimoStock) {
        return productoService.obtenerProductosConStockBajo(minimoStock);
    }

    @PatchMapping("/{id}/stock")
    public Mono<Producto> actualizarStock(@PathVariable Long id, @RequestParam Integer stock) {
        return productoService.actualizarStock(id, stock);
    }
}