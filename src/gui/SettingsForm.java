package gui;

import general.Listener;

import javax.swing.*;
import java.util.ArrayList;

public abstract class SettingsForm<T> extends JPanel {

    protected final ArrayList<Listener<T>> listeners;

    public SettingsForm() {
        super();
        listeners = new ArrayList<>();
    }

}
