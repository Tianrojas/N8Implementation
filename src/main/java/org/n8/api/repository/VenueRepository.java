package org.n8.api.repository;

import org.n8.api.model.Venue;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VenueRepository extends MongoRepository<Venue, Long> {
}