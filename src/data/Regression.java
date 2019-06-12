package data;

import function.Function;

public class Regression {

    public static Function linearRegression(Double[][] data) {
        final double meanX, meanY;
        double xSum = 0, ySum = 0;
        double count = data.length;

        for (Double[] row : data) {
            xSum += row[0];
            ySum += row[1];
        }

        meanX = xSum / count;
        meanY = ySum / count;

        double numerator = 0, denominator = 0;

        for (Double[] row : data) {
            numerator += (row[0] - meanX) * (row[1] - meanY);
            denominator += (row[0] - meanX) * (row[0] - meanX);
        }

        final double b = numerator / denominator;
        final double c = meanY - b * meanX;

        return x -> b * x + c;
    }

}
