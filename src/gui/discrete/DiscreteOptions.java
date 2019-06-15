package gui.discrete;

public class DiscreteOptions extends DiscreteSettings.SettingsData {

    public static DiscreteOptions valueOf(DiscreteSettings.SettingsData data) {
        DiscreteOptions options = new DiscreteOptions();

        options.count = data.count;
        options.rangeCount = data.rangeCount;
        options.rangeStride = data.rangeStride;
        options.rangeStart = data.rangeStart;
        options.sigmaX = data.sigmaX;
        options.center = data.center;

        return options;
    }

}
