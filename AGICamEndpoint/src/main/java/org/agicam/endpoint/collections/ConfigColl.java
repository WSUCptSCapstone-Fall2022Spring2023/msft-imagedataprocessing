package org.agicam.endpoint.collections;

import com.mongodb.client.MongoCollection;
import org.agicam.endpoint.MongoDB;
import org.bson.Document;

public class ConfigColl {
    /**
     * Singleton information
     */
    private static ConfigColl instance;

    public static ConfigColl get(){
        if (instance == null)instance = new ConfigColl();
        return instance;
    }

    /**
     * Resources
     */
    private final MongoCollection<Document> collection;

    public ConfigColl() {
        this.collection = MongoDB.getInstance().getDatabase().getCollection("configs");
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }
}
