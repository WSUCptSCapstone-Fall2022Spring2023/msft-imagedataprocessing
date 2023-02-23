package org.agicam.processor.util;

import java.util.List;

/**
 * Created By: Jordan M
 * Description:
 */
public class Plot {
    private List<Couple<Integer, Integer>> points;

    /**
     * Creates a plot from two sets of x & y
     * @param points points for this plot
     */
    public Plot(List<Couple<Integer, Integer>> points) {
        this.points = points;
    }

    public List<Couple<Integer, Integer>> getPoints() {
        return points;
    }
}
