package gui;

import javax.swing.*;

/**
 * The pane containing the tabs in the application.
 */
public class MainTabbedPane extends JTabbedPane {

    private gui.continuous.MainPanel continuousPanel;
    private gui.discrete.MainPanel discretePanel;

    public MainTabbedPane() {
        continuousPanel = new gui.continuous.MainPanel();
        discretePanel = new gui.discrete.MainPanel();

        addTab("Continuous", continuousPanel);
        addTab("Discrete", discretePanel);
    }

}
