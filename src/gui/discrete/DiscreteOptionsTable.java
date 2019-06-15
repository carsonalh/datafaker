package gui.discrete;

import gui.MoreOptionsTable;

import javax.swing.table.TableModel;

/**
 * A table for modifying the discrete options.
 */
public class DiscreteOptionsTable extends MoreOptionsTable<DiscreteOptions> {

    private static final Object[][] DISCRETE_DATA = {
            {"Count", 0, Integer.class},
            {"Range Count", 0, Integer.class},
            {"Range Start", 0.0, Double.class},
            {"Range Stride", 0.0, Double.class},
            {"Sigma X", 0.0, Double.class},
            {"Center", 0.0, Double.class}
    };

    /**
     * Constructs a <code>DiscreteOptionsTable</code> instance.
     *
     * @param options The options to edit.
     */
    public DiscreteOptionsTable(DiscreteOptions options) {
        super();

        data = getDisplayData(DISCRETE_DATA);
        setOptions(options);
        updateModel();
    }

    /**
     * Constructs a <code>DiscreteOptionsTable</code> instance.
     */
    public DiscreteOptionsTable() {
        super();

        data = getDisplayData(DISCRETE_DATA);
        updateModel();
    }

    /**
     * Returns the options that are currently in the fields.
     *
     * @return The options that are currently in the fields.
     */
    @Override
    public DiscreteOptions getOptions() {
        DiscreteOptions options = new DiscreteOptions();

        TableModel model = getModel();
        options.count = (Integer) model.getValueAt(0, 1);
        options.rangeCount = (Integer) model.getValueAt(1, 1);
        options.rangeStart = (Double) model.getValueAt(2, 1);
        options.rangeStride = (Double) model.getValueAt(3, 1);
        options.sigmaX = (Double) model.getValueAt(4, 1);
        options.center = (Double) model.getValueAt(5, 1);

        return options;
    }

    /**
     * Sets the options in the fields to the given parameter <code>options</code>.
     *
     * @param options The options to set.
     */
    @Override
    public void setOptions(DiscreteOptions options) {
        data[0][1] = options.count;
        data[1][1] = options.rangeCount;
        data[2][1] = options.rangeStart;
        data[3][1] = options.rangeStride;
        data[4][1] = options.sigmaX;
        data[5][1] = options.center;

        updateModel();
    }

}
