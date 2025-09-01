

### Proveedores (`/api/proveedores`)
- `GET /` - Obtener todos los proveedores
- `GET /activos` - Obtener proveedores activos
- `GET /{id}` - Obtener proveedor por ID
- `POST /` - Crear nuevo proveedor
- `PUT /{id}` - Actualizar proveedor
- `DELETE /{id}` - Desactivar proveedor
- `GET /buscar?nombre={nombre}` - Buscar proveedores por nombre

### Productos (`/api/productos`)
- `GET /` - Obtener todos los productos
- `GET /activos` - Obtener productos activos
- `GET /{id}` - Obtener producto por ID
- `GET /proveedor/{proveedorId}` - Obtener productos por proveedor
- `POST /` - Crear nuevo producto
- `PUT /{id}` - Actualizar producto
- `DELETE /{id}` - Desactivar producto
- `GET /buscar?nombre={nombre}` - Buscar productos por nombre
- `GET /stock-bajo?minimoStock={cantidad}` - Productos con stock bajo
- `PATCH /{id}/stock?stock={cantidad}` - Actualizar stock

### Ventas (`/api/ventas`)
- `GET /` - Obtener todas las ventas
- `GET /{id}` - Obtener venta por ID
- `GET /producto/{productoId}` - Obtener ventas por producto
- `POST /` - Registrar nueva venta
- `GET /periodo?fechaInicio={fecha}&fechaFin={fecha}` - Ventas en período
- `GET /total-periodo?fechaInicio={fecha}&fechaFin={fecha}` - Total ventas en período
- `GET /cliente?nombre={nombre}` - Buscar ventas por cliente

## Configuración

1. Crear base de datos MySQL llamada `proveedores_db`
2. Configurar credenciales en `application.yml`
3. Ejecutar el script SQL de `schema.sql` para crear las tablas

## Ejecución

```bash
mvn spring-boot:run
```

La API estará disponible en `http://localhost:8080`

## Ejemplos de Uso

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
