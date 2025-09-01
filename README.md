### Crear Proveedor
```json
POST /api/proveedores
{
    "nombre": "Proveedor ABC",
    "email": "contacto@proveedorabc.com",
    "telefono": "+1234567890",
    "direccion": "Calle 123, Ciudad"
}
```

### Crear Producto
```json
POST /api/productos
{
    "nombre": "Producto XYZ",
    "descripcion": "Descripción del producto",
    "precio": 25.99,
    "stock": 100,
    "proveedorId": 1
}
```

### Registrar Venta
```json
POST /api/ventas
{
    "productoId": 1,
    "cantidad": 5,
    "precioUnitario": 25.99,
    "clienteNombre": "Juan Pérez"
}
```
