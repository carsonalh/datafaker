package gui.discrete;

import data.DataGenerator;

import java.util.Random;

/**
 * A class for generating discrete data.
 */
public class DiscreteGenerator extends DataGenerator<Double> {

    private int count;
    private int rangeCount;
    private double rangeStart;
    private double rangeStride;
    private double sigmaX;
    private double center;

    /**
     * Constructs a <code>DiscreteGenerator</code> instance.
     */
    public DiscreteGenerator() {
        this(100, 10, 0, 1, 1, 0);
    }

    /**
     * Constructs a <code>DiscreteGenerator</code> instance.
     *
     * @param count       The amount of data points to generate.
     * @param rangeCount  The amount of ranges for the points to be classified into.
     * @param rangeStart  The starting point of the ranges.
     * @param rangeStride The spacing between each range.
     * @param sigmaX      The value of one standard deviation.
     * @param center      The center value of the normal distribution.
     */
    public DiscreteGenerator(int count, int rangeCount, double rangeStart, double rangeStride, double sigmaX, double center) {
        super(count);
        this.count = count;
        this.rangeCount = rangeCount;
        this.rangeStart = rangeStart;
        this.rangeStride = rangeStride;
        this.sigmaX = sigmaX;
        this.center = center;
    }

    /**
     * Generates, and classifies the data into the discrete ranges.
     *
     * @return The array with all the generated, and classified data.
     */
    public Double[][] genData() {
        data = new Double[rangeCount][2];
        double rangeEnd = getRangeEnd();

        for (int i = 0; i < rangeCount; i++) {
            data[i][0] = (rangeStart + i * rangeStride);
            data[i][1] = 0d;
        }

        if (rangeStart == rangeEnd)
            return null;

        Random r = new Random();

        // Generate the values and classify them
        values:
        for (int i = 0; i < count; i++) {
            double n;

            // Create until in the range
            do
                n = R(r.nextDouble(), sigmaX, center);
            while (n < rangeStart || n > rangeEnd);

            // Classify the number
            for (int j = 0; j < rangeCount - 1; j++) {
                if (n <= data[j + 1][0]) {
                    data[j][1]++;
                    continue values;
                }
            }

            data[rangeCount - 1][1]++;
        }

        return data;
    }

    public double getRangeEnd() {
        return rangeStart + (rangeCount + 1) * rangeStride;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRangeCount() {
        return rangeCount;
    }

    public void setRangeCount(int rangeCount) {
        this.rangeCount = rangeCount;
    }

    public double getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(double rangeStart) {
        this.rangeStart = rangeStart;
    }

    public double getRangeStride() {
        return rangeStride;
    }

    public void setRangeStride(double rangeStride) {
        this.rangeStride = rangeStride;
    }

    public double getSigmaX() {
        return sigmaX;
    }

    public void setSigmaX(double sigmaX) {
        this.sigmaX = sigmaX;
    }

    public double getCenter() {
        return center;
    }

    public void setCenter(double center) {
        this.center = center;
    }
}
