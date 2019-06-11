package gui.continuous;

import data.DataGenerator;
import function.TrendFunction;

import java.util.Random;

public class ContinuousGenerator extends DataGenerator<Double> {

    private TrendFunction function;
    private double start = 0;
    private int count = 10;
    private double stride = 1;
    private double sigmaX = 1;
    private double sigmaY = 1;
    private int outlierCount;
    private double outlierScale;

    public ContinuousGenerator(int count) {
        this(count, null, 0, 0, 0, 0, 0, 0);
    }

    public ContinuousGenerator(int count, TrendFunction function, double start, double stride, double sigmaX, double sigmaY, int outlierCount, double outlierScale) {
        super(count);
        this.function = function;
        this.start = start;
        this.count = count;
        this.stride = stride;
        this.sigmaX = sigmaX;
        this.sigmaY = sigmaY;
        this.outlierCount = outlierCount;
        this.outlierScale = outlierScale;
    }

    public Double[][] genData() {
        final double OUTLIER_PROBABILITY;

        if (this.outlierCount != 0) {
            OUTLIER_PROBABILITY = 1d / (double) this.outlierCount;
        } else {
            OUTLIER_PROBABILITY = 0d;
        }

        Double[][] out = new Double[count][2]; // The output data

        Random r = new Random();
        advanceRandom(r);

        for (int i = 0; i < count; i++) {
            final double trendX, trendY;
            trendX = i * stride + start; // The desired x
            trendY = function.f(trendX); // The y on the trend line

            double x, y;

            if (sigmaX == 0)
                x = trendX;
            else
                x = R(r.nextDouble(), sigmaX, trendX);

            if (sigmaY == 0)
                y = trendY;
            else
                y = R(r.nextDouble(), sigmaY, trendY);

            if (r.nextDouble() <= OUTLIER_PROBABILITY) {
                x = R(r.nextDouble(), outlierScale * sigmaX, trendX);
                y = R(r.nextDouble(), outlierScale * sigmaY, trendY);
            }

            out[i][0] = x;
            out[i][1] = y;
        }

        data = out;

        return out;
    }

    private void advanceRandom(Random r) {
        for (int i = 0; i < 10; i++)
            r.nextDouble();
    }

    public int getOutlierCount() {
        return outlierCount;
    }

    public void setOutlierCount(int outlierCount) {
        this.outlierCount = outlierCount;
    }

    public double getOutlierScale() {
        return outlierScale;
    }

    public void setOutlierScale(double outlierScale) {
        this.outlierScale = outlierScale;
    }

    public Double[][] getData() {
        return data;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getStride() {
        return stride;
    }

    public void setStride(double stride) {
        this.stride = stride;
    }

    public double getSigmaX() {
        return sigmaX;
    }

    public void setSigmaX(double sigmaX) {
        this.sigmaX = sigmaX;
    }

    public double getSigmaY() {
        return sigmaY;
    }

    public void setSigmaY(double sigmaY) {
        this.sigmaY = sigmaY;
    }

    public TrendFunction getFunction() {
        return function;
    }

    public void setFunction(TrendFunction function) {
        this.function = function;
    }
}
