package hello.pgexample.services;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;

@Service
public class MongoExtensionService {
    private MongoClient mongo = new MongoClient("localhost", 27017);

    public MongoExtensionService() throws UnknownHostException {
    }

    public void saveExtensionOf(ExtensionModel model) {
        DBCollection collection = mongo.getDB("test").getCollection(model.getClass().getSimpleName() + "_extensions");

        BasicDBObject basicDBObject = new BasicDBObject(model.getExtensionFields());
        collection.insert(basicDBObject);

        ObjectId id = basicDBObject.getObjectId("_id");
        model.setExtensionId(id.toHexString());
    }

    public void fillExtension(ExtensionModel model) {
        DBCollection collection = mongo.getDB("test").getCollection(model.getClass().getSimpleName() + "_extensions");
        DBObject one = collection.findOne(new ObjectId(model.getExtensionId()));
        one.removeField("_id");
        System.out.print(one);
        model.setExtensionFields(one.toMap());
    }
}
