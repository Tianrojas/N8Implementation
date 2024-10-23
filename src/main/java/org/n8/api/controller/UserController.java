package org.n8.api.controller;

import org.n8.api.model.Boleta;
import org.n8.api.model.User;
import org.n8.api.model.Venue;
import org.n8.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // curl -X GET "http://localhost:8080/users"
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    // curl -X POST "http://localhost:8080/users" -H "Content-Type: application/json" -d "{\"id\":\"rt465ty\",\"nombre\":\"d\",\"email\":\"s\",\"telefono\":\"fds\",\"metodoPago\":\"sdsa\",\"rol\":\"customer\"}"
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    // curl -X DELETE "http://localhost:8080/users/3725734"
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    // curl -X PUT "http://localhost:8080/users/{id}" -H "Content-Type: application/json" -d "{\"nombre\":\"nuevoNombre\",\"email\":\"nuevoEmail\",\"telefono\":\"nuevoTelefono\",\"metodoPago\":\"nuevoMetodo\",\"rol\":\"nuevoRol\"}"
    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        return userService.modifyUser(id, updatedUser);
    }

    // curl -X GET "http://localhost:8080/users/{id}/tickets"
    @GetMapping("/{id}/tickets")
    public List<Boleta> getUserTickets(@PathVariable String id) {
        return userService.getTicketsByUserId(id);
    }

    // curl -X GET "http://localhost:8080/users/{id}/venues"
    @GetMapping("/{id}/venues")
    public List<Venue> getUserVenues(@PathVariable String id) {
        return userService.getVenuesByUserId(id);
    }

    // curl -X GET "http://localhost:8080/users/{id}/tickets/{ticketId}"
    @GetMapping("/{id}/tickets/{ticketId}")
    public Boleta getUserTicketById(@PathVariable String id, @PathVariable String ticketId) {
        return userService.getTicketByUserIdAndTicketId(id, ticketId);
    }

    // curl -X GET "http://localhost:8080/users/{id}/venues/{venueId}"
    @GetMapping("/{id}/venues/{venueId}")
    public Venue getUserVenueById(@PathVariable String id, @PathVariable String venueId) {
        return userService.getVenueByUserIdAndVenueId(id, venueId);
    }

    // curl -X POST "http://localhost:8080/users/{id}/tickets" -H "Content-Type: application/json" -d "{\"eventName\":\"Concierto\",\"price\":\"100.0\",\"purchaseDate\":\"2024-10-23T10:00:00\"}"
    @PostMapping("/{id}/tickets")
    public Boleta addTicketToUser(@PathVariable String id, @RequestBody Boleta boleta) {
        return userService.addTicketToUser(id, boleta);
    }

    // curl -X POST "http://localhost:8080/users/{id}/venues" -H "Content-Type: application/json" -d "{\"nombre\":\"Estadio\",\"direccion\":\"Calle 123\",\"ciudad\":\"Ciudad\",\"telefono\":\"123456789\",\"tipo\":\"Deportivo\"}"
    @PostMapping("/{id}/venues")
    public Venue addVenueToUser(@PathVariable String id, @RequestBody Venue venue) {
        return userService.addVenueToUser(id, venue);
    }

    // curl -X PUT "http://localhost:8080/users/{id}/tickets/{ticketId}" -H "Content-Type: application/json" -d "{\"eventName\":\"Nuevo Evento\",\"price\":\"150.0\",\"purchaseDate\":\"2024-12-01T15:00:00\"}"
    @PutMapping("/{id}/tickets/{ticketId}")
    public Boleta updateTicket(@PathVariable String id, @PathVariable String ticketId, @RequestBody Boleta updatedBoleta) {
        return userService.updateTicket(id, ticketId, updatedBoleta);
    }

    // curl -X PUT "http://localhost:8080/users/{id}/venues/{venueId}" -H "Content-Type: application/json" -d "{\"nombre\":\"Nuevo Estadio\",\"direccion\":\"Nueva Direccion\",\"ciudad\":\"Nueva Ciudad\",\"telefono\":\"987654321\",\"tipo\":\"Cultural\"}"
    @PutMapping("/{id}/venues/{venueId}")
    public Venue updateVenue(@PathVariable String id, @PathVariable String venueId, @RequestBody Venue updatedVenue) {
        return userService.updateVenue(id, venueId, updatedVenue);
    }

    // curl -X DELETE "http://localhost:8080/users/{id}/tickets/{ticketId}"
    @DeleteMapping("/{id}/tickets/{ticketId}")
    public void deleteTicketFromUser(@PathVariable String id, @PathVariable String ticketId) {
        userService.deleteTicketFromUser(id, ticketId);
    }

    // curl -X DELETE "http://localhost:8080/users/{id}/venues/{venueId}"
    @DeleteMapping("/{id}/venues/{venueId}")
    public void deleteVenueFromUser(@PathVariable String id, @PathVariable String venueId) {
        userService.deleteVenueFromUser(id, venueId);
    }
}