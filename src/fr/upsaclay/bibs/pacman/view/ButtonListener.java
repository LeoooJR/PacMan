package fr.upsaclay.bibs.pacman.view;

import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.control.Controller;
import fr.upsaclay.bibs.pacman.control.GameAction;

import javax.naming.ldap.Control;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

    private final Controller controller;

    private final GameAction action;

    public ButtonListener(Controller controller, GameAction action){
        this.controller = controller;
        this.action = action;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            controller.receiveAction(action);
        } catch (PacManException ex) {
            throw new RuntimeException(ex);
        }
    }
}
