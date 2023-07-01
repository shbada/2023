import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertOneModel;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MigrationToolApplication {
    private final static String[] tableList = {"HELP"};

    private static Logger logger = LoggerFactory.getLogger(MigrationToolApplication.class);

    private final static SimpleDateFormat stdDate = new SimpleDateFormat("yyyyMMdd");

    private static MongoClient mongoClient;
    private static Connection conn;

    private static void oracleToMongoInsert(String tableName) {

        StringBuilder sqlString = new StringBuilder()
                .append("SELECT * FROM ").append(tableName);

        try {

            PreparedStatement psmt = conn.prepareStatement(sqlString.toString());
            // TODO : Set Fetch Count or Limit 1000 Loops
            ResultSet rs = psmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();
            int insertCount = 0;

            HashMap buffer = null;

            MongoDatabase mongoDatabase = mongoClient.getDatabase("ccs");
            MongoCollection mongoCollection = mongoDatabase.getCollection("profile");

            BulkWriteResult bulkWriteResult = null;

            InsertOneModel insertOneModel = null;
            Document insertDoc = null;
            List<InsertOneModel> insertDocuments = new ArrayList<>();

            while (rs.next()) {
                buffer = new HashMap<>();
                for (int idx = 1; idx < columnCount+1; idx++) {
                    buffer.put(
                            rsmd.getColumnName(idx).toLowerCase(), // KEY
                            rs.getObject(idx)  // VALUE
                    );
                }

                insertDoc = new Document(buffer);
                insertOneModel = new InsertOneModel(insertDoc);
                insertDocuments.add(insertOneModel);
                insertCount++;

                if(insertCount % 1000 == 0) {
                    bulkWriteResult = mongoCollection.bulkWrite(insertDocuments);
                    insertDocuments = new ArrayList<>();
                }

            } // End of While Loop Fetch

            // Commit Rest of Insert Doc
            mongoCollection.bulkWrite(insertDocuments);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void batchWorker() {
        boolean isResult = false;

        try {
            for (String tableName : tableList) {
                oracleToMongoInsert( tableName );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void batchInit(){

        // ---------------------------------------------------------------------
        // Oracle Connect
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url="jdbc:oracle:thin:@localhost:1521:xe";
        String user="system";
        String pwd="oracle";

        conn = getConnection(driver,url,user,pwd);

        // ---------------------------------------------------------------------
        // MongoDB Connect with MongoDB URI format
        ConnectionString connectionString = new ConnectionString(
                //"mongodb://admin:admin@localhost:27017,localhost:27018,localhost:27019/test?authSource=admin&replicaSet=replset"
                "mongodb://localhost:27017/test"
        );

        // POJO Object를 등록
        CodecRegistry pojoCodecRegistry = fromProviders(
                PojoCodecProvider.
                        builder().
                        // Add Custom POJO Packages
                        //register("").
                                build()

        );

        CodecRegistry codecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry
        );

        // MongoDB Connection String 과 POJO Object 등의 옵션을 MongoClientSettings 에 적용
        MongoClientSettings mongoClientSettings =
                MongoClientSettings.builder()
                        .applyConnectionString(connectionString)
                        .codecRegistry(codecRegistry)
                        .build();

        // 전역 객체에 MongoDB Client 접속
        mongoClient = MongoClients.create(mongoClientSettings);

    }

    public static Connection getConnection(String driver, String url, String user, String pwd) {
        Connection conn = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException se) {
            String msg = se.getMessage();
            logger.error(msg, se);
            se.printStackTrace();
        } catch (Exception e) {
            String msg = e.getMessage();
            logger.error(msg, e);
            e.printStackTrace();;
        }

        return conn;
    }


    public static void main(String[] args) throws SQLException {
        logger.info("--------------------------------------------------------------------------------");
        logger.info("Start MigrationToolApplication");
        logger.info("--------------------------------------------------------------------------------");

        batchInit();
        batchWorker();

        logger.info("--------------------------------------------------------------------------------");
        logger.info("MigrationToolApplication Finished");
        logger.info("--------------------------------------------------------------------------------");

        conn.close();
        mongoClient.close();
    }

}