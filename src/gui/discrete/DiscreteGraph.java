package gui.discrete;

import gui.GraphPanel;

import java.awt.*;

public class DiscreteGraph extends GraphPanel {

    private static final Dimension WINDOW_SIZE = new Dimension(400, 400);

    Double[][] data;

    public DiscreteGraph(Double[][] data) {
        super();
        setPreferredSize(WINDOW_SIZE);
        this.data = data;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.clearRect(0, 0, WINDOW_SIZE.width, WINDOW_SIZE.height);

        Double maxColumn = 0d;

        for (Double[] range : data) {
            if (range[1] > maxColumn) {
                maxColumn = range[1];
            }
        }

        if (data.length == 0 || maxColumn == 0)
            return;

        final int rangeColumnWidth = WINDOW_SIZE.width / data.length;
        final double yRatio = WINDOW_SIZE.height / (double) maxColumn;

        for (int i = 0; i < data.length; i++) {
            int x = rangeColumnWidth * i;
            int y = (int) (WINDOW_SIZE.height - data[i][1] * yRatio);

            int width = rangeColumnWidth;
            int height = (int) (data[i][1] * yRatio);

            g.setColor(new Color(0, 0, 0));
            g.fillRect(x, y, width, height);
        }
    }

    void setData(Double[][] data) {
        this.data = data;

        repaint();
    }

}
