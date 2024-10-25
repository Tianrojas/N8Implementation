package org.n8.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.n8.api.config.TestMongoConfig;
import org.n8.api.model.Venue;
import org.n8.api.service.VenueService;
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

@WebMvcTest(VenueController.class)
@AutoConfigureMockMvc
@Import(TestMongoConfig.class)
public class VenueTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VenueService venueService;

    @BeforeEach
    public void setup() {
        // Mocks are initialized automatically with @MockBean
    }

    @Test
    public void testGetAllVenues() throws Exception {
        Venue venue1 = new Venue("1", "Stadium", "Street 123", "City", "123456789", "Sport");
        Venue venue2 = new Venue("2", "Arena", "Street 456", "City", "987654321", "Concert");
        List<Venue> venues = Arrays.asList(venue1, venue2);

        when(venueService.getAllVenues()).thenReturn(venues);

        mockMvc.perform(get("/venues")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].nombre", is("Stadium")))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].nombre", is("Arena")));
    }

    @Test
    public void testGetVenueById() throws Exception {
        Venue venue = new Venue("1", "Stadium", "Street 123", "City", "123456789", "Sport");

        when(venueService.getVenueById("1")).thenReturn(venue);

        mockMvc.perform(get("/venues/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.nombre", is("Stadium")));
    }

    @Test
    public void testCreateVenue() throws Exception {
        Venue venue = new Venue("3", "Theater", "Street 789", "City", "123123123", "Drama");
        when(venueService.createVenue(any(Venue.class))).thenReturn(venue);

        mockMvc.perform(post("/venues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"3\",\"nombre\":\"Theater\",\"direccion\":\"Street 789\",\"ciudad\":\"City\",\"telefono\":\"123123123\",\"tipo\":\"Drama\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("3")))
                .andExpect(jsonPath("$.nombre", is("Theater")));
    }

    @Test
    public void testUpdateVenue() throws Exception {
        Venue updatedVenue = new Venue("1", "UpdatedStadium", "Updated Street", "Updated City", "111111111", "Updated Sport");
        when(venueService.updateVenue(eq("1"), any(Venue.class))).thenReturn(updatedVenue);

        mockMvc.perform(put("/venues/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"UpdatedStadium\",\"direccion\":\"Updated Street\",\"ciudad\":\"Updated City\",\"telefono\":\"111111111\",\"tipo\":\"Updated Sport\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("UpdatedStadium")));
    }

    @Test
    public void testDeleteVenue() throws Exception {
        when(venueService.deleteVenue("1")).thenReturn(true);

        mockMvc.perform(delete("/venues/1"))
                .andExpect(status().isNoContent());

        verify(venueService, times(1)).deleteVenue("1");
    }
}