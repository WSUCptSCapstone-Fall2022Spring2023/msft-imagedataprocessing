package org.agicam.endpoint;

import com.mongodb.client.MongoDatabase;

public class MongoDB {
    private MongoDatabase database;

    private static MongoDB instance;
    public static MongoDB getInstance() {
        return instance;
    }

    public MongoDB(MongoDatabase database) {
        this.database = database;
    }
}
