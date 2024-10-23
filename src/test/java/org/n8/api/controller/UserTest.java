package org.n8.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.n8.api.config.TestMongoConfig;
import org.n8.api.model.Boleta;
import org.n8.api.model.User;
import org.n8.api.model.Venue;
import org.n8.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@Import(TestMongoConfig.class)
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
        // Mocks se inicializan automáticamente con @MockBean
    }

    @Test
    public void testGetAllUsers() throws Exception {
        User user1 = new User("1", "John", "john@example.com", "123456", "card", "customer", Arrays.asList("ticket1"), Arrays.asList("venue1"));
        User user2 = new User("2", "Jane", "jane@example.com", "654321", "paypal", "customer", Arrays.asList("ticket2"), Arrays.asList("venue2"));
        List<User> users = Arrays.asList(user1, user2);

        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].nombre", is("John")))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].nombre", is("Jane")));
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User("3", "Sam", "sam@example.com", "789123", "card", "customer", Arrays.asList(), Arrays.asList());
        when(userService.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"3\",\"nombre\":\"Sam\",\"email\":\"sam@example.com\",\"telefono\":\"789123\",\"metodoPago\":\"card\",\"rol\":\"customer\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("3")))
                .andExpect(jsonPath("$.nombre", is("Sam")));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser("1");
    }

    @Test
    public void testUpdateUser() throws Exception {
        User updatedUser = new User("1", "UpdatedName", "updated@example.com", "987654", "card", "customer", Arrays.asList(), Arrays.asList());
        when(userService.modifyUser(eq("1"), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"UpdatedName\",\"email\":\"updated@example.com\",\"telefono\":\"987654\",\"metodoPago\":\"card\",\"rol\":\"customer\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("UpdatedName")));
    }

    @Test
    public void testGetUserTickets() throws Exception {
        Boleta boleta = new Boleta("Concert", "1", "100.0", "2024-10-23T10:00:00");
        List<Boleta> tickets = Arrays.asList(boleta);

        when(userService.getTicketsByUserId("1")).thenReturn(tickets);

        mockMvc.perform(get("/users/1/tickets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventName", is("Concert")));
    }


    @Test
    public void testGetUserVenues() throws Exception {
        Venue venue = new Venue("1", "Stadium", "Street 123", "City", "123456789", "Sport");
        List<Venue> venues = Arrays.asList(venue);

        when(userService.getVenuesByUserId("1")).thenReturn(venues);

        mockMvc.perform(get("/users/1/venues")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre", is("Stadium")));
    }

    @Test
    public void testGetUserTicketById() throws Exception {
        Boleta boleta = new Boleta("Concert", "1", "100.0", "2024-10-23T10:00:00");

        when(userService.getTicketByUserIdAndTicketId("1", "1")).thenReturn(boleta);

        mockMvc.perform(get("/users/1/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName", is("Concert")))
                .andExpect(jsonPath("$.price", is("100.0")))
                .andExpect(jsonPath("$.purchaseDate", is("2024-10-23T10:00:00")));
    }


    @Test
    public void testGetUserVenueById() throws Exception {
        Venue venue = new Venue("1", "Stadium", "Street 123", "City", "123456789", "Sport");

        when(userService.getVenueByUserIdAndVenueId("1", "1")).thenReturn(venue);

        mockMvc.perform(get("/users/1/venues/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Stadium")));
    }

    @Test
    public void testAddTicketToUser() throws Exception {
        Boleta boleta = new Boleta("Concert", "1", "100.0", "2024-10-23T10:00:00");
        when(userService.addTicketToUser(eq("1"), any(Boleta.class))).thenReturn(boleta);

        mockMvc.perform(post("/users/1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"eventName\":\"Concert\",\"userId\":\"1\",\"price\":\"100.0\",\"purchaseDate\":\"2024-10-23T10:00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName", is("Concert")))
                .andExpect(jsonPath("$.price", is("100.0")));
    }

    @Test
    public void testAddVenueToUser() throws Exception {
        Venue venue = new Venue("1", "Stadium", "Street 123", "City", "123456789", "Sport");
        when(userService.addVenueToUser(eq("1"), any(Venue.class))).thenReturn(venue);

        mockMvc.perform(post("/users/1/venues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Stadium\",\"direccion\":\"Street 123\",\"ciudad\":\"City\",\"telefono\":\"123456789\",\"tipo\":\"Sport\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Stadium")));
    }

    @Test
    public void testDeleteTicketFromUser() throws Exception {
        mockMvc.perform(delete("/users/1/tickets/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteTicketFromUser("1", "1");
    }

    @Test
    public void testDeleteVenueFromUser() throws Exception {
        mockMvc.perform(delete("/users/1/venues/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteVenueFromUser("1", "1");
    }
}
