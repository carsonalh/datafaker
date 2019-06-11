package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ButtonPanel extends JPanel {

    private final JButton saveButton;
    private final JButton reseedButton;

    private final ArrayList<SaveListener> saveListeners = new ArrayList<>();
    private final ArrayList<ReseedListener> reseedListeners = new ArrayList<>();

    public ButtonPanel() {
        super();

        // Creation
        reseedButton = new JButton("Re-Seed Data");
        saveButton = new JButton("Save...");

        // Setup
        saveButton.addActionListener(e -> {
            for (SaveListener l : saveListeners)
                l.onSave();
        });

        reseedButton.addActionListener(e -> {
            for (ReseedListener l : reseedListeners)
                l.onReseed();
        });

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Reseed
        c.gridx = 0;
        c.gridy = 0;

        add(reseedButton, c);

        // Save
        c.gridx = 0;
        c.gridy = 1;

        add(saveButton, c);
    }

    public void addSaveListener(SaveListener l) {
        saveListeners.add(l);
    }

    public void addReseedListener(ReseedListener l) {
        reseedListeners.add(l);
    }

    public interface SaveListener {
        void onSave();
    }

    public interface ReseedListener {
        void onReseed();
    }

}
