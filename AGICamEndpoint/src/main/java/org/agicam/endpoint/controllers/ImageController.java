package org.agicam.endpoint.controllers;

import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static spark.Spark.halt;

public class ImageController {

    public static Route getImage = (Request request, Response response) -> {
        File file = new File("./AGICamEndpoint/image.png");
        BufferedImage image = ImageIO.read(file);
        try {
            HttpServletResponse raw = response.raw();
            response.header("Content-Disposition", "attachment; filename=image.png");
            ImageIO.write(image, "PNG", response.raw().getOutputStream());
            raw.getOutputStream().flush();
            raw.getOutputStream().close();
            return raw;

        } catch (IOException ex) {
            halt();

        }

        return response;
    };

}
