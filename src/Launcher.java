import gui.MainFrame;

import javax.swing.*;

public class Launcher implements Runnable {

    private static final String APPLICATION_TITLE = "Data Faker";

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Launcher());
    }

    @Override
    public void run() {
        new MainFrame(APPLICATION_TITLE);
    }

}
