package org.agicam.endpoint;

import org.agicam.endpoint.collections.ConfigColl;
import org.agicam.endpoint.collections.ImageColl;
import org.agicam.endpoint.controllers.ConfigController;

import static spark.Spark.get;
import static spark.Spark.port;

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
        get("/config", ConfigController.fetchConfig);
    }
}
