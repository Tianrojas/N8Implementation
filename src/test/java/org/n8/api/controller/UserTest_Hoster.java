package org.n8.api.controller;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.n8.api.config.TestMongoConfig;
import org.n8.api.model.Venue;
import org.n8.api.security.JwtUtil;
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
public class UserTest_Hoster {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    private String jwtToken = "mocked-jwt-token";

    @BeforeEach
    public void setup() {
        // Mock para generar el token
        when(jwtUtil.generateToken(anyString(), anyString())).thenReturn(jwtToken);

        // Mock para validar el token y extraer el email
        when(jwtUtil.validateToken(eq(jwtToken), anyString())).thenReturn(true);
        when(jwtUtil.extractEmail(eq(jwtToken))).thenReturn("customer@example.com");

        // Mock para los claims
        Claims claims = mock(Claims.class);
        when(claims.get("role", String.class)).thenReturn("Hoster");
        when(jwtUtil.extractClaims(jwtToken)).thenReturn(claims);
    }



    // Venue-related endpoints (Hoster role should have access)

    @Test
    public void testGetUserVenues() throws Exception {
        Venue venue = new Venue("1", "Stadium", "Street 123", "City", "123456789", "Sport",Arrays.asList(), Arrays.asList());
        List<Venue> venues = Arrays.asList(venue);

        when(userService.getVenuesByUserId("1")).thenReturn(venues);

        mockMvc.perform(get("/users/1/venues")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre", is("Stadium")));
    }

    @Test
    public void testGetUserVenueById() throws Exception {
        Venue venue = new Venue("1", "Stadium", "Street 123", "City", "123456789", "Sport",Arrays.asList(), Arrays.asList());

        when(userService.getVenueByUserIdAndVenueId("1", "1")).thenReturn(venue);

        mockMvc.perform(get("/users/1/venues/1")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Stadium")));
    }

    @Test
    public void testAddVenueToUser() throws Exception {
        Venue venue = new Venue("1", "Stadium", "Street 123", "City", "123456789", "Sport",Arrays.asList(), Arrays.asList());
        when(userService.addVenueToUser(eq("1"), any(Venue.class))).thenReturn(venue);

        mockMvc.perform(post("/users/1/venues")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Stadium\",\"direccion\":\"Street 123\",\"ciudad\":\"City\",\"telefono\":\"123456789\",\"tipo\":\"Sport\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Stadium")));
    }

    @Test
    public void testUpdateVenue() throws Exception {
        Venue updatedVenue = new Venue("1", "Updated Stadium", "New Street", "New City", "987654321", "Cultural",Arrays.asList(), Arrays.asList());
        when(userService.updateVenue(eq("1"), eq("1"), any(Venue.class))).thenReturn(updatedVenue);

        mockMvc.perform(put("/users/1/venues/1")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Updated Stadium\",\"direccion\":\"New Street\",\"ciudad\":\"New City\",\"telefono\":\"987654321\",\"tipo\":\"Cultural\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Updated Stadium")))
                .andExpect(jsonPath("$.tipo", is("Cultural")));
    }

    @Test
    public void testDeleteVenueFromUser() throws Exception {
        mockMvc.perform(delete("/users/1/venues/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteVenueFromUser("1", "1");
    }

    // Ticket-related endpoints (Hoster role should not have access)

    @Test
    public void testGetUserTickets_Unauthorized() throws Exception {
        mockMvc.perform(get("/users/1/tickets")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // Expecting 403 Forbidden
    }

    @Test
    public void testGetUserTicketById_Unauthorized() throws Exception {
        mockMvc.perform(get("/users/1/tickets/1")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // Expecting 403 Forbidden
    }

    @Test
    public void testAddTicketToUser_Unauthorized() throws Exception {
        mockMvc.perform(post("/users/1/tickets")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"eventName\":\"Concert\",\"userId\":\"1\",\"price\":\"100.0\",\"purchaseDate\":\"2024-10-23T10:00:00\"}"))
                .andExpect(status().isForbidden()); // Expecting 403 Forbidden
    }

    @Test
    public void testUpdateTicket_Unauthorized() throws Exception {
        mockMvc.perform(put("/users/1/tickets/1")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"eventName\":\"Updated Event\",\"price\":\"200.0\",\"purchaseDate\":\"2024-12-01T15:00:00\"}"))
                .andExpect(status().isForbidden()); // Expecting 403 Forbidden
    }

    @Test
    public void testDeleteTicketFromUser_Unauthorized() throws Exception {
        mockMvc.perform(delete("/users/1/tickets/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isForbidden()); // Expecting 403 Forbidden

        verify(userService, never()).deleteTicketFromUser(anyString(), anyString());
    }
}
