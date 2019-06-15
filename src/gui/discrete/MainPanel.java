package gui.discrete;

import gui.ButtonPanel;
import gui.DataTab;
import gui.MoreOptionsFrame;

import javax.swing.*;
import java.awt.*;

/**
 * The panel for making discrete data.
 */
@SuppressWarnings("FieldCanBeLocal")
public class MainPanel extends DataTab {

    private ButtonPanel buttonPanel;
    private DiscreteSettings settingsForm;
    private DiscreteGraph discreteGraph;

    private MoreOptionsFrame<DiscreteOptions, DiscreteOptionsTable> moreOptionsFrame;

    private DiscreteGenerator generator;

    public MainPanel() {
        generator = new DiscreteGenerator();

        // Creation
        data = generator.genData();
        buttonPanel = new ButtonPanel();
        settingsForm = new DiscreteSettings();
        discreteGraph = new DiscreteGraph(data);

        // Setup
        updateCenter(generator);

        settingsForm.addListener(this::updateGraph);
        buttonPanel.addReseedListener(data -> reseedData());
        buttonPanel.addSaveListener(data -> saveData());
        buttonPanel.addMoreOptionsListener(data -> openMoreOptionsFrame());

        discreteGraph.setBorder(BorderFactory.createEtchedBorder());

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Settings Form
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.SOUTH;
        c.weightx = 1;
        c.weighty = 2;

        add(settingsForm, c);

        // Discrete Graph
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 3;
        c.weighty = 3;

        add(discreteGraph, c);

        // Button Panel
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 1;
        c.weighty = 1;

        add(buttonPanel, c);
    }

    private void openMoreOptionsFrame() {
        if (moreOptionsFrame == null) {
            DiscreteOptions options = DiscreteOptions.valueOf(settingsForm.getData());
            DiscreteOptionsTable table = new DiscreteOptionsTable(options);
            moreOptionsFrame = new MoreOptionsFrame<>(table);
            moreOptionsFrame.addOptionsListener(this::updateData);
            moreOptionsFrame.addCloseListener(data -> MainPanel.this.moreOptionsFrame = null);
        }
    }

    private void updateData(DiscreteOptions data) {
        settingsForm.setData(data);

        generator.setCount(data.count);
        generator.setRangeCount(data.rangeCount);
        generator.setRangeStart(data.rangeStart);
        generator.setRangeStride(data.rangeStride);
        generator.setSigmaX(data.sigmaX);
        generator.setCenter(data.center);
    }

    public void reseedData() {
        data = generator.genData();
        discreteGraph.setData(data);
    }

    private void updateGraph(DiscreteSettings.SettingsData data) {
        generator.setRangeStart(data.rangeStart);
        generator.setRangeCount(data.rangeCount);
        generator.setRangeStride(data.rangeStride);
        generator.setSigmaX(data.sigmaX);
        generator.setCenter(data.center);
        generator.setCount(data.count);

        updateCenter(generator);

        this.data = generator.genData();
        discreteGraph.setData(this.data);
    }

    private void updateCenter(DiscreteSettings.SettingsData data) {
        double rangeEnd = data.rangeStart + data.rangeCount * data.rangeStride;
        settingsForm.setCenterRange(data.rangeStart - data.sigmaX, rangeEnd + data.sigmaX);
    }

    private void updateCenter(DiscreteGenerator generator) {
        double rangeEnd = generator.getRangeStart() + generator.getRangeCount() * generator.getRangeStride();
        settingsForm.setCenterRange(generator.getRangeStart() - generator.getSigmaX(), rangeEnd + generator.getSigmaX());
    }

}
