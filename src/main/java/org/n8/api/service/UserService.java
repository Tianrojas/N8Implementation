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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * UserService es una clase de servicio que gestiona las operaciones relacionadas con los usuarios, boletos y lugares.
 * Proporciona métodos para crear, actualizar, eliminar y autenticar usuarios, además de gestionar boletos y lugares asociados a cada usuario.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private VenueRepository venueRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Obtiene todos los usuarios almacenados en la base de datos.
     *
     * @return Una lista de usuarios.
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Guarda un nuevo usuario en la base de datos, encriptando su contraseña antes de almacenarlo.
     *
     * @param user El usuario a guardar.
     * @return El usuario guardado.
     */
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param userId El ID del usuario a eliminar.
     */
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Modifica un usuario existente en la base de datos.
     *
     * @param userId       El ID del usuario a modificar.
     * @param updatedUser  Los datos actualizados del usuario.
     * @return El usuario actualizado.
     */
    public User modifyUser(String userId, User updatedUser) {
        return userRepository.findById(userId).map(user -> {
            user.setNombre(updatedUser.getNombre());
            user.setEmail(updatedUser.getEmail());
            user.setTelefono(updatedUser.getTelefono());
            user.setMetodoPago(updatedUser.getMetodoPago());
            user.setRol(updatedUser.getRol());
            user.setTicketIds(updatedUser.getTicketIds());
            user.setVenueIds(updatedUser.getVenueIds());

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            return userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    /**
     * Autentica un usuario verificando su email y contraseña.
     *
     * @param email       El email del usuario.
     * @param rawPassword La contraseña sin cifrar.
     * @return true si la autenticación es exitosa, false en caso contrario.
     */
    public boolean authenticate(String email, String rawPassword) {
        User user = findByEmail(email);
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    /**
     * Encuentra un usuario por su email.
     *
     * @param email El email del usuario.
     * @return El usuario encontrado.
     */
    public User findByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param userId El ID del usuario.
     * @return El usuario encontrado.
     */
    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    /**
     * Obtiene un boleto asociado a un usuario.
     *
     * @param userId   El ID del usuario.
     * @param ticketId El ID del boleto.
     * @return El boleto encontrado.
     */
    public Boleta getTicketByUserIdAndTicketId(String userId, String ticketId) {
        User user = getUserById(userId);
        if (user.getTicketIds().contains(ticketId)) {
            return boletaRepository.findById(ticketId)
                    .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));
        } else {
            throw new RuntimeException("Ticket not associated with user or not found");
        }
    }

    /**
     * Obtiene un lugar asociado a un usuario.
     *
     * @param userId  El ID del usuario.
     * @param venueId El ID del lugar.
     * @return El lugar encontrado.
     */
    public Venue getVenueByUserIdAndVenueId(String userId, String venueId) {
        User user = getUserById(userId);
        if (user.getVenueIds().contains(venueId)) {
            return venueRepository.findById(venueId)
                    .orElseThrow(() -> new RuntimeException("Venue not found with id: " + venueId));
        } else {
            throw new RuntimeException("Venue not associated with user or not found");
        }
    }

    /**
     * Obtiene todos los boletos asociados a un usuario.
     *
     * @param userId El ID del usuario.
     * @return Una lista de boletos.
     */
    public List<Boleta> getTicketsByUserId(String userId) {
        User user = getUserById(userId);
        List<String> ticketIds = user.getTicketIds();
        Iterable<Boleta> boletasIterable = boletaRepository.findAllById(ticketIds);
        return StreamSupport.stream(boletasIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los lugares asociados a un usuario.
     *
     * @param userId El ID del usuario.
     * @return Una lista de lugares.
     */
    public List<Venue> getVenuesByUserId(String userId) {
        User user = getUserById(userId);
        List<String> venueIds = user.getVenueIds();
        Iterable<Venue> venuesIterable = venueRepository.findAllById(venueIds);
        return StreamSupport.stream(venuesIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Agrega un boleto a un usuario.
     *
     * @param userId El ID del usuario.
     * @param boleta El boleto a agregar.
     * @return El boleto agregado.
     */
    public Boleta addTicketToUser(String userId, Boleta boleta) {
        User user = getUserById(userId);
        Boleta savedBoleta = boletaRepository.save(boleta);
        user.getTicketIds().add(savedBoleta.getId());
        userRepository.save(user);
        return savedBoleta;
    }

    /**
     * Actualiza un boleto de un usuario.
     *
     * @param userId       El ID del usuario.
     * @param ticketId     El ID del boleto.
     * @param updatedBoleta La información actualizada del boleto.
     * @return El boleto actualizado.
     */
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

    /**
     * Elimina un boleto de un usuario.
     *
     * @param userId   El ID del usuario.
     * @param ticketId El ID del boleto.
     */
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

    /**
     * Agrega un lugar a un usuario.
     *
     * @param userId El ID del usuario.
     * @param venue  El lugar a agregar.
     * @return El lugar agregado.
     */
    public Venue addVenueToUser(String userId, Venue venue) {
        User user = getUserById(userId);
        Venue savedVenue = venueRepository.save(venue);
        user.getVenueIds().add(savedVenue.getId());
        userRepository.save(user);
        return savedVenue;
    }

    /**
     * Actualiza un lugar de un usuario.
     *
     * @param userId       El ID del usuario.
     * @param venueId      El ID del lugar.
     * @param updatedVenue La información actualizada del lugar.
     * @return El lugar actualizado.
     */
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

    /**
     * Elimina un lugar de un usuario.
     *
     * @param userId  El ID del usuario.
     * @param venueId El ID del lugar.
     */
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

    /**
     * Obtiene el rol de un usuario por su email.
     *
     * @param email El email del usuario.
     * @return El rol del usuario.
     */
    public String getRoleByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            return user.getRol();
        }
        throw new RuntimeException("Usuario no encontrado con el correo: " + email);
    }
}
