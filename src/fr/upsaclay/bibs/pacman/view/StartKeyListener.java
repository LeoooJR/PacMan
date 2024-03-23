package fr.upsaclay.bibs.pacman.view;

import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.control.Controller;
import fr.upsaclay.bibs.pacman.control.GameAction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StartKeyListener implements KeyListener {

    private final Controller controller;
    private final PacManGameView view;
    public StartKeyListener(Controller controller, PacManGameView view){
        this.controller = controller;
        this.view = view;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        try {
            switch (view.getViewLayout()){
                case INIT:
                    controller.receiveAction(GameAction.START); break;
                case PAUSE:
                    controller.receiveAction(GameAction.RESUME); break;
            }

        } catch (PacManException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
