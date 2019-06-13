package gui;

import javax.swing.*;
import java.awt.*;

/**
 * The frame containing the main section of the application.
 */
public class MainFrame extends JFrame {

    private final static Dimension SIZE = new Dimension(800, 600);

    private MainTabbedPane mainTabbedPane;

    public MainFrame(String title) {
        super(title);

        mainTabbedPane = new MainTabbedPane();

        add(mainTabbedPane);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point windowLocation = new Point(screenSize.width / 2 - SIZE.width / 2, screenSize.height / 2 - SIZE.height / 2);

        setMinimumSize(SIZE);
        setLocation(windowLocation);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public MainFrame() {
        this("Application");
    }
}
