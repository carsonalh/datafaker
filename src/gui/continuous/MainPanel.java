package gui.continuous;

import function.Function;
import function.FunctionLoader;
import gui.ButtonPanel;
import gui.DataTab;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

import javax.swing.*;
import java.awt.*;

/**
 * The main panel of the continuous tab.
 */
public class MainPanel extends DataTab {

    private static final Dimension SIZE = new Dimension(800, 600);

    private final ContinuousSettings settingsForm;
    private final ContinuousGraph graphPanel;
    private final ButtonPanel buttonPanel;
    private MoreOptionsFrame moreOptionsFrame;

    private ContinuousGenerator generator;

    /**
     * Constructs a <code>MainPanel</code> instance.
     */
    public MainPanel() {
        super();

        // Creation
        settingsForm = new ContinuousSettings();
        graphPanel = new ContinuousGraph(data);
        buttonPanel = new ButtonPanel();

        generator = new ContinuousGenerator(10);

        updateData(settingsForm.getData());

        // Config
        settingsForm.addListener(this::updateData);
        buttonPanel.addSaveListener(data -> saveData());
        buttonPanel.addReseedListener(data -> reseedData());
        buttonPanel.addMoreOptionsListener(data -> openMoreOptionsFrame());

        graphPanel.setBorder(BorderFactory.createEtchedBorder());

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Settings Form
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 2;
        c.anchor = GridBagConstraints.SOUTH;

        add(settingsForm, c);

        // Graph Panel
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 2;
        c.gridwidth = 1;
        c.weightx = 3;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;

        add(graphPanel, c);

        // Button Panel
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.NORTH;

        add(buttonPanel, c);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - SIZE.width / 2, screenSize.height / 2 - SIZE.height / 2);

        setSize(SIZE);
        setVisible(true);
    }

    /**
     * Updates the data in the fields and on the graph.
     *
     * @param data The data to update with.
     */
    private void updateData(ContinuousSettings.SettingsData data) {
        if (data == null)
            return;

        settingsForm.setData(data);

        Function f;

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

        if (data.outliersEnabled) {
            generator.setOutlierCount(data.outlierCount);
            generator.setOutlierScale(data.outlierScale);
        } else {
            generator.setOutlierCount(0);
            generator.setOutlierScale(0d);
        }

        // Update the values in ContinuousSettings
        settingsForm.setData(data);

        this.data = generator.genData();

        graphPanel.setData(this.data);
    }

    public void reseedData() {
        ContinuousSettings.SettingsData settings = settingsForm.getData();
        updateData(settings);
    }

    private void openMoreOptionsFrame() {
        if (moreOptionsFrame == null) {
            moreOptionsFrame = new MoreOptionsFrame(ContinuousOptions.valueOf(settingsForm.getData()));
            moreOptionsFrame.addContinuousOptionsListener(this::updateData);
            moreOptionsFrame.addCloseListener(data -> MainPanel.this.moreOptionsFrame = null);
        }
    }

}
