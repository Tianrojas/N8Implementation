package org.n8.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.n8.api.model.User;

import java.util.Optional;

/**
 * Repositorio para la entidad User que interactúa con MongoDB.
 * Esta interfaz extiende MongoRepository, proporcionando métodos CRUD
 * para la colección "users" y permite consultas personalizadas.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Encuentra un usuario por su dirección de correo electrónico.
     * Este método devuelve un Optional que contiene el usuario si existe
     * o un Optional vacío si no se encuentra ningún usuario con ese email.
     *
     * @param email Correo electrónico del usuario a buscar.
     * @return Optional que contiene el usuario encontrado, o vacío si no se encuentra.
     */
    Optional<User> findByEmail(String email);
}
