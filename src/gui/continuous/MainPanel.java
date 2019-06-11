package gui.continuous;

import function.FunctionLoader;
import function.TrendFunction;
import gui.ButtonPanel;
import gui.DataTab;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends DataTab {

    private static final Dimension SIZE = new Dimension(800, 600);

    private final ContinuousSettings settingsForm;
    private final ContinuousGraph graphPanel;
    private final ButtonPanel buttonPanel;

    private ContinuousGenerator generator;

    public MainPanel() {
        super();

        // Creation
        settingsForm = new ContinuousSettings();
        graphPanel = new ContinuousGraph(data);
        buttonPanel = new ButtonPanel();

        generator = new ContinuousGenerator(10);

        updateGraph(settingsForm.getData());

        // Config
        settingsForm.addListener(this::updateGraph);
        buttonPanel.addSaveListener(this::saveData);
        buttonPanel.addReseedListener(this::reseedData);

        graphPanel.setBorder(BorderFactory.createEtchedBorder());

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

        add(graphPanel, c);

        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 1;

        add(buttonPanel, c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - SIZE.width / 2, screenSize.height / 2 - SIZE.height / 2);

        setSize(SIZE);
        setVisible(true);
    }

    private void updateGraph(ContinuousSettings.SettingsData data) {
        if (data == null)
            return;

        TrendFunction f;

        try {
            f = FunctionLoader.loadFromString(data.function);
        } catch (UnknownFunctionOrVariableException e) {
            JOptionPane.showMessageDialog(
                    this,
                    String.format("Unknown function or variable '%s'", e.getExpression()),
                    "Error in Function",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        generator.setFunction(f);
        generator.setStart(data.start);
        generator.setCount(data.count);
        generator.setStride(data.stride);
        generator.setSigmaX(data.sigmaX);
        generator.setSigmaY(data.sigmaY);
        generator.setOutlierCount(data.outlierCount);
        generator.setOutlierScale(data.outlierScale);

        this.data = generator.genData();

        graphPanel.setData(this.data);
    }

    public void reseedData() {
        ContinuousSettings.SettingsData settings = settingsForm.getData();
        updateGraph(settings);
    }

}
