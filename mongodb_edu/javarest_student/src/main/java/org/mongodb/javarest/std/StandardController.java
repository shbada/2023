package org.mongodb.javarest.std;

import io.swagger.annotations.Api;
import org.bson.Document;
import org.mongodb.javarest.std.dto.Product;
import org.mongodb.javarest.std.dto.Review;
import org.mongodb.javarest.std.repos.ProductRepository;
import org.mongodb.javarest.std.repos.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Api(value = "StandardController")
public class StandardController {

    private static Logger logger = LoggerFactory.getLogger(StandardController.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private StandardService standardService;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 등록 API
     * @param bodyDoc
     * @return
     */
    @RequestMapping(value = "/api/v1/product/register",
            consumes = "application/json",
            produces = "application/json",
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> registerProduct(@RequestBody Document bodyDoc ) {

        ResponseEntity<String> responseEntity = null;

        // Empty Check
        if(bodyDoc.isEmpty() ||
                !bodyDoc.containsKey("name") ||
                    !bodyDoc.containsKey("type") ) {
            responseEntity = new ResponseEntity<>("RequestBody",HttpStatus.NO_CONTENT);
            return responseEntity;
        }

        try {

            if(logger.isDebugEnabled()) {
                logger.debug("========================================");
                logger.debug("RequestBody: {}", bodyDoc.toString());
                //logger.debug("review     : {}", bodyDoc.get("review").getClass().getName());
                //logger.debug("review     : {}", bodyDoc.get("review").toString());
                logger.debug("========================================");
            }

            /*
            HashMap<String,Object> releaseMap = (HashMap<String, Object>) bodyDoc.get("release");

            ArrayList<HashMap<String,String>> inputReviewList =(ArrayList<HashMap<String,String>>) bodyDoc.get("review");

            ArrayList<Review> reviewList = new ArrayList<>();

            for (HashMap<String,String> reviewMap: inputReviewList) {
                logger.debug("reviewMap : {}", reviewMap.toString());
                reviewList.add(
                        new Review(
                                reviewMap.get("user"),
                                reviewMap.get("comment"),
                                new Date()
                        )
                );
            }
            *
             */

            productRepository.insert(new Product(
                    bodyDoc.get("name").toString(),
                    bodyDoc.get("type").toString()
                    /*
                    (ArrayList) bodyDoc.get("tags"),
                    new Release(
                            Integer.parseInt(releaseMap.get("version").toString()),
                            releaseMap.get("date").toString()
                    ),
                    reviewList
                    */
            ));

            responseEntity = new ResponseEntity<>(bodyDoc.toString(),HttpStatus.OK);

        } catch (Exception e) {
            // Exception 에 따라 정의된 응답을 정의해 주시기 바랍니다.
            responseEntity = new ResponseEntity<>(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    /**
     * 태그 저장 API
     * @param name
     * @param type
     * @param tags
     * @return
     */
    @RequestMapping(value = "/api/v1/product/tagput", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<String> putTagProduct(
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam String tags) {

        ResponseEntity<String> responseEntity = null;

        try {

            // TODO : ProductRepository 를 사용하여 데이터 조회 후 tags 추가 하여 Save
            Update updatePush = new Update().push("tags").value(tags);
            mongoTemplate.upsert(
                    Query.query(Criteria.where("name").is(name).and("type").is(type)),
                    updatePush,
                    "product"
            );

        } catch (Exception e) {
            // Exception 에 따라 정의된 응답을 정의해 주시기 바랍니다.
            responseEntity = new ResponseEntity<>(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }

    /**
     * 리뷰 등록 API
     * @param name
     * @param type
     * @param userid
     * @param comment
     * @return
     */
    @RequestMapping(value = "/api/v1/product/reviewput", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<String> putReviewProduct(
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam String userid,
            @RequestParam String comment) {

        ResponseEntity<String> responseEntity = null;

        try {

            Review review = new Review(name, type, userid, comment, new Date());
            reviewRepository.save(review);

            /*
            // Push with Slice
            BasicDBObject sliceObject =
                    new BasicDBObject("$each", list).
                            append("$sort", new BasicDBObject("date",-1)).
                            append("$slice", -10);

            Update updatePush = new Update().push("review",sliceObject);

            mongoTemplate.upsert(
                    Query.query(Criteria.where("name").is(name).and("type").is(type)),
                    updatePush,
                    "product"
            );
            */

            responseEntity = new ResponseEntity<>("Update OK!!",HttpStatus.OK);

        } catch (Exception e) {
            // Exception 에 따라 정의된 응답을 정의해 주시기 바랍니다.
            responseEntity = new ResponseEntity<>(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }

    /**
     * name이 등록한 리뷰 조회 API
     * @param name
     * @param type
     * @return
     */
    @RequestMapping(value = "/api/v1/product/review/getbynametype", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Product> getReviewByNameAndType(
            @RequestParam String name,
            @RequestParam String type ) {

        ResponseEntity<Product> responseEntity = null;

        try {

            Product product = standardService.findByNameAndTypeReview(name,type);
            responseEntity = new ResponseEntity<Product>(product,HttpStatus.OK);

        } catch (Exception e) {
            logger.error("{}",e.toString());
        }

        return responseEntity;

    }

}
