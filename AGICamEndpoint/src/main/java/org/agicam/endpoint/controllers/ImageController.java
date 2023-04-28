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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static spark.Spark.halt;

public class ImageController {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
    public static Route getImage = (Request request, Response response) -> {
        String cameraNumberString = request.queryParams("camNum");
        String dateTime = request.queryParams("dateTime");
        String take = request.queryParams("take");

        String id = cameraNumberString + "-" + dateTime + "-" + take;

        // Get the file image now
        GridFSBucket bucket = GridFSBuckets.create(MongoDB.getInstance().getDatabase());

        Document doc = ImageColl.get().getCollection().find(eq("_id", id)).first();
        // Create output stream
        FileOutputStream fileOutputStream = new FileOutputStream("./" + id + ".png");
        bucket.downloadToStream(doc.getObjectId("file_id"), fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();

        // Get our temporary local file
        File file = new File("./" + id + ".png");

        // Add the image into the response.
        fileToResponse(file, response);

        return response;
    };

    public static Route getImageData = (Request request, Response response) -> {
        //Get query parameters
        String cameraNumberString = request.queryParams("camNum");
        String startDateTimeString = request.queryParams("startDate");
        String endDateTimeString = request.queryParams("endDate");

        // Parse date time strings
        LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeString, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeString, formatter);

        // Retrieve NDVI data from database
        Map<String, List<Double>> ndviDataMap = getNDVIData(cameraNumberString, startDateTime, endDateTime);


        // Set response type to JSON and return NDVI data
        response.type("application/json");
        return ndviDataMap;
    };

    // Method to retrieve NDVI data from the database
    private static Map<String, List<Double>> getNDVIData(String cameraNumberString, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Document> ndviDataDocs = ImageColl.get().getCollection()
                .find(and(
                        eq("cam#", Integer.parseInt(cameraNumberString))
                        , eq("stats", new Document("$exists", true))
                        , eq("date", new Document("$gte", startDateTime).append("$lte", endDateTime))
                ))
                .into(new ArrayList<>());

        // Create map to hold NDVI data
        Map<String, List<Double>> ndviDataMap = new HashMap<>();
        for (Document doc : ndviDataDocs) {

            String dateTimeString = doc.getDate("date").toString();
            Document ndviData = doc.get("stats", Document.class);
            ndviDataMap.put(dateTimeString, ndviData.get("Plots", ArrayList.class));
        }

        return ndviDataMap;
    }


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