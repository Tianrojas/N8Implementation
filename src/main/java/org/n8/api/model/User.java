package org.n8.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String nombre;
    private String email;
    private String telefono;
    private String metodoPago;
    private String rol;

    private List<String> ticketIds;
    private List<String> venueIds;

    public User() {
    }

    public User(String id, String nombre, String email, String telefono, String metodoPago, String rol,
                List<String> ticketIds, List<String> venueIds) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.metodoPago = metodoPago;
        this.rol = rol;
        this.ticketIds = ticketIds;
        this.venueIds = venueIds;
    }

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
}
