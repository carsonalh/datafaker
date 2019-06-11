package data;

import org.apache.commons.math3.special.Erf;

import java.lang.reflect.Array;

public abstract class DataGenerator<T extends Number> {

    private static final double SQRT_2 = 1.41421356237;
    protected int count;
    protected T[][] data;

    protected DataGenerator(int count) {
        this.count = count;
        data = (T[][]) Array.newInstance(Number.class, this.count, 2);
    }

    protected static double R(double x, double sigma, double a) {
        return sigma * SQRT_2 * Erf.erfInv(2 * x - 1) + a;
    }

    public abstract T[][] genData();

}
