package hello.pgexample.services;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;

@Service
public class MongoExtensionService {
    private MongoClient mongo = new MongoClient("localhost", 27017);

    public MongoExtensionService() throws UnknownHostException {
    }

    public void saveExtension(ExtendableModel model) {
        DBCollection collection = mongo.getDB("test").getCollection(model.getClass().getSimpleName() + "_extensions");

        BasicDBObject document = new BasicDBObject();
        document.put("a", "b");
        collection.insert(document);
    }
}
