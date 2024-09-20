package org.n8.api.service;

import org.n8.api.model.Boleta;
import org.n8.api.repository.BoletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoletaService {

    @Autowired
    private BoletaRepository boletaRepository;

    // Crear o actualizar boleta
    public Boleta saveBoleta(Boleta boleta) {
        return boletaRepository.save(boleta);
    }

    // Obtener todas las boletas
    public List<Boleta> getAllBoletas() {
        return boletaRepository.findAll();
    }

    // Obtener boleta por ID
    public Optional<Boleta> getBoletaById(String boletaId) {
        return boletaRepository.findById(boletaId);
    }

    // Eliminar boleta por ID
    public void deleteBoletaById(String boletaId) {
        boletaRepository.deleteById(boletaId);
    }

    // Obtener boletas por ID de usuario
    public List<Boleta> getBoletasByUserId(Long userId) {
        return boletaRepository.findByUserId(userId);
    }
}
