package gui;

import com.sun.istack.internal.NotNull;
import general.Listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * The "more options" frame of the application. Has all the settings that are available.
 *
 * @param <OptionClass> The type of options to send / listen for.
 */
@SuppressWarnings("FieldCanBeLocal")
public class MoreOptionsFrame<OptionClass, Table extends MoreOptionsTable<OptionClass>> extends JFrame {

    private static final Dimension SIZE = new Dimension(400, 600);
    private static final String TITLE = "More Options";

    private final Table table;
    private final JScrollPane tableContainer;
    private final JButton submitButton;
    private final JButton closeButton;

    private final ArrayList<Listener<OptionClass>> optionsListeners;
    private final ArrayList<Listener<Void>> closeListeners;

    /**
     * Constructs a <code>MoreOptionsFrame</code> instance.
     */
    public MoreOptionsFrame(@NotNull Table table) {
        super();

        // Creation
        this.table = table;

        tableContainer = new JScrollPane(table);
        submitButton = new JButton("Save");
        closeButton = new JButton("Close");

        closeListeners = new ArrayList<>();
        optionsListeners = new ArrayList<>();

        // Setup
        submitButton.addActionListener(e -> submitData());
        closeButton.addActionListener(e -> close());

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

        add(closeButton, c);

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

    /**
     * Fired when the window closes. Fires all the <code>Listener&lt;Void&gt;</code> instances
     * in the <code>closeListeners</code> <code>ArrayList</code>.
     */
    @SuppressWarnings("unchecked")
    private void close() {
        for (Listener l : closeListeners)
            l.onSubmit(null);

        dispose();
    }

    /**
     * Adds a <em>close</em> listener to the <code>closeListeners</code> <code>ArrayList</code>.
     *
     * @param l The listener to add.
     */
    public void addCloseListener(Listener<Void> l) {
        closeListeners.add(l);
    }

    /**
     * Fired when the data needs to be submitted.
     */
    @SuppressWarnings("unchecked")
    private void submitData() {
        OptionClass options = getData();

        for (Listener l : optionsListeners)
            l.onSubmit(options);
    }

    /**
     * Gets the data in the fields.
     *
     * @return The data in the fields.
     */
    private OptionClass getData() {
        return table.getOptions();
    }

    /**
     * Adds a listener for when the field values are saved.
     *
     * @param l The listener to add.
     */
    public void addOptionsListener(Listener<OptionClass> l) {
        optionsListeners.add(l);
    }

}
