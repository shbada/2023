package org.mongodb.javarest.sample.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Component
public class SampleDao {

    private static Logger logger = LoggerFactory.getLogger(SampleDao.class);

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    MongoClient mongoClient;

    private final static UpdateOptions upsertOptions = new UpdateOptions().upsert(true);

    public Document getReviewStats(String name) {

        ArrayList<Document> aggregateStages = new ArrayList<>();

        /*
            db.reviews.aggregate([
                { "$match" :  { "productId" : name } },
                { "$group" : { "_id" : "$score" , "count" : {"$sum":1} } },
                { "$sort" : { "_id" : 1} },
                { "$group" :
                    {
                      _id" : name,
                      "histogram" : { "$push" : {"score":"$_id","count":"$count"} },
                      "count" : {"$sum" : "$count"},
                      "average" : { "$avg" : {"$multiply":["$_id","$count"]}}
                    }
                },
            ])
         */

        Document match = new Document("$match", new Document("productId", name));
        Document countbyscore = new Document("$group", new Document().append("_id","$score")
                                                                .append("count",new Document("$sum",1))
        );
        Document sortbyscore = new Document("$sort", new Document("_id",1));
        Document histocalc = new Document("$push", new Document()
                                    .append("score","$_id")
                                    .append("count","$count")
        );
        ArrayList<String> multiply = new ArrayList<String>();
        multiply.add("$_id");
        multiply.add("$count");
        Document average = new Document("$avg", new Document("$multiply",multiply));
        Document groupall = new Document("$group", new Document()
                .append("_id",name)
                .append("histogram",histocalc)
                .append("count", new Document("$sum","$count"))
                .append("average",average)
        );

        aggregateStages.add(match);
        aggregateStages.add(countbyscore);
        aggregateStages.add(sortbyscore);
        aggregateStages.add(groupall);

        if( logger.isDebugEnabled() ) {
            logger.debug("aggregateStages : {}",aggregateStages.toString());
        }

        MongoCursor<Document> cursor = mongoClient.
                getDatabase("test").
                getCollection("reviews").aggregate(aggregateStages).iterator();

        return cursor.next();
    }

    public UpdateResult upsertReviewCached(Document review) {

        // Update Query
        // { "_id" : review['productId']}
        Document query = new Document();

        // Update Push with Each
        /*
          {
            "reviewlist": {
            "$each": [ review ],
            "$slice": 10,
            "$sort" : { "time" : -1 }
            }
          }
         */

        Document reviewlist = new Document();

        // { "$push":  reviewlist  }
        Document update = new Document();

        return mongoClient.
                getDatabase("test").
                getCollection("review_cached").updateOne(query,update,upsertOptions);

    }

}
