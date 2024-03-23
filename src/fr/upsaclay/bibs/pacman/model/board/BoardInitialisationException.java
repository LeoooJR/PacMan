package fr.upsaclay.bibs.pacman.model.board;

import fr.upsaclay.bibs.pacman.PacManException;

/**
 * Exception raised if something went wrong at board initialization (typically, an error in the maze file)
 */
public class BoardInitialisationException extends PacManException {

    public BoardInitialisationException() {
        super();
    }
    public BoardInitialisationException(Exception e) {
        super(e);
    }

}
