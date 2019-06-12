package gui.continuous;

import general.Listener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MoreOptionsFrame extends JFrame {

    private static final Dimension SIZE = new Dimension(400, 600);
    private static final String TITLE = "More Options";

    private final JTable table;
    private final JScrollPane tableContainer;
    private final JButton submitButton;
    private final JButton cancelButton;

    private final ArrayList<Listener<ContinuousOptions>> listeners;

    public MoreOptionsFrame() {
        super();

        // Creation
        table = new JTable();
        tableContainer = new JScrollPane(table);
        submitButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        listeners = new ArrayList<>();

        // Setup
        submitButton.addActionListener(e -> submitData());
        cancelButton.addActionListener(e -> MoreOptionsFrame.this.dispose());

        // Layout
        setLayout(new GridBagLayout());

        //GLOBAL CONSTRAINTS
        GridBagConstraints c = new GridBagConstraints();

        // Table
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 15;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;

        add(tableContainer, c);

        // BUTTONS
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Cancel Button
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = .5;
        c.weighty = 1;
        c.gridwidth = 1;
        c.gridheight = 1;

        add(cancelButton, c);

        // Submit Button
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = .5;
        c.weighty = 1;
        c.gridwidth = 1;
        c.gridheight = 1;

        add(submitButton, c);

        // Show the Window
        setMinimumSize(SIZE);
        setAlwaysOnTop(true);
        setTitle(TITLE);
        setVisible(true);
    }

    private void submitData() {
        ContinuousOptions options = getData();

        for (Listener l : listeners)
            l.onSubmit(options);
    }

    private ContinuousOptions getData() {
        return null;
    }

    public void addContinuousOptionsListener(Listener<ContinuousOptions> l) {
        listeners.add(l);
    }

}
