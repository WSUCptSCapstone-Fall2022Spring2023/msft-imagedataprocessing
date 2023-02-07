package org.agicam.endpoint;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
    private MongoDatabase database;

    private static MongoDB instance;
    public static MongoDB getInstance() {
        return instance;
    }

    public static void setInstance(String mongoString, String databaseName)
    {
        if(instance == null)
        {
            // Connect to MongoDatabase
            ConnectionString connectionString = new ConnectionString(mongoString);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .serverApi(ServerApi.builder()
                            .version(ServerApiVersion.V1)
                            .build())
                    .build();

            // Get client
            MongoClient mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase(databaseName);

            instance = new MongoDB(database);
        }
    }

    private MongoDB(MongoDatabase database) {
        this.database = database;
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
