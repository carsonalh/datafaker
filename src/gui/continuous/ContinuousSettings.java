package gui.continuous;

import general.Listener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A GUI class for changing the settings regarding continuous data generation.
 */
public class ContinuousSettings extends JPanel {

    private final ArrayList<Listener<SettingsData>> listeners = new ArrayList<>();
    private final JLabel functionLabel;
    private final JLabel startLabel;
    private final JLabel countLabel;
    private final JLabel strideLabel;
    private final JLabel sigmaXLabel;
    private final JLabel sigmaYLabel;
    private final JLabel outlierLabel;
    private final JLabel outlierCountLabel;
    private final JLabel outlierScaleLabel;

    private final JTextField functionTextField;
    private final JSpinner startSpinner;
    private final JSpinner countSpinner;
    private final JSpinner strideSpinner;
    private final JSpinner sigmaXSpinner;
    private final JSpinner sigmaYSpinner;
    private final JCheckBox outlierCheckbox;
    private final JSpinner outlierCountSpinner;
    private final JSpinner outlierScaleSpinner;
    private boolean outlierEnabled;

    /**
     * Constructs a <code>ContinuousSettings</code> object.
     */
    public ContinuousSettings() {
        // Creation
        functionLabel = new JLabel("Function:");
        startLabel = new JLabel("Start:");
        countLabel = new JLabel("Count:");
        strideLabel = new JLabel("Stride:");
        sigmaXLabel = new JLabel("Sigma X:");
        sigmaYLabel = new JLabel("Sigma Y:");
        outlierLabel = new JLabel("Outliers:");
        outlierCountLabel = new JLabel("One Every:");
        outlierScaleLabel = new JLabel("Scale:");

        functionTextField = new JTextField(16);
        startSpinner = new JSpinner();
        countSpinner = new JSpinner();
        strideSpinner = new JSpinner();
        sigmaXSpinner = new JSpinner();
        sigmaYSpinner = new JSpinner();
        outlierCheckbox = new JCheckBox();
        outlierCountSpinner = new JSpinner();
        outlierScaleSpinner = new JSpinner();

        // Config
        startSpinner.setModel(new SpinnerNumberModel(0, -1e7, 1e7, 0.1));
        countSpinner.setModel(new SpinnerNumberModel(0, 0, (int) 1e7, 1));
        strideSpinner.setModel(new SpinnerNumberModel(0, 0, 1e7, 0.01));
        sigmaXSpinner.setModel(new SpinnerNumberModel(0, 0, 1e7, 0.01));
        sigmaYSpinner.setModel(new SpinnerNumberModel(0, 0, 1e7, 0.01));
        outlierCountSpinner.setModel(new SpinnerNumberModel(0, 0, (int) 1e7, 1));
        outlierScaleSpinner.setModel(new SpinnerNumberModel(0, 0, 1e7, 0.01));

        functionTextField.addActionListener(e -> this.update());
        startSpinner.addChangeListener(e -> this.update());
        countSpinner.addChangeListener(e -> this.update());
        strideSpinner.addChangeListener(e -> this.update());
        sigmaXSpinner.addChangeListener(e -> this.update());
        sigmaYSpinner.addChangeListener(e -> this.update());
        outlierCheckbox.addChangeListener(e -> this.update());
        outlierCountSpinner.addChangeListener(e -> this.update());
        outlierScaleSpinner.addChangeListener(e -> this.update());

        outlierCheckbox.addActionListener(e -> this.updateOutliersGUI());
        updateOutliersGUI();

        functionTextField.setText("sin(x)");
        countSpinner.setValue(100);
        strideSpinner.setValue(.1);
        outlierCheckbox.setSelected(false);

        // Layout
        setLayout(new GridBagLayout());

        // GLOBAL CONSTRAINTS
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1;
        c.insets = new Insets(10, 10, 10, 10);

        // FIRST COLUMN
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.weightx = 1;

        // Function Label
        c.gridx = 0;
        c.gridy = 0;

        add(functionLabel, c);

        // Start Label
        c.gridx = 0;
        c.gridy = 1;

        add(startLabel, c);

        // Count Label
        c.gridx = 0;
        c.gridy = 2;

        add(countLabel, c);

        // Stride Label
        c.gridx = 0;
        c.gridy = 3;

        add(strideLabel, c);

        // Sigma X Label
        c.gridx = 0;
        c.gridy = 4;

        add(sigmaXLabel, c);

        // Sigma Y Label
        c.gridx = 0;
        c.gridy = 5;

        add(sigmaYLabel, c);

        // Outlier Label
        c.gridx = 0;
        c.gridy = 6;

        add(outlierLabel, c);

        // Outlier Count Label
        c.gridx = 0;
        c.gridy = 7;

        add(outlierCountLabel, c);

        // Outlier Scale Label
        c.gridx = 0;
        c.gridy = 8;

        add(outlierScaleLabel, c);

        // SECOND COLUMN
        c.anchor = GridBagConstraints.BASELINE_LEADING;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        // Function Field
        c.gridx = 1;
        c.gridy = 0;

        add(functionTextField, c);

        // Start Spinner
        c.gridx = 1;
        c.gridy = 1;

        add(startSpinner, c);

        // Count Spinner
        c.gridx = 1;
        c.gridy = 2;

        add(countSpinner, c);

        // Stride Spinner
        c.gridx = 1;
        c.gridy = 3;

        add(strideSpinner, c);

        // Sigma X Spinner
        c.gridx = 1;
        c.gridy = 4;

        add(sigmaXSpinner, c);

        // Sigma Y Spinner
        c.gridx = 1;
        c.gridy = 5;

        add(sigmaYSpinner, c);

        // Outlier Checkbox
        c.gridx = 1;
        c.gridy = 6;

        add(outlierCheckbox, c);

        // Outlier Count Spinner
        c.gridx = 1;
        c.gridy = 7;

        add(outlierCountSpinner, c);

        // Outlier Scale Spinner
        c.gridx = 1;
        c.gridy = 8;

        add(outlierScaleSpinner, c);
    }

