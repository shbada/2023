import com.mongodb.DuplicateKeyException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class SampleDao {

    @Autowired
    MongoClient mongoClient;

    public void insertSampleData(String name) {

        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        MongoCollection mongoCollection = mongoDatabase.getCollection("sample");

        try {
            mongoCollection.insertOne(new Document("name", name));
        } catch ( DuplicateKeyException dupkey) {
            System.out.println(dupkey.toString());
        } catch ( Exception e ) {
            System.out.println(e.toString());
        }

    }

    public String getSampleData(String name) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
        MongoCollection mongoCollection = mongoDatabase.getCollection("sample");

        String result = "";

        try {

            Document document = new Document();
            document = (Document) mongoCollection.find(eq("name",name)).first();

            if( document != null ) {
                System.out.println(String.format("Results Doc:[%s]", document.toString()));
                result = document.getString("name");
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return result;

    }

//    public UpdateResult updateSampleData(String name, String value) {
//
//        //.... 코드 생략
//
//        return updateResult;
//    }
//
//    public DeleteResult deleteSampleData(String name) {
//
//        //.... 코드 생략
//
//        return deleteResult;
//
//    }

}