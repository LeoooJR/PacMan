package fr.upsaclay.bibs.pacman.view;

import javax.swing.*;
import java.awt.*;

public class PausePanel extends JPanel {
    public PausePanel() {
        setBackground(Color.BLACK);
        JLabel pauseLabel = new JLabel("Pause");
        pauseLabel.setFont(new Font("Futura", Font.BOLD, 33));
        pauseLabel.setForeground(Color.WHITE);
        add(pauseLabel);
    }
}
