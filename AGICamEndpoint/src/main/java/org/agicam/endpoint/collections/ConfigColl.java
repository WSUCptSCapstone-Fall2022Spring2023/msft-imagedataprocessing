package org.agicam.endpoint.collections;

import com.mongodb.client.MongoCollection;
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
    private MongoCollection<Document> collection;
}
