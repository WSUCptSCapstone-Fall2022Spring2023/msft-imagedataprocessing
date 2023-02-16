package org.agicam.endpoint.controllers;

import org.agicam.endpoint.collections.ConfigColl;
import org.agicam.endpoint.util.Point;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;


public class ConfigController {

    public static Route fetchConfig = (Request request, Response response) ->
    {
        // Get the camera number string
        String cameraNumberString = request.queryParams("camNum");

        // Check if convertible
        try
        {
            int value = Integer.parseInt(cameraNumberString);

            return Objects.requireNonNull(ConfigColl.get().getCollection().find(eq("camID", value)).first()).toJson();

        }catch (NumberFormatException e)
        {
            // Have some pre-made json responses for error
        }

        return new Document().toJson();
    };

    public static Route fetchPlots = (Request request, Response response) -> {

        String cameraNumberString = request.queryParams("camNum");

        // Check if convertible
        try
        {
            int value = Integer.parseInt(cameraNumberString);

            Document doc = ConfigColl.get().getCollection().find(eq("camID", value)).first();

            // Create builder to format plots
            StringBuilder result = new StringBuilder();
            result.append("[");

            // Add plots into list
            List<Document> plots = doc.getList("plots", Document.class, new ArrayList<>());
            for (Document plot : plots)
            {
                result.append(plot.toJson()).append(",");
            }

            // Remove extra comma
            if(result.toString().contains(","))result.deleteCharAt(result.lastIndexOf(","));

            // Close list
            result.append("]");

            return result;
        }catch (NumberFormatException e)
        {
            // Have some pre-made json responses for error
        }

        return new Document().toJson();
    };

    private static boolean isValidPoint(String input)
    {
        String split[] = input.split(",");

        // Should be two integers seperated by a comma
        if(split.length != 2)return false;

        // Confirm parseable
        try
        {
            Integer.parseInt(split[0]);
            Integer.parseInt(split[1]);
        }catch (NumberFormatException e)
        {
            return false;
        }

        return true;
    }

    private static Point convert(String input)
    {
        String[] splitInput = input.split(",");

        int x = Integer.parseInt(splitInput[0]);
        int y = Integer.parseInt(splitInput[1]);

        return new Point(x,y);
    }

    private static void reOrderPoints(List<Point> points)
    {
        int smallestX = points.stream().mapToInt(Point::getX).min().getAsInt();
        int smallestY = points.stream().mapToInt(Point::getY).min().getAsInt();
        int largestX = points.stream().mapToInt(Point::getX).max().getAsInt();;
        int largestY = points.stream().mapToInt(Point::getY).max().getAsInt();;

        points.clear();

        // Store the smallest & largest
        points.add(new Point(smallestX, smallestY));
        points.add(new Point(largestX, largestY));

        // Store left and right
        points.add(new Point(smallestX, largestY));
        points.add(new Point(largestX, smallestY));
    }

    public static Route addPlot = (Request request, Response response) -> {
        String cameraNumberString = request.queryParams("camNum");
        String pointOne = request.queryParams("p1");
        String pointTwo = request.queryParams("p2");
        String pointThree = request.queryParams("p3");
        String pointFour = request.queryParams("p4");

        if(!isValidPoint(pointOne) || !isValidPoint(pointTwo) || !isValidPoint(pointThree) || !isValidPoint(pointFour)){
            return "Invalid points";
        }

        // Conver these points and store them
        List<Point> points = new ArrayList<>();
        points.add(convert(pointOne));
        points.add(convert(pointTwo));
        points.add(convert(pointThree));
        points.add(convert(pointFour));

        // Reorder the points to be optimal
        reOrderPoints(points);

        List<Document> docs = new ArrayList<>();
        points.forEach(point -> {
            Document doc = new Document();
            doc.put("x", point.getX());
            doc.put("y", point.getY());
            docs.add(doc);
        });

        Document plot = new Document();
        plot.put("points", docs);


        // Check if convertible
        try
        {
            // Find the config
            int value = Integer.parseInt(cameraNumberString);
            Document doc = ConfigColl.get().getCollection().find(eq("camID", value)).first();

            // Add plot into list of plots
            List<Document> plots = doc.getList("plots", Document.class, new ArrayList<>());
            plots.add(plot);

            // Update document
            doc.replace("plots", plots);

            // Replace remotely
            ConfigColl.get().getCollection().replaceOne(eq("camID", value), doc);

            return "{success:true}";
        }catch (NumberFormatException e)
        {
            // Have some pre-made json responses for error
        }

        return new Document().toJson();
    };

    public static Route removePlot = (Request request, Response response) -> {
        String cameraNumberString = request.queryParams("camNum");
        String plotIndex = request.queryParams("pIndex");

        // Check if convertible
        try
        {
            // Find the config
            int value = Integer.parseInt(cameraNumberString);
            int index = Integer.parseInt(plotIndex);


            Document doc = ConfigColl.get().getCollection().find(eq("camID", value)).first();

            // Add plot into list of plots
            List<Document> plots = doc.getList("plots", Document.class, new ArrayList<>());


            if(plots.size() == 0)
            {
                return new Document("error", "Camera " + value  + ": has no plots").toJson();
            }

            if(index < 0 || index >= plots.size())
            {
                return new Document("error", "Invalid index of plots"); // Should we return list of valid plots?
            }

            // Update the plots
            plots.remove(index);

            // Update document
            doc.replace("plots", plots);

            // Replace remotely
            ConfigColl.get().getCollection().replaceOne(eq("camID", value), doc);

            return "{removed:true}";
        }catch (NumberFormatException e)
        {
            // Have some pre-made json responses for error
            return new Document("error","Invalid Camera Number").toJson();
        }
    };
}
