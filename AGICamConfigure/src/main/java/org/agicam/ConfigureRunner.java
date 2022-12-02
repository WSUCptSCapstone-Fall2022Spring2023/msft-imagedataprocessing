package org.agicam;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.mongodb.client.model.Filters.eq;

public class ConfigureRunner {
    public static void main(String[] args) throws IOException {
        // First argument should be the location of file to write configuration too.
        // Second argument should be camera #
        // Third argument should be connection string
        if(args.length != 3)
        {
            System.out.println("Invalid arguments. Please supply (config location) (camera #) (mongo connection string)");
            return;
        }

        // Collect arguments
        String configLocation = args[0];
        Integer cameraNumber = Integer.parseInt(args[1]);
        String connection = args[2];

        // Connect to MongoDatabase
        ConnectionString connectionString = new ConnectionString(connection);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();

        // Get client
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("test");

        // Get / Create collection
        MongoCollection<Document> configCollection = database.getCollection("configs");

        // Find the config for this camera
        Document config = configCollection.find(eq("camID", cameraNumber)).first();
        if(config == null)
        {
            config = getDefaultConfig(cameraNumber);
            configCollection.insertOne(config); // Write to remote the configuration for this device.
        }

        // Update config locally
        updateConfig(configLocation, config);
    }

    private static Document getDefaultConfig(int cameraNumber)
    {
        Document doc = new Document();
        doc.put("camID", cameraNumber); // Id of the camera
        doc.put("interval", true); // Are we capturing on intervals?
        doc.put("start", 600); // What time do we start? In minutes (600 = 10 a.m)
        doc.put("distance", 120); // How long between each capture in minutes
        doc.put("amount", 2); // Amount of captures in day
        return doc;
    }

    private static void updateConfig(String configLocation, Document doc) throws IOException {
        File localConfig = new File(configLocation); // find the local file
        FileWriter writer = new FileWriter(localConfig);
    }
}