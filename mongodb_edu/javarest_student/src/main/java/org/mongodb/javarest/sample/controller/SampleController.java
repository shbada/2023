package org.mongodb.javarest.sample.controller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.InsertOneResult;
import io.swagger.annotations.Api;
import org.bson.Document;
import org.mongodb.javarest.sample.dao.SampleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@Api(value = "SampleController")
public class SampleController {

    private static Logger logger = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    MongoClient mongoClient;

    @Autowired
    SampleDao sampleDao;

    @RequestMapping(value = "/api/v1/review",
            consumes = "application/json",
            produces = "application/json",
            method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> reviewPost(
            @RequestHeader HttpHeaders headers,
            @RequestParam HashMap<String,String> paramsMap,
            @RequestBody Document bodyDoc ) {

        ResponseEntity<String> responseEntity = null;

        try {

            if (logger.isDebugEnabled()) {
                logger.debug("========================================");
                logger.debug("HttpHeaders     : {}", headers.toString());
                logger.debug("RequestParam    : {}", paramsMap.toString());
                logger.debug("RequestBody     : {}", bodyDoc.toString());
                logger.debug("========================================");
            }

            Document insertOneDoc = new Document(bodyDoc);

            if (logger.isDebugEnabled()) {
                logger.debug("========================================");
                logger.debug("InsertOneDoc    : {}", insertOneDoc.toString());
                logger.debug("========================================");
            }

            // Convert String to Objects
            try {
                Float score = Float.parseFloat( insertOneDoc.get("score").toString() );
                insertOneDoc.put("score",score);
            } catch (Exception e) {
                logger.error("Errors on score convert Float : {}", e.toString());
            }

            // TODO : Controller - Service - DAO
            InsertOneResult insertOneResult = mongoClient
                    .getDatabase("test")
                    .getCollection("reviews")
                    .insertOne(insertOneDoc);

            // Business 정의에 따라 error enum type 를 정의해서 front end 와 규약에 맞게 처리 바랍니다.
            if(insertOneResult.wasAcknowledged())
                responseEntity = new ResponseEntity<>(insertOneResult.toString(), HttpStatus.OK);
            else
                responseEntity = new ResponseEntity<>(insertOneResult.toString(),HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            // Exception 에 따라 정의된 응답을 정의해 주시기 바랍니다.
            responseEntity = new ResponseEntity<>(e.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @RequestMapping(
            value = "/api/v1/review/{name}",
            produces = "application/json",
            method = RequestMethod.GET )
    @ResponseBody
    public ResponseEntity<Document> reviewGet(
            @RequestHeader HttpHeaders headers,
            @RequestParam HashMap<String,String> paramsMap,
            @PathVariable(value="name") final String name) {

        ResponseEntity<Document> responseEntity = null;

        try {

            if(logger.isDebugEnabled()) {
                logger.debug("========================================");
                logger.debug("HttpHeaders     : {}", headers.toString());
                logger.debug("Name            : {}", name);
                logger.debug("Parameters      : {}", paramsMap.toString());
            }

            Document query = new Document("productId",name);

            Integer skipCount = 0;

            if( paramsMap != null && paramsMap.size() > 0) {
                try {
                    skipCount = Integer.parseInt( paramsMap.get("from").toString() );
                } catch (Exception e) {
                    logger.error("Parameters : {}", paramsMap.toString());
                }
            }

            if(logger.isDebugEnabled()) {
                logger.debug("========================================");
                logger.debug("query Doc       : {}", query.toString());
                logger.debug("========================================");
            }

            // TODO : Controller - Service - DAO
            Document returnDoc = new Document();
            ArrayList<Document> reviews = new ArrayList<>();

            MongoCursor<Document> cursor = null;

            cursor = mongoClient
                    .getDatabase("test")
                    .getCollection("reviews")
                    .find(query)
                    .sort(new Document("_id", -1))
                    .skip(skipCount)
                    .limit(10)
                    .iterator();

            while (cursor.hasNext()) {
                reviews.add(cursor.next());
            }

            returnDoc.append("productId",name);
            returnDoc.append("reviews",reviews);
            returnDoc.append("nextreview",Integer.toString( skipCount+10 ));

            Document stats = null;

            try {
                stats = sampleDao.getReviewStats(name);
                if( stats != null && !stats.isEmpty() ) {
                    for (String key: stats.keySet()) {
                        returnDoc.append(key,stats.get(key));
                    }
                }
            } catch (Exception e) {
                logger.error("Error : {}", e.toString());
            }

            responseEntity = new ResponseEntity<>(returnDoc,HttpStatus.OK);

        } catch (Exception e) {
            // Exception 에 따라 정의된 응답을 정의해 주시기 바랍니다.
            responseEntity = new ResponseEntity<>(new Document("ErrorMsg",e.toString()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
}
