package org.n8.api.controller;

import org.n8.api.model.Boleta;
import org.n8.api.model.User;
import org.n8.api.model.Venue;
import org.n8.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.n8.api.security.JwtUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Collections;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    /**
     * Obtiene todos los usuarios.
     *
     * @return Lista de todos los usuarios.
     * @curl curl -X GET "http://localhost:8080/users" -H "Content-Type: application/json" -H "Authorization: Bearer <token>"
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    /**
     * Crea un nuevo usuario.
     *
     * @param user Datos del usuario a crear.
     * @return Usuario creado.
     * @curl curl -X POST "http://localhost:8080/users" -H "Content-Type: application/json" -d "{\"id\":\"rt465ty\",\"nombre\":\"d\",\"email\":\"s\",\"telefono\":\"fds\",\"metodoPago\":\"sdsa\",\"rol\":\"customer\"}"
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @curl curl -X DELETE "http://localhost:8080/users/3725734"
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id ID del usuario a actualizar.
     * @param updatedUser Datos actualizados del usuario.
     * @return Usuario actualizado.
     * @curl curl -X PUT "http://localhost:8080/users/{id}" -H "Content-Type: application/json" -d "{\"nombre\":\"nuevoNombre\",\"email\":\"nuevoEmail\",\"telefono\":\"nuevoTelefono\",\"metodoPago\":\"nuevoMetodo\",\"rol\":\"nuevoRol\"}"
     */
    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        return userService.modifyUser(id, updatedUser);
    }

    /**
     * Obtiene los tickets de un usuario.
     *
     * @param id ID del usuario.
     * @return Lista de boletos del usuario.
     * @curl curl -X GET "http://localhost:8080/users/{id}/tickets"
     */
    @GetMapping("/{id}/tickets")
    public List<Boleta> getUserTickets(@PathVariable String id) {
        return userService.getTicketsByUserId(id);
    }

    /**
     * Obtiene los venues de un usuario.
     *
     * @param id ID del usuario.
     * @return Lista de venues del usuario.
     * @curl curl -X GET "http://localhost:8080/users/{id}/venues"
     */
    @GetMapping("/{id}/venues")
    public List<Venue> getUserVenues(@PathVariable String id) {
        return userService.getVenuesByUserId(id);
    }

    /**
     * Obtiene un ticket específico de un usuario.
     *
     * @param id ID del usuario.
     * @param ticketId ID del boleto.
     * @return Boleto del usuario.
     * @curl curl -X GET "http://localhost:8080/users/{id}/tickets/{ticketId}"
     */
    @GetMapping("/{id}/tickets/{ticketId}")
    public Boleta getUserTicketById(@PathVariable String id, @PathVariable String ticketId) {
        return userService.getTicketByUserIdAndTicketId(id, ticketId);
    }

    /**
     * Obtiene un venue específico de un usuario.
     *
     * @param id ID del usuario.
     * @param venueId ID del venue.
     * @return Venue del usuario.
     * @curl curl -X GET "http://localhost:8080/users/{id}/venues/{venueId}"
     */
    @GetMapping("/{id}/venues/{venueId}")
    public Venue getUserVenueById(@PathVariable String id, @PathVariable String venueId) {
        return userService.getVenueByUserIdAndVenueId(id, venueId);
    }

    /**
     * Agrega un ticket al usuario.
     *
     * @param id ID del usuario.
     * @param boleta Datos del boleto a agregar.
     * @return Boleto agregado.
     * @curl curl -X POST "http://localhost:8080/users/{id}/tickets" -H "Content-Type: application/json" -d "{\"eventName\":\"Concierto\",\"price\":\"100.0\",\"purchaseDate\":\"2024-10-23T10:00:00\"}"
     */
    @PostMapping("/{id}/tickets")
    public Boleta addTicketToUser(@PathVariable String id, @RequestBody Boleta boleta) {
        return userService.addTicketToUser(id, boleta);
    }

    /**
     * Agrega un venue al usuario.
     *
     * @param id ID del usuario.
     * @param venue Datos del venue a agregar.
     * @return Venue agregado.
     * @curl curl -X POST "http://localhost:8080/users/{id}/venues" -H "Content-Type: application/json" -d "{\"nombre\":\"Estadio\",\"direccion\":\"Calle 123\",\"ciudad\":\"Ciudad\",\"telefono\":\"123456789\",\"tipo\":\"Deportivo\"}"
     */
    @PostMapping("/{id}/venues")
    public Venue addVenueToUser(@PathVariable String id, @RequestBody Venue venue) {
        return userService.addVenueToUser(id, venue);
    }

    /**
     * Actualiza un ticket del usuario.
     *
     * @param id ID del usuario.
     * @param ticketId ID del boleto a actualizar.
     * @param updatedBoleta Datos actualizados del boleto.
     * @return Boleto actualizado.
     * @curl curl -X PUT "http://localhost:8080/users/{id}/tickets/{ticketId}" -H "Content-Type: application/json" -d "{\"eventName\":\"Nuevo Evento\",\"price\":\"150.0\",\"purchaseDate\":\"2024-12-01T15:00:00\"}"
     */
    @PutMapping("/{id}/tickets/{ticketId}")
    public Boleta updateTicket(@PathVariable String id, @PathVariable String ticketId, @RequestBody Boleta updatedBoleta) {
        return userService.updateTicket(id, ticketId, updatedBoleta);
    }

    /**
     * Actualiza un venue del usuario.
     *
     * @param id ID del usuario.
     * @param venueId ID del venue a actualizar.
     * @param updatedVenue Datos actualizados del venue.
     * @return Venue actualizado.
     * @curl curl -X PUT "http://localhost:8080/users/{id}/venues/{venueId}" -H "Content-Type: application/json" -d "{\"nombre\":\"Nuevo Estadio\",\"direccion\":\"Nueva Direccion\",\"ciudad\":\"Nueva Ciudad\",\"telefono\":\"987654321\",\"tipo\":\"Cultural\"}"
     */
    @PutMapping("/{id}/venues/{venueId}")
    public Venue updateVenue(@PathVariable String id, @PathVariable String venueId, @RequestBody Venue updatedVenue) {
        return userService.updateVenue(id, venueId, updatedVenue);
    }

    /**
     * Elimina un ticket del usuario.
     *
     * @param id ID del usuario.
     * @param ticketId ID del boleto a eliminar.
     * @curl curl -X DELETE "http://localhost:8080/users/{id}/tickets/{ticketId}"
     */
    @DeleteMapping("/{id}/tickets/{ticketId}")
    public void deleteTicketFromUser(@PathVariable String id, @PathVariable String ticketId) {
        userService.deleteTicketFromUser(id, ticketId);
    }

    /**
     * Elimina un venue del usuario.
     *
     * @param id ID del usuario.
     * @param venueId ID del venue a eliminar.
     * @curl curl -X DELETE "http://localhost:8080/users/{id}/venues/{venueId}"
     */
    @DeleteMapping("/{id}/venues/{venueId}")
    public void deleteVenueFromUser(@PathVariable String id, @PathVariable String venueId) {
        userService.deleteVenueFromUser(id, venueId);
    }

    /**
     * Autentica al usuario y genera un token JWT.
     *
     * @param email Email del usuario.
     * @param password Contraseña del usuario.
     * @return Token JWT si las credenciales son válidas.
     * @curl curl -X POST "http://localhost:8080/users/login" -H "Content-Type: application/json" -d "{\"email\":\"juan@example.com\",\"password\":\"securePassword\"}"
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        if (userService.authenticate(email, password)) {
            String role = userService.getRoleByEmail(email);
            String token = jwtUtil.generateToken(email, role);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param nombre Nombre del usuario.
     * @param email Email del usuario.
     * @param password Contraseña del usuario.
     * @param telefono Teléfono del usuario.
     * @param metodoPago Método de pago del usuario.
     * @param rol Rol del usuario (e.g., Customer).
     * @return Mensaje de éxito al registrar el usuario.
     * @curl curl -X POST "http://localhost:8080/users/register" -H "Content-Type: application/json" -d "{\"id\":\"123\",\"nombre\":\"Juan\",\"email\":\"juan@example.com\",\"telefono\":\"1234567890\",\"metodoPago\":\"credit_card\",\"rol\":\"Customer\",\"password\":\"securePassword\"}"
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String telefono,
            @RequestParam String metodoPago,
            @RequestParam String rol) {

        String id = UUID.randomUUID().toString();
        List<String> ticketIds = Collections.emptyList();
        List<String> venueIds = Collections.emptyList();
        User newUser = new User(id, nombre, email, telefono, metodoPago, rol, ticketIds, venueIds, password);
        userService.save(newUser);

        return ResponseEntity.ok("Usuario registrado exitosamente");
    }
}
