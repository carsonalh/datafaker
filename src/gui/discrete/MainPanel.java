package gui.discrete;

import gui.ButtonPanel;
import gui.DataTab;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends DataTab {

    private ButtonPanel buttonPanel;
    private DiscreteSettings settingsForm;
    private DiscreteGraph discreteGraph;

    private DiscreteGenerator generator;

    public MainPanel() {
        generator = new DiscreteGenerator();

        // Creation
        data = generator.genData();
        buttonPanel = new ButtonPanel();
        settingsForm = new DiscreteSettings();
        discreteGraph = new DiscreteGraph(data);

        // Setup
        settingsForm.addListener(this::updateGraph);
        buttonPanel.addReseedListener(this::reseedData);
        buttonPanel.addSaveListener(this::saveData);

        discreteGraph.setBorder(BorderFactory.createEtchedBorder());

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;

        add(settingsForm, c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 3;

        add(discreteGraph, c);

        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 1;

        add(buttonPanel, c);
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

        double rangeEnd = data.rangeStart + (data.rangeCount + 1) * data.rangeStride;

        settingsForm.setCenterRange(data.rangeStart - data.sigmaX, rangeEnd + data.sigmaX);

        this.data = generator.genData();
        discreteGraph.setData(this.data);
    }

}
