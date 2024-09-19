package org.n8.api.service;

import org.n8.api.model.Venue;
import org.n8.api.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public Venue getVenueById(Long id) {
        return venueRepository.findById(id).orElse(null);
    }

    public Venue createVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    public Venue updateVenue(Long id, Venue venueDetails) {
        return venueRepository.findById(id).map(venue -> {
            venue.setNombre(venueDetails.getNombre());
            venue.setDireccion(venueDetails.getDireccion());
            venue.setCiudad(venueDetails.getCiudad());
            venue.setTelefono(venueDetails.getTelefono());
            venue.setTipo(venueDetails.getTipo());
            return venueRepository.save(venue);
        }).orElse(null);
    }

    public boolean deleteVenue(Long id) {
        return venueRepository.findById(id).map(venue -> {
            venueRepository.delete(venue);
            return true;
        }).orElse(false);
    }
}