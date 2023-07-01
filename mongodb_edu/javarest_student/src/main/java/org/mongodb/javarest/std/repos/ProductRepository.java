package org.mongodb.javarest.std.repos;

import org.mongodb.javarest.std.dto.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository <Product, String> {

    public Product findByName(String name);
    public Product findOneByNameAndType(String name, String type);
    public List<Product> findByType(String type);

}
