package gui.continuous;

import gui.MoreOptionsTable;

import javax.swing.table.TableModel;

public class ContinuousOptionsTable extends MoreOptionsTable<ContinuousOptions> {

    private static final Object[][] CONTINUOUS_DATA = {
            {"Function", "x^2", String.class},
            {"Start", 0.0, Double.class},
            {"Count", 0, Integer.class},
            {"Stride", 0.0, Double.class},
            {"SigmaX", 0.0, Double.class},
            {"SigmaY", 0.0, Double.class},
            {"Outliers", false, Boolean.class},
            {"One Every", 0, Integer.class},
            {"Scale", 0.0, Double.class},
    };

    /**
     * Constructs a new <code>ContinuousOptions</code> instance and passes in the field
     * values that are already set.
     *
     * @param options The options to pass.
     */
    public ContinuousOptionsTable(ContinuousOptions options) {
        super(options);
    }

    /**
     * Constructs a new <code>MoreOptionsTable</code> instance.
     */
    public ContinuousOptionsTable() {
        super();

        data = getDisplayData(CONTINUOUS_DATA);

        super.updateModel();
    }

    /**
     * Returns the options that are currently in the fields.
     *
     * @return The options that are currently in the fields.
     */
    @Override
    public ContinuousOptions getOptions() {
        ContinuousOptions options = new ContinuousOptions();

        TableModel model = getModel();

        options.function = (String) model.getValueAt(0, 1);
        options.start = (Double) model.getValueAt(0, 1);
        options.count = (Integer) model.getValueAt(0, 1);
        options.stride = (Double) model.getValueAt(0, 1);
        options.sigmaX = (Double) model.getValueAt(0, 1);
        options.sigmaY = (Double) model.getValueAt(0, 1);
        options.outliersEnabled = (Boolean) model.getValueAt(0, 1);
        options.outlierCount = (Integer) model.getValueAt(0, 1);
        options.outlierScale = (Double) model.getValueAt(0, 1);

        return options;
    }

    /**
     * Sets the options in the fields to the given parameter <code>options</code>.
     *
     * @param options The options to set.
     */
    @Override
    public void setOptions(ContinuousOptions options) {
        data[0][1] = options.function;
        data[1][1] = options.start;
        data[2][1] = options.count;
        data[3][1] = options.stride;
        data[4][1] = options.sigmaX;
        data[5][1] = options.sigmaY;
        data[6][1] = options.outliersEnabled;
        data[7][1] = options.outlierCount;
        data[8][1] = options.outlierScale;

        super.updateModel();
    }

}
