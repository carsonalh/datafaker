package gui;

import general.Listener;

import javax.swing.*;
import java.util.ArrayList;

/**
 * An abstract class for making settings for each of the tabs.
 *
 * @param <T> The type of data that will be contained by the form (usually a custom
 *            data structure).
 */
public abstract class SettingsForm<T> extends JPanel {

    protected final ArrayList<Listener<T>> listeners;

    public SettingsForm() {
        super();
        listeners = new ArrayList<>();
    }

}
