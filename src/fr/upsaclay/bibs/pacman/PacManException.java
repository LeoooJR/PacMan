package fr.upsaclay.bibs.pacman;

/**
 * A super exception for all problems that might occur during the game
 */
public class PacManException extends Exception {

    public PacManException() {
        super();
    }

    public PacManException(String message) {
        super(message);
    }

    public PacManException(Exception e) {
        super(e);
    }
}
