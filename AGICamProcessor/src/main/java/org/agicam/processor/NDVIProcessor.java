package org.agicam.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.PolygonRoi;
import ij.gui.Roi;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import ij.process.ImageStatistics;
import org.agicam.processor.util.Couple;
import org.agicam.processor.util.Plot;
import org.bson.Document;

public class NDVIProcessor {

    public Document calculateNDVI(File image, List<Plot> plots) {
        ImagePlus img = IJ.openImage(image.getPath());
        int w = img.getWidth();
        int h = img.getHeight();

        ImageProcessor ip = img.getProcessor();

        // Create a new float processor for the NDVI
        FloatProcessor ndvi = new FloatProcessor(w, h);

        // Calculate the NDVI
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int[] rgb = ip.getPixel(x, y, null);
                float r = (float) rgb[0];
                float g = (float) rgb[1];
                float b = (float) rgb[2];
                ndvi.setf(x, y, (1.664f * b) / (0.953f * r) - 1);
            }
        }

        // Create a new image from the NDVI data
        ImagePlus ndviImp = new ImagePlus("NDVI", ndvi);
        IJ.run(ndviImp, "Apply LUT", "red=0 green=0 blue=0"); // Mask the image

        Document doc = new Document();
        List<Document> plotInfo = new ArrayList<>();
        // Calculate statistics for each plot
        for (int i = 0; i < plots.size(); i++) {
            Plot plot = plots.get(i);
            int[] xPoints = plot.getPoints().stream().mapToInt(Couple::getItemOne).toArray();
            int[] yPoints = plot.getPoints().stream().mapToInt(Couple::getItemTwo).toArray();
            int length = xPoints.length;

            ndviImp.setRoi(new PolygonRoi(xPoints, yPoints, length, Roi.POLYGON));
            ImageStatistics stats = ImageStatistics.getStatistics(ndviImp.getProcessor(), ImageStatistics.MEAN, null);
            double mean = stats.mean;

            stats = ImageStatistics.getStatistics(ndviImp.getProcessor(), ImageStatistics.MEDIAN, null);
            double median = stats.median;
            stats = ImageStatistics.getStatistics(ndviImp.getProcessor(), ImageStatistics.STD_DEV, null);
            double std = stats.stdDev;
            stats = ImageStatistics.getStatistics(ndviImp.getProcessor(), ImageStatistics.MIN_MAX, null);
            double max = stats.max;
            double min = stats.min;

            Document plotDoc = new Document();
            doc.put("Plot #", i);
            doc.put("mean", mean);
            doc.put("median", median);
            doc.put("max", max);
            doc.put("min", min);

            plotInfo.add(plotDoc);
        }
        doc.put("plots", plotInfo);

        return doc;
    }
}
