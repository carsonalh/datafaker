package gui.discrete;

import general.Listener;
import gui.SettingsForm;

import javax.swing.*;
import java.awt.*;

/**
 * A GUI component for modifying the discrete settings and the discrete graph.
 */
public class DiscreteSettings extends SettingsForm<DiscreteSettings.SettingsData> {

    private static final int SPINNER_WIDTH = 200;

    private final JLabel countLabel;
    private final JLabel rangeCountLabel;
    private final JLabel rangeStrideLabel;
    private final JLabel rangeStartLabel;
    private final JLabel sigmaXLabel;
    private final JLabel centerLabel;

    private final JSpinner countSpinner;
    private final JSpinner rangeCountSpinner;
    private final JSpinner rangeStartSpinner;
    private final JSpinner rangeStrideSpinner;
    private final JSpinner sigmaXSpinner;
    private final JSpinner centerSpinner;

    /**
     * Constructs a <code>DiscreteSettings</code> instance.
     */
    public DiscreteSettings() {
        super();

        // Creation
        countLabel = new JLabel("Count:");
        rangeCountLabel = new JLabel("Range Count:");
        rangeStartLabel = new JLabel("Range Start:");
        rangeStrideLabel = new JLabel("Range Stride:");
        sigmaXLabel = new JLabel("Sigma X:");
        centerLabel = new JLabel("Center:");

        countSpinner = new JSpinner();
        rangeCountSpinner = new JSpinner();
        rangeStartSpinner = new JSpinner();
        rangeStrideSpinner = new JSpinner();
        sigmaXSpinner = new JSpinner();
        centerSpinner = new JSpinner();

        // Setup
        countSpinner.setModel(new SpinnerNumberModel(0, 0, (int) 1e7, 1));
        rangeCountSpinner.setModel(new SpinnerNumberModel(0, 0, (int) 1e7, 1));
        rangeStartSpinner.setModel(new SpinnerNumberModel(0, 0, 1e7, .1));
        rangeStrideSpinner.setModel(new SpinnerNumberModel(1, 0, 1e7, .1));
        sigmaXSpinner.setModel(new SpinnerNumberModel(1, 0, 1e7, .1));
        centerSpinner.setModel(new DynamicSpinnerNumberModel<Double>(0.0, 0.0, 10.0, 0.1));

        // Set the spinners to the default values
        DiscreteGenerator g = new DiscreteGenerator();
        countSpinner.setValue(g.getCount());
        rangeCountSpinner.setValue(g.getRangeCount());
        rangeStartSpinner.setValue(g.getRangeStart());
        rangeStrideSpinner.setValue(g.getRangeStride());
        sigmaXSpinner.setValue(g.getSigmaX());
        centerSpinner.setValue(g.getCenter());

        countSpinner.addChangeListener(e -> this.update());
        rangeCountSpinner.addChangeListener(e -> this.update());
        rangeStartSpinner.addChangeListener(e -> this.update());
        rangeStrideSpinner.addChangeListener(e -> this.update());
        sigmaXSpinner.addChangeListener(e -> this.update());
        centerSpinner.addChangeListener(e -> this.update());

        // Layout
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(10, 10, 10, 10);

        // COLUMN 1
        c.anchor = GridBagConstraints.BASELINE_LEADING;

        // Count Label
        c.gridx = 0;
        c.gridy = 0;

        add(countLabel, c);

        // Range Count Label
        c.gridx = 0;
        c.gridy = 1;

        add(rangeCountLabel, c);

        // Range Start Label
        c.gridx = 0;
        c.gridy = 2;

        add(rangeStartLabel, c);

        // Range Stride Label
        c.gridx = 0;
        c.gridy = 3;

        add(rangeStrideLabel, c);

        // Sigma X Label
        c.gridx = 0;
        c.gridy = 4;

        add(sigmaXLabel, c);

        // Center Label
        c.gridx = 0;
        c.gridy = 5;

        add(centerLabel, c);

        // COLUMN 2
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.fill = GridBagConstraints.HORIZONTAL;

        // Count Spinner
        c.gridx = 1;
        c.gridy = 0;

        add(countSpinner, c);

        // Range Count Spinner
        c.gridx = 1;
        c.gridy = 1;

        add(rangeCountSpinner, c);

        // Range Start Spinner
        c.gridx = 1;
        c.gridy = 2;

        add(rangeStartSpinner, c);

        // Range Stride Spinner
        c.gridx = 1;
        c.gridy = 3;

        add(rangeStrideSpinner, c);

        // Sigma X Spinner
        c.gridx = 1;
        c.gridy = 4;

        add(sigmaXSpinner, c);

        // Center Spinner
        c.gridx = 1;
        c.gridy = 5;

        add(centerSpinner, c);
    }

    /**
     * Gets the data from <code>getData</code> and sends it to all the listeners.
     */
    private void update() {
        SettingsData data = getData();

        for (Listener<SettingsData> l : listeners)
            l.onSubmit(data);
    }

    /**
     * Sets the range that the center spinner can be between.
     *
     * @param start The start of the range.
     * @param end   The end of the range.
     */
    public void setCenterRange(double start, double end) {
        if (end < start)
            return;

        double center = (Double) centerSpinner.getValue();

        if (!(start <= center && center <= end))
            center = start;

        DynamicSpinnerNumberModel<Double> centerModel = (DynamicSpinnerNumberModel<Double>) centerSpinner.getModel();
        centerModel.setMinimumValue(start);
        centerModel.setMaximumValue(end);
        centerModel.setValue(center);
    }

    public void addListener(Listener<SettingsData> l) {
        listeners.add(l);
    }

    private SettingsData getData() {
        SettingsData data = new SettingsData();
        data.count = (Integer) countSpinner.getValue();
        data.rangeCount = (Integer) rangeCountSpinner.getValue();
        data.rangeStart = (Double) rangeStartSpinner.getValue();
        data.rangeStride = (Double) rangeStrideSpinner.getValue();
        data.center = (Double) centerSpinner.getValue();
        data.sigmaX = (Double) sigmaXSpinner.getValue();

        return data;
    }

    public static class SettingsData {
        public int rangeCount;
        public int count;
        public double rangeStride;
        public double rangeStart;
        public double sigmaX;
        public double center;
    }

}
