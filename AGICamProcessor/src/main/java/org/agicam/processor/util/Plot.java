package org.agicam.processor.util;

/**
 * Created By: Jordan M
 * Description:
 */
public class Plot {
    private Couple<Integer, Integer> xPoints;
    private Couple<Integer, Integer> yPoints;

    public Plot(int x1, int x2, int y1, int y2) {
        xPoints = new Couple<>(x1,x2);
        yPoints = new Couple<>(y1, y2);
    }

    /**
     * Creates a plot from two sets of x & y
     * @param xPoints two x values of plot
     * @param yPoints two y values of plot
     */
    public Plot(Couple<Integer, Integer> xPoints, Couple<Integer, Integer> yPoints) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
    }

    public Couple<Integer, Integer> getxPoints() {
        return xPoints;
    }

    public Couple<Integer, Integer> getyPoints() {
        return yPoints;
    }
}
