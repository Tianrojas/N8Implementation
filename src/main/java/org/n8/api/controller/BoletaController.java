package org.n8.api.controller;

import org.n8.api.model.Boleta;
import org.n8.api.service.BoletaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/boletas")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    // Crear o actualizar una boleta
    @PostMapping
    public ResponseEntity<Boleta> createBoleta(@RequestBody Boleta boleta) {
        Boleta savedBoleta = boletaService.saveBoleta(boleta);
        return new ResponseEntity<>(savedBoleta, HttpStatus.CREATED);
    }

    // Obtener todas las boletas
    @GetMapping
    public ResponseEntity<List<Boleta>> getAllBoletas() {
        List<Boleta> boletas = boletaService.getAllBoletas();
        return new ResponseEntity<>(boletas, HttpStatus.OK);
    }

    // Obtener boleta por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Boleta> getBoletaById(@PathVariable String id) {
        Optional<Boleta> boleta = boletaService.getBoletaById(id);
        return boleta.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/pasados")
    public ResponseEntity<List<Boleta>> getBoletasPasadas() {
        List<Boleta> boletasPasadas = boletaService.getBoletasPasadas();
        return new ResponseEntity<>(boletasPasadas, HttpStatus.OK);
    }
    // Eliminar boleta por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoletaById(@PathVariable String id) {
        boletaService.deleteBoletaById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Obtener boletas por ID de usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Boleta>> getBoletasByUserId(@PathVariable Long userId) {
        List<Boleta> boletas = boletaService.getBoletasByUserId(userId);
        return new ResponseEntity<>(boletas, HttpStatus.OK);
    }
}
