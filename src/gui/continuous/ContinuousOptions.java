package gui.continuous;

public class ContinuousOptions extends ContinuousSettings.SettingsData {
    public static ContinuousOptions valueOf(ContinuousSettings.SettingsData data) {
        ContinuousOptions options = new ContinuousOptions();
        options.function = data.function;
        options.start = data.start;
        options.stride = data.stride;
        options.count = data.count;
        options.sigmaX = data.sigmaX;
        options.sigmaY = data.sigmaY;
        options.outlierCount = data.outlierCount;
        options.outlierScale = data.outlierScale;

        return options;
    }
}
