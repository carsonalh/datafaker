package gui.continuous;

import gui.GraphPanel;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.awt.*;

public class ContinuousGraph extends GraphPanel {

    private static final Dimension POINT_SIZE = new Dimension(2, 2);
    private static final Dimension WINDOW_SIZE = new Dimension(400, 400);

    private Double[][] data;

    public ContinuousGraph(Double[][] data) {
        setPreferredSize(WINDOW_SIZE);
        this.data = data;
    }

    public ContinuousGraph() {
        this(null);
    }

    public void setData(Double[][] data) {
        this.data = data;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.clearRect(0, 0, WINDOW_SIZE.width, WINDOW_SIZE.height);

        if (data == null)
            return;

        Box dataBoundingBox = getBoundingBox();
        Rectangle windowRect = new Rectangle(0, 0, WINDOW_SIZE.width, WINDOW_SIZE.height);

        // Plot all the points
        for (Double[] point : data) {
            double oldX = point[0], oldY = point[1];
            Point newPoint = map(dataBoundingBox, windowRect, oldX, oldY);

            int x = newPoint.x, y = newPoint.y, w = POINT_SIZE.width, h = POINT_SIZE.height;

            g.setColor(new Color(0, 0, 0));
            g.fillRect(x - w / 2, y - h / 2, w, h);
        }

        // Draw x and y axes
        // X
        Vector2D xStartWorld = new Vector2D(0, dataBoundingBox.y);
        Vector2D xEndWorld = new Vector2D(0, dataBoundingBox.y + dataBoundingBox.width);
        Vector2D yStartWorld = new Vector2D(dataBoundingBox.x, 0);
        Vector2D yEndWorld = new Vector2D(dataBoundingBox.x + dataBoundingBox.width, 0);

        Point xStartScreen = map(dataBoundingBox, windowRect, xStartWorld.getX(), xStartWorld.getY());
        Point xEndScreen = map(dataBoundingBox, windowRect, xEndWorld.getX(), xEndWorld.getY());
        Point yStartScreen = map(dataBoundingBox, windowRect, yStartWorld.getX(), yStartWorld.getY());
        Point yEndScreen = map(dataBoundingBox, windowRect, yEndWorld.getX(), yEndWorld.getY());

        g.drawLine(xStartScreen.x, xStartScreen.y, xEndScreen.x, xEndScreen.y);
        g.drawLine(yStartScreen.x, yStartScreen.y, yEndScreen.x, yEndScreen.y);
    }

    private Box getBoundingBox() {
        double minX, minY, maxX, maxY;
        final double ASPECT_RATIO = (double) WINDOW_SIZE.width / (double) WINDOW_SIZE.height;

        minX = maxX = data[0][0];
        minY = maxY = data[0][1];

        double deltaX, deltaY;

        for (Double[] row : data) {
            if (row[0] < minX)
                minX = row[0];
            if (row[0] > maxX)
                maxX = row[0];

            if (row[1] < minY)
                minY = row[1];
            if (row[1] > maxY)
                maxY = row[1];
        }

        deltaX = maxX - minX;
        deltaY = maxY - minY;

        double relativeX = deltaY * ASPECT_RATIO;
        double relativeY = deltaX / ASPECT_RATIO;

        double width, height;

        final double xOffset, yOffset;

        if (deltaY > relativeY) { // Y is "bigger": according to the aspect ratio
            width = relativeX;
            height = deltaY;
            yOffset = 0;
            xOffset = relativeX / 2d - deltaX / 2d;
        } else { // X is "bigger": also according to the aspect ratio
            width = deltaX;
            height = relativeY;
            yOffset = relativeY / 2d - deltaY / 2d;
            xOffset = 0;
        }

        return new Box(minX - xOffset, minY - yOffset, width, height);
    }

    private Point map(Box boundingBox, Rectangle screen, double x, double y) {
        double xPercent = (x - boundingBox.x) / boundingBox.width, yPercent = (y - boundingBox.y) / boundingBox.height;
        int newX = (int) (screen.width * xPercent), newY = (int) (screen.height * (1 - yPercent));

        return new Point(newX, newY);
    }

    private static class Box {
        public double x, y, width, height;

        public Box(double x, double y, double width, double height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

}
