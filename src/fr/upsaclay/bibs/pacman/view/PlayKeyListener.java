package fr.upsaclay.bibs.pacman.view;

import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.control.Controller;
import fr.upsaclay.bibs.pacman.control.GameAction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayKeyListener implements KeyListener {

    private final Controller controller;

    public PlayKeyListener(Controller controller){
        this.controller = controller;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    controller.receiveAction(GameAction.RIGHT);
                    break;
                case KeyEvent.VK_LEFT:
                    controller.receiveAction(GameAction.LEFT);
                    break;
                case KeyEvent.VK_UP:
                    controller.receiveAction(GameAction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    controller.receiveAction(GameAction.DOWN);
                    break;
                case KeyEvent.VK_ESCAPE:
                    controller.receiveAction((GameAction.PAUSE));
                    break;
            }
        } catch (PacManException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
