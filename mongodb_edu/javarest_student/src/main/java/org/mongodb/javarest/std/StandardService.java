package org.mongodb.javarest.std;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.mongodb.javarest.std.dto.Product;
import org.mongodb.javarest.std.dto.Review;
import org.mongodb.javarest.std.repos.ProductRepository;
import org.mongodb.javarest.std.repos.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StandardService {

    private static Logger logger = LoggerFactory.getLogger(StandardService.class);

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ReviewRepository reviewRepository;

    public Product findByNameAndTypeReview(String name,String type) {

        Product product = productRepository.findOneByNameAndType(name, type);
        List<Review> reviews = reviewRepository.findByNameAndType(name, type);

        logger.debug("Reviews : {}", reviews.toString());

        List<Review> okReviews = new ArrayList<>();
        product.setReview(new ArrayList<Review>(okReviews));

        Observable
            .fromIterable(reviews)
                .subscribe(
                    review -> {
                    if(!"marantz".equals(review.getUserid())) {
                        okReviews.add(review);
                    }
                }
            )
        .dispose()
        ;

        logger.debug("Product : {}", product.toString());

        return product;
    }


}
