package org.n8.api.repository;

import org.n8.api.model.Boleta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoletaRepository extends MongoRepository<Boleta, String> {
    List<Boleta> findByUserId(Long userId);
}
