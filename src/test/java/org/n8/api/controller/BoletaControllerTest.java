package org.n8.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.n8.api.model.Boleta;
import org.n8.api.service.BoletaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BoletaControllerTest {

    @Mock
    private BoletaService boletaService;

    @InjectMocks
    private BoletaController boletaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBoleta() {
        Boleta boleta = new Boleta("Concierto", "user123", "150", "2023-10-28T19:00:00");
        when(boletaService.saveBoleta(boleta)).thenReturn(boleta);

        ResponseEntity<Boleta> response = boletaController.createBoleta(boleta);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(boleta, response.getBody());
        verify(boletaService, times(1)).saveBoleta(boleta);
    }

    @Test
    void testGetAllBoletas() {
        Boleta boleta1 = new Boleta("Concierto", "user123", "150", "2023-10-28T19:00:00");
        Boleta boleta2 = new Boleta("Teatro", "user456", "120", "2023-11-01T20:00:00");
        List<Boleta> boletas = Arrays.asList(boleta1, boleta2);

        when(boletaService.getAllBoletas()).thenReturn(boletas);

        ResponseEntity<List<Boleta>> response = boletaController.getAllBoletas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(boletas, response.getBody());
        verify(boletaService, times(1)).getAllBoletas();
    }

    @Test
    void testGetBoletaByIdFound() {
        String boletaId = "1";
        Boleta boleta = new Boleta("Concierto", "user123", "150", "2023-10-28T19:00:00");
        when(boletaService.getBoletaById(boletaId)).thenReturn(Optional.of(boleta));

        ResponseEntity<Boleta> response = boletaController.getBoletaById(boletaId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(boleta, response.getBody());
        verify(boletaService, times(1)).getBoletaById(boletaId);
    }

    @Test
    void testGetBoletaByIdNotFound() {
        String boletaId = "999";
        when(boletaService.getBoletaById(boletaId)).thenReturn(Optional.empty());

        ResponseEntity<Boleta> response = boletaController.getBoletaById(boletaId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(boletaService, times(1)).getBoletaById(boletaId);
    }

    @Test
    void testDeleteBoletaById() {
        String boletaId = "1";
        doNothing().when(boletaService).deleteBoletaById(boletaId);

        ResponseEntity<Void> response = boletaController.deleteBoletaById(boletaId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(boletaService, times(1)).deleteBoletaById(boletaId);
    }

    @Test
    void testGetBoletasByUserId() {
        Long userId = 123L;
        Boleta boleta1 = new Boleta("Concierto", "user123", "150", "2023-10-28T19:00:00");
        Boleta boleta2 = new Boleta("Teatro", "user123", "120", "2023-11-01T20:00:00");
        List<Boleta> boletas = Arrays.asList(boleta1, boleta2);

        when(boletaService.getBoletasByUserId(userId)).thenReturn(boletas);

        ResponseEntity<List<Boleta>> response = boletaController.getBoletasByUserId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(boletas, response.getBody());
        verify(boletaService, times(1)).getBoletasByUserId(userId);
    }

    @Test
    void testGetBoletasPasadas() {
        Boleta boleta1 = new Boleta("Concierto", "user123", "150", "2023-09-10T19:00:00");
        Boleta boleta2 = new Boleta("Teatro", "user456", "120", "2023-08-15T20:00:00");
        List<Boleta> boletasPasadas = Arrays.asList(boleta1, boleta2);

        when(boletaService.getBoletasPasadas()).thenReturn(boletasPasadas);

        ResponseEntity<List<Boleta>> response = boletaController.getBoletasPasadas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(boletasPasadas, response.getBody());
        verify(boletaService, times(1)).getBoletasPasadas();
    }
}
