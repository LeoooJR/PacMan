package fr.upsaclay.bibs.pacman.control;

import fr.upsaclay.bibs.pacman.PacManException;

/**
 * Thrown if an action is asked from the controller at a forbidden time
 * Typically represent a big in game implementation
 */
public class ForbiddenActionException extends PacManException {

    public ForbiddenActionException(GameAction action) {
        super("Forbiden action : " + action);
    }
}
