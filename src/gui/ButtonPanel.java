package gui;

import general.Listener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The button panel for options concerning reseeding data, saving data, and opening all the options.
 */
@SuppressWarnings("FieldCanBeLocal")
public class ButtonPanel extends JPanel {

    private final JButton saveButton;
    private final JButton reseedButton;
    private final JButton moreOptionsButton;

    private final ArrayList<Listener<Void>> saveListeners = new ArrayList<>();
    private final ArrayList<Listener<Void>> reseedListeners = new ArrayList<>();
    private final ArrayList<Listener<Void>> moreOptionsListeners = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public ButtonPanel() {
        super();

        // Creation
        reseedButton = new JButton("Re-Seed Data");
        saveButton = new JButton("Save...");
        moreOptionsButton = new JButton("More Options...");

        // Setup
        saveButton.addActionListener(e -> {
            for (Listener l : saveListeners)
                //noinspection unchecked
                l.onSubmit(null);
        });

        reseedButton.addActionListener(e -> {
            for (Listener l : reseedListeners)
                l.onSubmit(null);
        });

        moreOptionsButton.addActionListener(e -> {
            for (Listener l : moreOptionsListeners)
                l.onSubmit(null);
        });

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Reseed
        c.gridx = 0;
        c.gridy = 0;

        add(reseedButton, c);

        // Save
        c.gridx = 0;
        c.gridy = 1;

        add(saveButton, c);

        // More Options
        c.gridx = 0;
        c.gridy = 2;

        add(moreOptionsButton, c);
    }

    public void addSaveListener(Listener<Void> l) {
        saveListeners.add(l);
    }

    public void addReseedListener(Listener<Void> l) {
        reseedListeners.add(l);
    }

    public void addMoreOptionsListener(Listener<Void> l) {
        moreOptionsListeners.add(l);
    }

}
