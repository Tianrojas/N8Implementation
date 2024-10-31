package org.n8.api.service;

import org.n8.api.exception.UserNotFoundException;
import org.n8.api.model.Boleta;
import org.n8.api.model.Order;
import org.n8.api.model.User;
import org.n8.api.model.Venue;
import org.n8.api.repository.BoletaRepository;
import org.n8.api.repository.OrderRepository;
import org.n8.api.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BoletaRepository boletaRepository;

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public Venue getVenueById(String id) {
        return venueRepository.findById(id).orElse(null);
    }

    public Venue createVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    public Venue updateVenue(String id, Venue venueDetails) {
        return venueRepository.findById(id).map(venue -> {
            venue.setNombre(venueDetails.getNombre());
            venue.setDireccion(venueDetails.getDireccion());
            venue.setCiudad(venueDetails.getCiudad());
            venue.setTelefono(venueDetails.getTelefono());
            venue.setTipo(venueDetails.getTipo());
            venue.setBoleteriasIds(venueDetails.getBoleteriasIds());
            venue.setMenuIds(venueDetails.getMenuIds());
            return venueRepository.save(venue);
        }).orElse(null);
    }

    public boolean deleteVenue(String id) {
        return venueRepository.findById(id).map(venue -> {
            venueRepository.delete(venue);
            return true;
        }).orElse(false);
    }
    public Boleta getTicketByVenueIdAndTicketId(String venueId, String ticketId) {
        Venue venue = getVenueById(venueId);
        if (venue.getBoleteriasIds().contains(ticketId)){
            return boletaRepository.findById(ticketId)
                    .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));
        } else {
            throw new RuntimeException("Ticket not associated with Venue or not found");
        }
    }
    public Order getMenuByVenueIdAndMenuId(String venueId, String menuId) {
        Venue venue = getVenueById(venueId);
        if (venue.getMenuIds().contains(menuId)) {
            return orderRepository.findById(menuId)
                    .orElseThrow(() -> new RuntimeException("menu not found with id: " + menuId));
        } else {
            throw new RuntimeException("Venue not associated with user or not found");
        }
    }
    public List<Boleta> getTicketsByVenueId(String venueId) {
        Venue venue = getVenueById(venueId);
        List<String> ticketIds = venue.getBoleteriasIds();
        Iterable<Boleta> boletasIterable = boletaRepository.findAllById(ticketIds);
        return StreamSupport.stream(boletasIterable.spliterator(), false)
                .collect(Collectors.toList());
    }
    public List<Order> getOrderByVenueId(String venueId) {
        Venue venue = getVenueById(venueId);
        List<String> orderIds = venue.getMenuIds();
        Iterable<Order> ordersIterable = orderRepository.findAllById(orderIds);
        return StreamSupport.stream(ordersIterable.spliterator(), false)
                .collect(Collectors.toList());
    }
    public Boleta addTicketToVenue(String venueId, Boleta boleta) {
        Venue venue = getVenueById(venueId);
        Boleta savedBoleta = boletaRepository.save(boleta);
        venue.getBoleteriasIds().add(savedBoleta.getId());
        venueRepository.save(venue);
        return savedBoleta;
    }
    public Order addMenuToVenue(String venueId, Order order) {
        Venue venue = getVenueById(venueId);
        Order savedOrder = orderRepository.save(order);
        venue.getMenuIds().add(savedOrder.getOrderId());
        venueRepository.save(venue);
        return savedOrder;
    }
}