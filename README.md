# NightOwl: Plataforma de Intermediación entre Bares, Discotecas y Clientes

## Presentación General del Proyecto

NightOwl es una plataforma diseñada para actuar como intermediario entre bares, discotecas y clientes, facilitando la venta de boletas y bebidas, y ahorrando las filas que normalmente se generan. Nuestra motivación surge al identificar los problemas comunes que se presentan en una noche de fiesta, y la escasa calidad de las aplicaciones actuales que intentan resolverlos. A través de NightOwl, buscamos ofrecer promociones que beneficien a todos los involucrados.

## Fuentes de Información

Para validar nuestra solución, utilizaremos encuestas mediante Google Docs, donde solicitaremos a los encuestados que compartan sus experiencias y necesidades. Esto nos permitirá entender mejor las problemáticas que enfrentan y cómo NightOwl puede ser útil para ellos. Además, aplicaremos métodos de desarrollo de negocios, como el modelo CANVAS, para evaluar la viabilidad y rentabilidad del proyecto.

## Entregable 2: Seguridad y Despliegue Backend

### Objetivo

Integrar una capa de seguridad JWT en el proyecto backend y preparar el despliegue del mismo.

### Instrucciones Paso a Paso

1. **Implementación de JWT:**
   - Se incorporó autenticación JWT para proteger el acceso a los endpoints.
   - Se implementaron rutas de registro y login de usuarios que generan y validan tokens JWT.
   - Se gestionó la seguridad de rutas y control de acceso basado en roles de usuario (`Customer` y `Hoster`).

2. **Pruebas Unitarias:**
   - Se escribieron pruebas unitarias para los controladores y servicios clave del sistema.
   - Las pruebas cubren autenticación, lógica de negocio y manejo de errores en las rutas protegidas.
   - Se validó la funcionalidad CRUD y se comprobó la seguridad de los endpoints con las pruebas.

3. **Documentación y Despliegue:**
   - Se documentaron las APIs y el código de la aplicación para facilitar la comprensión y mantenimiento.
   - Los cambios fueron subidos al repositorio de GitHub.
   - Se incluyeron instrucciones para la configuración y ejecución en este archivo `README.md`.
   
### Seguridad y Autenticación

- **Implementación de JWT**: La aplicación ahora cuenta con una capa de seguridad basada en JSON Web Tokens (JWT) que permite la autenticación segura de usuarios. Cada usuario recibe un token al iniciar sesión, el cual se incluye en las peticiones HTTP para acceder a rutas protegidas.
- **Control de Acceso Basado en Roles**: Las rutas se protegen según el rol del usuario (por ejemplo, `Customer` o `Hoster`), asegurando que solo usuarios con los permisos necesarios puedan acceder a los recursos relevantes.
- **Rutas de Registro y Login**: Se crearon endpoints para que los usuarios puedan registrarse y autenticarse, generando un token JWT en el proceso.

### Pruebas Unitarias

- **Pruebas para Seguridad**: Se realizaron pruebas que validan la correcta generación y verificación de tokens JWT, así como el acceso a rutas protegidas basado en roles.
- **Lógica de Negocio y Manejo de Errores**: Se cubrieron las principales funcionalidades del sistema, asegurando que los endpoints respondan adecuadamente en diferentes situaciones, como peticiones válidas, errores y acceso no autorizado.
- **Cobertura de CRUD**: Las operaciones CRUD para usuarios, boletas y establecimientos fueron probadas para confirmar la correcta interacción con la base de datos y el funcionamiento de la lógica de negocio.

### Despliegue del Backend

1. **Configuración del Repositorio**:
   - Se creó un repositorio en GitHub para el proyecto, con acceso configurado para el equipo.
   - El repositorio incluye toda la configuración necesaria y documentación en este `README.md`.

### Endpoints Principales

A continuación, se muestra un resumen de los endpoints más relevantes para la interacción con la API de NightOwl.

- **Usuarios**:
  - `POST /users/register`: Registrar un nuevo usuario.
  - `POST /users/login`: Iniciar sesión y obtener un token JWT.
  - `GET /users`: Obtener la lista de todos los usuarios (protegido).
  - `PUT /users/{id}`: Actualizar información de usuario (protegido).
  - `DELETE /users/{id}`: Eliminar usuario (protegido).

- **Boletas y Establecimientos**:
  - `POST /users/{id}/tickets`: Añadir una boleta al usuario (protegido).
  - `GET /users/{id}/tickets`: Obtener boletas de un usuario específico (protegido).
  - `POST /users/{id}/venues`: Añadir un establecimiento al usuario (protegido).
  - `GET /users/{id}/venues`: Obtener establecimientos de un usuario específico (protegido).

### Ejemplo de Configuración JWT en `application.properties`

```properties
jwt.secret=your_jwt_secret_key
```

### Ejecución de la Aplicación
- Compilar y Ejecutar:
  - Ejecuta el comando mvn spring-boot:run para iniciar la aplicación localmente.
- Autenticación de Usuarios:
  - Accede a /users/login para obtener un token JWT y utiliza este token en el encabezado Authorization: Bearer <token> para acceder a rutas protegidas.
    
### Conclusiones
Con esta implementación, NightOwl asegura la autenticación y autorización de sus usuarios mediante JWT, fortaleciendo la seguridad y el control de acceso en el backend. La integración de pruebas unitarias garantiza la fiabilidad del sistema, mientras que el despliegue en la nube permite ofrecer la solución a una mayor audiencia de forma escalable y segura.


