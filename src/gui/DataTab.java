package gui;

import csv.CSVWriter;
import data.DataGenerator;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public abstract class DataTab extends JPanel {

    protected DataGenerator<Double> generator;
    protected SettingsForm settingsForm;
    protected GraphPanel graphPanel;
    protected ButtonPanel buttonPanel;

    protected Double[][] data;

    protected void saveData() {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV File", "csv"));
        int chosenFileResult = fileChooser.showSaveDialog(this);
        File chosenFile = fileChooser.getSelectedFile();

        if (chosenFile == null || chosenFileResult != JFileChooser.APPROVE_OPTION)
            return;

        if (!chosenFile.getName().matches("^.*\\.csv$")) {
            String newFilename = chosenFile.getName() + ".csv";
            String dir = chosenFile.getParent();
            chosenFile = new File(Paths.get(dir, newFilename).toString());
        }

        if (chosenFile.exists()) {
            int confirmationResult = JOptionPane.showOptionDialog(
                    this,
                    "File \"" + chosenFile.getName() + "\" already exists. Do you want to replace it?",
                    "File \"" + chosenFile.getName() + "\" already exists.",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    new Object[]{"Yes", "No"},
                    JOptionPane.NO_OPTION
            );

            if (confirmationResult != JOptionPane.YES_OPTION) {
                return;
            }
        }

        CSVWriter<Double> writer = new CSVWriter<>(chosenFile);

        writer.setData(data);

        try {
            writer.write();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error Saving Data",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    public void reseedData() {
        data = generator.genData();
    }

}
