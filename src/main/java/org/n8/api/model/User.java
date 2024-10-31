package org.n8.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un usuario en el sistema, almacenado en la colección "users" en MongoDB.
 */
@Document(collection = "users")
public class User {

    @Id
    private String id; // Identificador único del usuario

    private String nombre; // Nombre del usuario
    private String email; // Correo electrónico del usuario
    private String telefono; // Teléfono de contacto del usuario
    private String metodoPago; // Método de pago preferido del usuario
    private String rol; // Rol del usuario (e.g., Customer, Hoster)
    private String password; // Contraseña del usuario

    private List<String> ticketIds; // Lista de IDs de boletos asociados al usuario
    private List<String> venueIds; // Lista de IDs de venues asociados al usuario

    /**
     * Constructor vacío necesario para ciertas operaciones de MongoDB.
     */
    public User() {
    }

    /**
     * Constructor con parámetros para crear una instancia completa de User.
     *
     * @param id Identificador único del usuario.
     * @param nombre Nombre del usuario.
     * @param email Correo electrónico del usuario.
     * @param telefono Teléfono del usuario.
     * @param metodoPago Método de pago preferido del usuario.
     * @param rol Rol del usuario.
     * @param ticketIds Lista de IDs de boletos asociados al usuario.
     * @param venueIds Lista de IDs de venues asociados al usuario.
     * @param password Contraseña del usuario.
     */
    public User(String id, String nombre, String email, String telefono, String metodoPago, String rol,
                List<String> ticketIds, List<String> venueIds, String password) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.metodoPago = metodoPago;
        this.rol = rol;
        this.ticketIds = ticketIds;
        this.venueIds = venueIds;
        this.password = password;
    }

    // Métodos Getters y Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<String> getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(List<String> ticketIds) {
        this.ticketIds = ticketIds;
    }

    public List<String> getVenueIds() {
        return venueIds;
    }

    public void setVenueIds(List<String> venueIds) {
        this.venueIds = venueIds;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Métodos adicionales para gestionar ticketIds y venueIds

    /**
     * Agrega un ticket a la lista de tickets del usuario si no está ya presente.
     *
     * @param ticketId ID del boleto a agregar.
     */
    public void addTicketId(String ticketId) {
        if (!ticketIds.contains(ticketId)) {
            ticketIds.add(ticketId);
        }
    }

    /**
     * Elimina un ticket de la lista de tickets del usuario si está presente.
     *
     * @param ticketId ID del boleto a eliminar.
     */
    public void removeTicketId(String ticketId) {
        ticketIds.remove(ticketId);
    }

    /**
     * Agrega un venue a la lista de venues del usuario si no está ya presente.
     *
     * @param venueId ID del venue a agregar.
     */
    public void addVenueId(String venueId) {
        if (!venueIds.contains(venueId)) {
            venueIds.add(venueId);
        }
    }

    /**
     * Elimina un venue de la lista de venues del usuario si está presente.
     *
     * @param venueId ID del venue a eliminar.
     */
    public void removeVenueId(String venueId) {
        venueIds.remove(venueId);
    }
}
