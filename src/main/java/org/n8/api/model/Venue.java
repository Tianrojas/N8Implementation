package org.n8.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "venues")
public class Venue {
    @Id
    private String id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String telefono;
    private String tipo;
    private List<String> boleteriaIds;
    private List<String> menuIds;

    // Constructor vacío para JPA
    public Venue() {
    }

    // Constructor con parámetros
    public Venue(String id, String nombre, String direccion, String ciudad, String telefono, String tipo, List<String> menuIds, List<String> boleteriaIds) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.tipo = tipo;
        this.menuIds = menuIds;
        this.boleteriaIds = boleteriaIds;
    }

    // Getters y Setters
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public List<String> getBoleteriasIds() {
        return boleteriaIds;
    }

    public void setBoleteriasIds(List<String> ticketIds) {
        this.boleteriaIds = ticketIds;
    }

    public List<String> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<String> venueIds) {
        this.menuIds = venueIds;
    }
}
