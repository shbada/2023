package org.mongodb.javarest.std.repos;

import org.mongodb.javarest.std.dto.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {

    public List<Review> findByNameAndType(String name, String type);

}
