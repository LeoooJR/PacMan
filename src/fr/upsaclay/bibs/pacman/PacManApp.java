package fr.upsaclay.bibs.pacman;

import fr.upsaclay.bibs.pacman.control.Controller;
import fr.upsaclay.bibs.pacman.control.InterfaceMode;

import javax.swing.*;

/**
 * The main application
 */
public class PacManApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Controller controller = Controller.getController(InterfaceMode.VISUAL);
                controller.setGameType(GameType.CLASSIC);
                controller.initialize();
            } catch (PacManException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