    /**
     * Adds a listener for when the fields are changed.
     *
     * @param l The listener to add.
     */
    public void addListener(Listener<SettingsData> l) {
        listeners.add(l);
    }

    /**
     * Fires when field values are changed.
     */
    public void update() {
        SettingsData data = getData();

        for (Listener<SettingsData> l : listeners)
            l.onSubmit(data);
    }

    /**
     * Gets all the data in the fields as a <code>SettingsData</code> instance.
     *
     * @return The <code>SettingsData</code> instance with the values in the fields.
     */
    public SettingsData getData() {
        SettingsData data = new SettingsData();

        data.function = functionTextField.getText();
        data.start = (Double) startSpinner.getValue();
        data.count = (Integer) countSpinner.getValue();
        data.stride = (Double) strideSpinner.getValue();
        data.sigmaX = (Double) sigmaXSpinner.getValue();
        data.sigmaY = (Double) sigmaYSpinner.getValue();
        data.outliersEnabled = outlierCheckbox.isSelected();
        data.outlierCount = (Integer) outlierCountSpinner.getValue();
        data.outlierScale = (Double) outlierScaleSpinner.getValue();

        return data;
    }

    /**
     * Sets the values in the fields to the values in <code>data</code>.
     *
     * @param data The data to set.
     */
    public void setData(SettingsData data) {
        functionTextField.setText(data.function);
        startSpinner.setValue(data.start);
        countSpinner.setValue(data.count);
        strideSpinner.setValue(data.stride);
        sigmaXSpinner.setValue(data.sigmaX);
        sigmaYSpinner.setValue(data.sigmaY);
        outlierCheckbox.setSelected(data.outliersEnabled);
        outlierCountSpinner.setValue(data.outlierCount);
        outlierScaleSpinner.setValue(data.outlierScale);

        updateOutliersGUI();
    }

    /**
     * Updates whether the outliers section is disabled or not.
     */
    private void updateOutliersGUI() {
        outlierEnabled = outlierCheckbox.isSelected();

        outlierCountLabel.setEnabled(outlierEnabled);
        outlierScaleLabel.setEnabled(outlierEnabled);

        outlierCountSpinner.setEnabled(outlierEnabled);
        outlierScaleSpinner.setEnabled(outlierEnabled);
    }

    /**
     * A class to store the settings values.
     */
    public static class SettingsData {
        public String function;
        public Double start;
        public Integer count;
        public Double stride;
        public Double sigmaX;
        public Double sigmaY;
        public Boolean outliersEnabled;
        public Integer outlierCount;
        public Double outlierScale;
    }

}
