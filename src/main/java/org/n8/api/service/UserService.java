package org.n8.api.service;

import org.n8.api.exception.UserNotFoundException;
import org.n8.api.model.Boleta;
import org.n8.api.model.User;
import org.n8.api.model.Venue;
import org.n8.api.repository.BoletaRepository;
import org.n8.api.repository.UserRepository;
import org.n8.api.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private VenueRepository venueRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public User modifyUser(String userId, User updatedUser) {
        return userRepository.findById(userId).map(user -> {
            user.setNombre(updatedUser.getNombre());
            user.setEmail(updatedUser.getEmail());
            user.setTelefono(updatedUser.getTelefono());
            user.setMetodoPago(updatedUser.getMetodoPago());
            user.setRol(updatedUser.getRol());
            user.setTicketIds(updatedUser.getTicketIds());
            user.setVenueIds(updatedUser.getVenueIds());
            return userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    public User findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    public Boleta getTicketByUserIdAndTicketId(String userId, String ticketId) {
        User user = getUserById(userId);
        if (user.getTicketIds().contains(ticketId)) {
            return boletaRepository.findById(ticketId)
                    .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));
        } else {
            throw new RuntimeException("Ticket not associated with user or not found");
        }
    }

    public Venue getVenueByUserIdAndVenueId(String userId, String venueId) {
        User user = getUserById(userId);
        if (user.getVenueIds().contains(venueId)) {
            return venueRepository.findById(venueId)
                    .orElseThrow(() -> new RuntimeException("Venue not found with id: " + venueId));
        } else {
            throw new RuntimeException("Venue not associated with user or not found");
        }
    }

    public List<Boleta> getTicketsByUserId(String userId) {
        User user = getUserById(userId);
        List<String> ticketIds = user.getTicketIds();
        Iterable<Boleta> boletasIterable = boletaRepository.findAllById(ticketIds);
        return StreamSupport.stream(boletasIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Venue> getVenuesByUserId(String userId) {
        User user = getUserById(userId);
        List<String> venueIds = user.getVenueIds();
        Iterable<Venue> venuesIterable = venueRepository.findAllById(venueIds);
        return StreamSupport.stream(venuesIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Boleta addTicketToUser(String userId, Boleta boleta) {
        User user = getUserById(userId);
        Boleta savedBoleta = boletaRepository.save(boleta);
        user.getTicketIds().add(savedBoleta.getId());
        userRepository.save(user);
        return savedBoleta;
    }

    public Boleta updateTicket(String userId, String ticketId, Boleta updatedBoleta) {
        User user = getUserById(userId);
        if (user.getTicketIds().contains(ticketId)) {
            return boletaRepository.findById(ticketId).map(boleta -> {
                boleta.setEventName(updatedBoleta.getEventName());
                boleta.setPrice(updatedBoleta.getPrice());
                boleta.setPurchaseDate(updatedBoleta.getPurchaseDate());
                return boletaRepository.save(boleta);
            }).orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));
        } else {
            throw new RuntimeException("Ticket not associated with user or not found");
        }
    }

    public void deleteTicketFromUser(String userId, String ticketId) {
        User user = getUserById(userId);
        if (user.getTicketIds().contains(ticketId)) {
            boletaRepository.deleteById(ticketId);
            user.getTicketIds().remove(ticketId);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Ticket not associated with user or not found");
        }
    }

    public Venue addVenueToUser(String userId, Venue venue) {
        User user = getUserById(userId);
        Venue savedVenue = venueRepository.save(venue);
        user.getVenueIds().add(savedVenue.getId());
        userRepository.save(user);
        return savedVenue;
    }

    public Venue updateVenue(String userId, String venueId, Venue updatedVenue) {
        User user = getUserById(userId);
        if (user.getVenueIds().contains(venueId)) {
            return venueRepository.findById(venueId).map(venue -> {
                venue.setNombre(updatedVenue.getNombre());
                venue.setDireccion(updatedVenue.getDireccion());
                venue.setCiudad(updatedVenue.getCiudad());
                venue.setTelefono(updatedVenue.getTelefono());
                venue.setTipo(updatedVenue.getTipo());
                return venueRepository.save(venue);
            }).orElseThrow(() -> new RuntimeException("Venue not found with id: " + venueId));
        } else {
            throw new RuntimeException("Venue not associated with user or not found");
        }
    }

    public void deleteVenueFromUser(String userId, String venueId) {
        User user = getUserById(userId);
        if (user.getVenueIds().contains(venueId)) {
            venueRepository.deleteById(venueId);
            user.getVenueIds().remove(venueId);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Venue not associated with user or not found");
        }
    }
}

