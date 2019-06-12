package gui.continuous;

import general.Listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MoreOptionsFrame extends JFrame {

    private static final Dimension SIZE = new Dimension(400, 600);
    private static final String TITLE = "More Options";

    private final JTable table;
    private final JScrollPane tableContainer;
    private final JButton submitButton;
    private final JButton cancelButton;

    private final ArrayList<Listener<ContinuousOptions>> continuousOptionsListeners;
    private final ArrayList<Listener<Void>> closeListeners;

    public MoreOptionsFrame(ContinuousOptions options) {
        super();

        // Creation
        if (options == null)
            table = new MoreOptionsTable();
        else
            table = new MoreOptionsTable(options);
        tableContainer = new JScrollPane(table);
        submitButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        closeListeners = new ArrayList<>();
        continuousOptionsListeners = new ArrayList<>();

        // Setup
        submitButton.addActionListener(e -> submitData());
        cancelButton.addActionListener(e -> close());

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
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MoreOptionsFrame.this.close();
            }
        });

        setVisible(true);
    }

    public MoreOptionsFrame() {
        this(null);
    }

    private void close() {
        for (Listener l : closeListeners)
            l.onSubmit(null);

        dispose();
    }

    public void addCloseListener(Listener<Void> l) {
        closeListeners.add(l);
    }

    private void submitData() {
        ContinuousOptions options = getData();

        for (Listener l : continuousOptionsListeners)
            l.onSubmit(options);
    }

    private ContinuousOptions getData() {
        return null;
    }

    public void addContinuousOptionsListener(Listener<ContinuousOptions> l) {
        continuousOptionsListeners.add(l);
    }

}
