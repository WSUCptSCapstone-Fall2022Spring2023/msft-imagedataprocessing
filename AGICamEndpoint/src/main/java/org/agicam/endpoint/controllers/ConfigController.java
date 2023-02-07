package org.agicam.endpoint.controllers;

import org.agicam.endpoint.collections.ConfigColl;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;


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

}
