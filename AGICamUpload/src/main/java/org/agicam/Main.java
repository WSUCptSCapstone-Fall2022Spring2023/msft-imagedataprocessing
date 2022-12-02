package org.agicam;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // First argument is going to be the location of uploaded images as an absolute path
        if(args.length < 4)
        {
            System.out.println("Invalid Arguments for AGICamUpload. Please provide [images folder] [connection string] [username] [password]");
            return;
        }

        String location = args[0];

        // Validate that the location is real
        if(location == null)
        {
            throw new UnsupportedOperationException("We cannot upload data that is not stored");
        }

        // Get the connection string passed in
        String argConnection = args[1];

        // Replace the username and password
        argConnection = argConnection.replace("<username>", args[2]);
        argConnection = argConnection.replace("<password>", args[3]);

        // Connect to MongoDatabase
        ConnectionString connectionString = new ConnectionString(argConnection);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("images");

        GridFSBucket bucket = GridFSBuckets.create(database);

        List<File> uploaded = new ArrayList<>();

        // Handle uploading all the files at this location if possible
        try (Stream<Path> paths = Files.walk(Paths.get(location))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach((file) -> {
                        // Prepare document for upload
                        Document document = new Document();

                        // Store the file's name as its unique id
                        String id = file.getFileName().toString().split("\\.")[0];

                        // Upload the file to grid fs
                        GridFSUploadOptions options = new GridFSUploadOptions()
                                .chunkSizeBytes(1048576) // 1MB chunk size
                                .metadata(new Document("type", "rgb noir sbs image"));

                        try {
                            ObjectId fileId = bucket.uploadFromStream(id, new FileInputStream(file.toFile()), options);
                            document.put("file_id", fileId);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        document.put("_id", id);

                        // Upload the document
                        InsertOneResult result = collection.insertOne(document);

                        // Store files gito delete
                        if(result.wasAcknowledged())
                        {
                            uploaded.add(file.toFile());
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Delete uploaded files
        for (File f : uploaded)
        {
            f.delete();
        }
    }
}