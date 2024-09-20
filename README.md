
# NightOwl: Plataforma de Intermediación entre Bares, Discotecas y Clientes

## Presentación General del Proyecto

NightOwl es una plataforma diseñada para actuar como intermediario entre bares, discotecas y clientes, facilitando la venta de boletas y bebidas, y ahorrando las filas que normalmente se generan. Nuestra motivación surge al identificar los problemas comunes que se presentan en una noche de fiesta, y la escasa calidad de las aplicaciones actuales que intentan resolverlos. A través de NightOwl, buscamos ofrecer promociones que beneficien a todos los involucrados.

## Fuentes de Información

Para validar nuestra solución, utilizaremos encuestas mediante Google Docs, donde solicitaremos a los encuestados que compartan sus experiencias y necesidades. Esto nos permitirá entender mejor las problemáticas que enfrentan y cómo NightOwl puede ser útil para ellos. Además, aplicaremos métodos de desarrollo de negocios, como el modelo CANVAS, para evaluar la viabilidad y rentabilidad del proyecto.

## Entregable 1: Prototipo Visual e Implementación Controladores + Persistencia

### Objetivo

Desarrollar un prototipo visual utilizando IA generativa y un modelo de datos, seguido de la implementación de controladores con conexión a la persistencia de datos.

### Instrucciones Paso a Paso

1. **Configuración del Repositorio:**
   - Crear un repositorio en GitHub para el proyecto.
   - Configurar acceso para el equipo de trabajo.
   - Iniciar un proyecto con configuración base y crear un `README.md`.

2. **Diseño del Modelo de Datos:**
   - Definir el modelo de datos para los usuarios, productos y pedidos.
     1. **Usuario**
         - **ID**: Identificador único del usuario (String).
         - **Nombre**: Nombre completo del usuario (String).
         - **Email**: Dirección de correo electrónico (String).
         - **Teléfono**: Número de teléfono (String).
         - **Método de Pago**: Tipo de pago preferido (String; puede ser tarjeta de crédito, débito, etc.).
         - **Rol**: Tipo de usuario (String; puede ser cliente, administrador, etc.).
      
      2. **Producto**
         - **ID**: Identificador único del producto (String).
         - **Nombre**: Nombre del producto (String; por ejemplo, nombre de la bebida).
         - **Descripción**: Descripción breve del producto (String).
         - **Precio**: Precio del producto (Double).
         - **Categoría**: Categoría del producto (String; puede ser bebida, comida, etc.).
         - **Disponibilidad**: Indica si el producto está disponible (Boolean).
      
      3. **Pedido**
         - **ID**: Identificador único del pedido (String).
         - **ID de Usuario**: Referencia al usuario que realiza el pedido (String).
         - **Lista de Productos**: Lista de productos incluidos en el pedido (List<String>; referencias a los IDs de los productos).
         - **Fecha y Hora**: Fecha y hora en que se realizó el pedido (Date).
         - **Estado**: Estado del pedido (String; puede ser pendiente, completado, cancelado, etc.).
         - **Total**: Monto total del pedido (Double).
         - Implementar clases con los tipos de datos y métodos necesarios.
        
      4. **Boleta**
         - **ID**: Identificador único de la boleta (String).
         - **ID de Pedido**: Referencia al pedido asociado (String).
         - **Fecha de Emisión**: Fecha y hora en que se emitió la boleta (Date).
         - **Monto Total**: Monto total de la boleta (Double).
         - **Método de Pago**: Método utilizado para el pago (String; puede ser efectivo, tarjeta, etc.).
         - **Estado**: Estado de la boleta (String; puede ser activa, cancelada, etc.).

3. **Prototipo Visual con IA Generativa:**
    - Investigar y seleccionar herramientas de IA generativa.
    - Crear prototipos visuales que guíen el diseño de la interfaz del proyecto.

4. **Implementación de Controladores y Servicios REST:**
    - Desarrollar controladores para gestionar peticiones HTTP (GET, POST, PUT, DELETE).
    - Implementar servicios REST siguiendo el nivel 2 del modelo de madurez de Richardson.
    - Realizar operaciones CRUD sobre los modelos de datos.

   ```java
   @RestController
   @RequestMapping("/users")
   public class UserController {
       @Autowired
       private UserService userService;

       @GetMapping
       public List<User> getAllUsers() {
           return userService.findAll();
       }

       @PostMapping
       public User createUser(@RequestBody User user) {
           return userService.save(user);
       }

       @PutMapping("/{id}")
       public User updateUser(@PathVariable String id, @RequestBody User updatedUser) {
           return userService.modifyUser(id, updatedUser);
       }

       @DeleteMapping("/{id}")
       public void deleteUser(@PathVariable String id) {
           userService.deleteUser(id);
       }
   }
   ```

5. **Integración con MongoDB:**
    - Crear y configurar un clúster en MongoDB Atlas.
    - Conectar la base de datos MongoDB con el proyecto.
    - Implementar operaciones de base de datos en los controladores.

   ```java
   public User modifyUser(String userId, User updatedUser) {
       // Lógica para actualizar la base de datos
   }
   ```

## Conclusiones

El desarrollo de NightOwl no solo resolverá los problemas actuales de las filas en los bares y discotecas, sino que también brindará una experiencia mejorada al usuario final. Con una sólida base de datos y un diseño intuitivo, NightOwl se posicionará como una solución valiosa en el mercado.
