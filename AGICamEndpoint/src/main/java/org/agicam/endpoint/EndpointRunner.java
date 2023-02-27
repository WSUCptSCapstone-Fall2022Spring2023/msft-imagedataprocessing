package org.agicam.endpoint;

import org.agicam.endpoint.collections.ConfigColl;
import org.agicam.endpoint.collections.ImageColl;
import org.agicam.endpoint.controllers.ConfigController;
import org.agicam.endpoint.controllers.ImageController;

import static spark.Spark.*;

public class EndpointRunner {
    public static void main(String[] args) {

        // Get arguments
        String connectionString = args[0];
        String database = args[1];

        // Prepare database connection
        MongoDB.setInstance(connectionString, database);

        // Confirm collections are working
        if(ImageColl.get() == null || ConfigColl.get() == null)
        {
            System.out.println("Could not connect to database");
            return;
        }

        // Configure spark
        port(4567);

        // Configure paths
        get("/image-get", ImageController.getImage);
        after("/image-get", ((request, response) -> {response.type("image/jpeg");}));
        get("/config", ConfigController.fetchConfig);
        get("/plot-get", ConfigController.fetchPlots);
        post("/plot-add", ConfigController.addPlot);
        post("/plot-del", ConfigController.removePlot);
        post("/time-add", ConfigController.addTime);
        post("/time-del", ConfigController.removeTime);
    }
}
