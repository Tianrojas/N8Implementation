package org.n8.api.controller;

import org.n8.api.model.Boleta;
import org.n8.api.model.Order;
import org.n8.api.model.Venue;
import org.n8.api.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @GetMapping
    public List<Venue> getAllVenues() {
        return venueService.getAllVenues();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venue> getVenueById(@PathVariable String id) {
        Venue venue = venueService.getVenueById(id);
        if (venue != null) {
            return ResponseEntity.ok(venue);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Venue createVenue(@RequestBody Venue venue) {
        return venueService.createVenue(venue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venue> updateVenue(@PathVariable String id, @RequestBody Venue venueDetails) {
        Venue updatedVenue = venueService.updateVenue(id, venueDetails);
        if (updatedVenue != null) {
            return ResponseEntity.ok(updatedVenue);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable String id) {
        if (venueService.deleteVenue(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/tickets")
    public List<Boleta> getVenuesTickets(@PathVariable String id) {
        return venueService.getTicketsByVenueId(id);
    }

    // curl -X GET "http://localhost:8080/venues/{id}/tickets"
    @GetMapping("/{id}/orders")
    public List<Order> getVenuesMenu(@PathVariable String id) {
        return venueService.getOrderByVenueId(id);
    }
    // curl -X GET "http://localhost:8080/venues/{id}/orders"
    @GetMapping("/{id}/tickets/{ticketId}")
    public Boleta getVenuesTicketById(@PathVariable String id, @PathVariable String ticketId) {
        return venueService.getTicketByVenueIdAndTicketId(id, ticketId);
    }

    // curl -X GET "http://localhost:8080/users/{id}/venues/{venueId}"
    @GetMapping("/{id}/orders/{orderId}")
    public Order getVenueOrderById(@PathVariable String id, @PathVariable String menuId) {
        return venueService.getMenuByVenueIdAndMenuId(id, menuId);
    }
    @PostMapping("/{id}/tickets")
    public Boleta addTicketToVenue(@PathVariable String id, @RequestBody Boleta boleta) {
        return venueService.addTicketToVenue(id, boleta);
    }

    // curl -X POST "http://localhost:8080/users/{id}/venues" -H "Content-Type: application/json" -d "{\"nombre\":\"Estadio\",\"direccion\":\"Calle 123\",\"ciudad\":\"Ciudad\",\"telefono\":\"123456789\",\"tipo\":\"Deportivo\"}"
    @PostMapping("/{id}/venues")
    public Order addVenueToUser(@PathVariable String id, @RequestBody Order menu) {
        return venueService.addMenuToVenue(id, menu);
    }
}