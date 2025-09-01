package com.api.proveedores.service;

import com.api.proveedores.dto.ProductoDto;
import com.api.proveedores.model.Producto;
import com.api.proveedores.repository.ProductoRepository;
import com.api.proveedores.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    public Flux<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    public Flux<Producto> obtenerProductosActivos() {
        return productoRepository.findByActivoTrue();
    }

    public Mono<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Flux<Producto> obtenerProductosPorProveedor(Long proveedorId) {
        return productoRepository.findByProveedorIdAndActivoTrue(proveedorId);
    }

    public Mono<Producto> crearProducto(ProductoDto productoDto) {
        return proveedorRepository.findById(productoDto.proveedorId())
                .switchIfEmpty(Mono.error(new RuntimeException("Proveedor no encontrado")))
                .flatMap(proveedor -> {
                    if (!proveedor.activo()) {
                        return Mono.error(new RuntimeException("El proveedor no está activo"));
                    }
                    Producto nuevoProducto = Producto.crear(
                            productoDto.nombre(),
                            productoDto.descripcion(),
                            productoDto.precio(),
                            productoDto.stock(),
                            productoDto.proveedorId()
                    );
                    return productoRepository.save(nuevoProducto);
                });
    }

    public Mono<Producto> actualizarProducto(Long id, ProductoDto productoDto) {
        return productoRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado")))
                .flatMap(producto -> {
                    Producto productoActualizado = producto.actualizar(
                            productoDto.nombre(),
                            productoDto.descripcion(),
                            productoDto.precio(),
                            productoDto.stock(),
                            producto.activo()
                    );
                    return productoRepository.save(productoActualizado);
                });
    }

    public Mono<Void> eliminarProducto(Long id) {
        return productoRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado")))
                .flatMap(producto -> {
                    Producto productoDesactivado = producto.actualizar(
                            producto.nombre(),
                            producto.descripcion(),
                            producto.precio(),
                            producto.stock(),
                            false
                    );
                    return productoRepository.save(productoDesactivado);
                })
                .then();
    }

    public Flux<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContaining(nombre);
    }

    public Flux<Producto> obtenerProductosConStockBajo(Integer minimoStock) {
        return productoRepository.findByStockBajo(minimoStock);
    }

    public Mono<Producto> actualizarStock(Long id, Integer nuevoStock) {
        return productoRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado")))
                .flatMap(producto -> {
                    Producto productoActualizado = producto.actualizarStock(nuevoStock);
                    return productoRepository.save(productoActualizado);
                });
    }
}