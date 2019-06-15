package gui.continuous;

public class ContinuousOptions extends ContinuousSettings.SettingsData {

    /**
     * Gets all the default options of the application.
     *
     * @return The default options.
     */
    public static ContinuousOptions getDefaultOptions() {
        ContinuousOptions options = new ContinuousOptions();

        options.function = "sin(x)";
        options.start = 0d;
        options.stride = 0.1d;
        options.count = 100;
        options.sigmaX = 0d;
        options.sigmaY = 0d;
        options.outliersEnabled = false;
        options.outlierCount = 0;
        options.outlierScale = 0d;

        return options;
    }

    public static ContinuousOptions valueOf(ContinuousSettings.SettingsData data) {

        ContinuousOptions options = new ContinuousOptions();
        options.function = data.function;
        options.start = data.start;
        options.stride = data.stride;
        options.count = data.count;
        options.sigmaX = data.sigmaX;
        options.sigmaY = data.sigmaY;
        options.outliersEnabled = data.outliersEnabled;
        options.outlierCount = data.outlierCount;
        options.outlierScale = data.outlierScale;

        return options;
    }

}
