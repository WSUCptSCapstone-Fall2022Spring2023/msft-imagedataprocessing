package org.agicam.endpoint.controllers;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.agicam.endpoint.MongoDB;
import org.agicam.endpoint.collections.ImageColl;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static spark.Spark.*;

public class ImageController {
    // Set up date formatter
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    

    // Handler for NDVI data requests
    public static Route getNDVI = (Request request, Response response) -> {
        //Get query parameters
        String cameraNumberString = request.queryParams("camNum");
        String startDateTimeString = request.queryParams("startDate");
        String endDateTimeString = request.queryParams("endDate");

        // Parse date time strings
        LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeString, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeString, formatter);

        // Retrieve NDVI data from database
        Map<String, Double> ndviDataMap = getNDVIData(cameraNumberString, startDateTime, endDateTime);

        // Set response type to JSON and return NDVI data
        response.type("application/json");
        return ndviDataMap;
    };

    // Method to retrieve NDVI data from the database
    private static Map<String, Double> getNDVIData(String cameraNumberString, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Document> ndviDataDocs = ImageColl.get().getCollection()
                .find(and(
                        eq("cameraNumber", cameraNumberString),
                        eq("ndviData", new Document("$exists", true)),
                        eq("dateTime", new Document("$gte", startDateTime).append("$lte", endDateTime))
                ))
                .into(new ArrayList<>());

        // Create map to hold NDVI data
        Map<String, Double> ndviDataMap = new HashMap<>();
        for (Document doc : ndviDataDocs) {
            String dateTimeString = doc.getString("dateTime");
            double ndviData = doc.getDouble("ndviData");
            ndviDataMap.put(dateTimeString, ndviData);
        }

        return ndviDataMap;
    }

    // Handler for image requests
    public static Route getImage = (Request request, Response response) -> {
        String cameraNumberString = request.queryParams("camNum");
        String dateTimeString = request.queryParams("dateTime");
        String takeString = request.queryParams("take");

        String id = cameraNumberString + "-" + dateTimeString + "-" + takeString;

        // Get the file image now
        GridFSBucket bucket = GridFSBuckets.create(MongoDB.getInstance().getDatabase());

        // Retrieve file from database and write to local file system
        Document doc = ImageColl.get().getCollection().find(eq("_id", id)).first();
        if (doc == null) {
            halt(404, "Image not found");
        }

        // Create output stream
        FileOutputStream fileOutputStream = new FileOutputStream("./" + id + ".png");
        bucket.downloadToStream(doc.getObjectId("file_id"), fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();

        // Get our temporary local file
        File file = new File("./" + id + ".png");
        if (!file.exists()) {
            halt(500, "Unable to download image");
        }

        // Add the image into the response.
        fileToResponse(file, response);

        return response;
    };

    private static void fileToResponse(File file, Response response) throws IOException {
        BufferedImage image = ImageIO.read(file);
        try {
            HttpServletResponse raw = response.raw();
            response.header("Content-Disposition", "attachment; filename=image.png");
            ImageIO.write(image, "PNG", response.raw().getOutputStream());
            raw.getOutputStream().flush();
            raw.getOutputStream().close();
        } catch (IOException ex) {
            halt();
        }
    }
}
