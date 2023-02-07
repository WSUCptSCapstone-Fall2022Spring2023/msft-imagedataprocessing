package org.agicam.endpoint.collections;

import com.mongodb.client.MongoCollection;
import org.agicam.endpoint.MongoDB;
import org.bson.Document;

public class ImageColl {
    private static ImageColl instance;
    public static ImageColl get()
    {
        if(instance == null)
        {
            instance = new ImageColl();
        }

        return instance;
    }

    private final MongoCollection<Document> collection;

    private ImageColl()
    {
        this.collection = MongoDB.getInstance().getDatabase().getCollection("images");
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }
}
