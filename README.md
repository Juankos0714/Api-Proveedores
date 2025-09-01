# API de Proveedores - Spring Boot Reactive

Una API REST desarrollada con Spring Boot 3, Java 17, programación reactiva y MySQL que gestiona proveedores, productos y ventas.

## Características

- **Programación Reactiva**: Uso de Spring WebFlux y R2DBC
- **Java Records**: Implementación de entidades con records inmutables
- **Base de Datos**: MySQL con R2DBC para acceso reactivo
- **Validaciones**: Validaciones de entrada con Bean Validation
- **Manejo de Errores**: Control centralizado de excepciones

## Entidades

### Proveedor
- `id`: Identificador único
- `nombre`: Nombre del proveedor
- `email`: Email único del proveedor
- `telefono`: Teléfono de contacto
- `direccion`: Dirección física
- `activo`: Estado del proveedor
- `fechaRegistro`: Fecha de registro

### Producto
- `id`: Identificador único
- `nombre`: Nombre del producto
- `descripcion`: Descripción del producto
- `precio`: Precio del producto
- `stock`: Cantidad en inventario
- `proveedorId`: Referencia al proveedor
- `activo`: Estado del producto
- `fechaRegistro`: Fecha de registro

### Venta
- `id`: Identificador único
- `productoId`: Referencia al producto vendido
- `cantidad`: Cantidad vendida
- `precioUnitario`: Precio al momento de la venta
- `total`: Total de la venta (calculado automáticamente)
- `fechaVenta`: Fecha de la venta
- `clienteNombre`: Nombre del cliente

## Endpoints

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