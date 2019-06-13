package data;

import org.apache.commons.math3.special.Erf;

import java.lang.reflect.Array;

/**
 * An abstract class for generating normally distributed data in a 2D array.
 *
 * @param <T> The type of data to generate.
 */
public abstract class DataGenerator<T extends Number> {

    private static final double SQRT_2 = 1.41421356237;
    protected int count;
    protected T[][] data;

    /**
     * Constructs a <code>DataGenerator</code>.
     *
     * @param count The number of data points that will be generated.
     */
    protected DataGenerator(int count) {
        this.count = count;
        data = (T[][]) Array.newInstance(Number.class, this.count, 2);
    }

    /**
     * Generates a normal distribution around the point <code>a</code>.
     *
     * @param x     The random value that will be passed in.
     * @param sigma The value of one standard deviation.
     * @param a     The center of the data distribution.
     * @return The normally distributed value.
     */
    protected static double R(double x, double sigma, double a) {
        return sigma * SQRT_2 * Erf.erfInv(2 * x - 1) + a;
    }

    /**
     * The method that will be called to generate the data.
     *
     * @return The generated data.
     */
    public abstract T[][] genData();

}
