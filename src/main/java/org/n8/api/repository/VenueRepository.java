package org.n8.api.repository;

import org.n8.api.model.Venue;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface VenueRepository extends MongoRepository<Venue, String> {
    Optional<Venue> findById(String id);

}