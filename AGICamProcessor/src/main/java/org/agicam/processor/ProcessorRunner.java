package org.agicam.processor;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSDownloadOptions;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.agicam.processor.util.Couple;
import org.agicam.processor.util.Plot;
import org.bson.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

/**
 * Created By: Abhi & Jordan
 * Description:
 */
public class ProcessorRunner {
    public static void main(String[] args) {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://agicam:QCZgJ97ledg5cXbf@agicam-store.dsxer1a.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("images");
        MongoCollection<Document> configs = database.getCollection("configs");

        // Find all images without stats
        FindIterable<Document> unprocessed = collection.find(and(exists("stats", false),
                                                                 exists("cam#", true)));
        unprocessed.forEach(doc -> {
            try {
                processImage(doc, collection, configs, database);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void processImage(Document doc, MongoCollection<Document> coll, MongoCollection<Document> configs, MongoDatabase database) throws IOException {
        int camNumber = doc.getInteger("cam#");

        // Get plots from configuration
        Document config = configs.find(eq("_id", camNumber)).first();

        assert config != null;
        List<Document> plotsDocs = config.getList("plots", Document.class, new ArrayList<>());
        List<Plot> plots = new ArrayList<>();

        // Deserialize plots
        for (Document pDoc : plotsDocs)
        {
            List<Document> points = pDoc.getList("points", Document.class, new ArrayList<>());
            List<Couple<Integer, Integer>> realPoints = new ArrayList<>();
            for (Document pointDoc : points)
            {
                realPoints.add(new Couple<>(pointDoc.getInteger("x"), pointDoc.getInteger("y")));
            }

            // Add to the plots list for plot construction
            plots.add(new Plot(realPoints));
        }

        // Get the file image now
        String fileName = doc.getString("_id");
        GridFSBucket bucket = GridFSBuckets.create(database);

        // Create output stream
        FileOutputStream fileOutputStream = new FileOutputStream("./" + fileName);
        bucket.downloadToStream(doc.getObjectId("file_id"), fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();

        // Get our temporary local file
        File file = new File("./" + fileName);

        // Process the image and update the collection
        Document stats = new NDVIProcessor().calculateNDVI(file, plots);
        doc.put("stats", stats);
        coll.replaceOne(eq("_id", doc.getString("_id")), doc);

        // Delete temporary local file
        file.delete();
    }
}
