package fr.upsaclay.bibs.pacman.control;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.board.Board;

/**
 * The interface for the Controller
 * The controller is launched at the beginning of the application
 * Its role is to control the model and the view and to perform designated
 * action on the model
 */
public interface Controller {


    /**
     * Initialization of the controller at the beginning
     * of the application.
     * Depending on the developer choices, this could for
     * example initialize a new game by default
     * This would also create and launch the view and user
     * interface
     * @throws PacManException if the initialization fails
     */
    void initialize() throws PacManException;

    /**
     * Initialize a new game
     * The initialization should do necessary steps on the model
     * and the view :
     * for example, creating a new board, update the view etc.
     * @throws PacManException if the initialization fails
     */
    void initializeNewGame() throws PacManException;

    /**
     * Setting the game type
     * @param gameType a game type to be played
     */
    void setGameType(GameType gameType);

    /**
     * Getting the game type
     * @return the current game type of the controller
     */
    GameType getGameType();

    /**
     * Receives an action order (typically from the view)
     * and perform necessary actions on model and view
     * @param action a game action
     * @throws ForbiddenActionException if the action is not permitted at this step
     */
    void receiveAction(GameAction action) throws PacManException;

    /**
     * Return the current board
     * @return the board
     */
    Board getBoard();

    /**
     * Get the appropriate controller depending on desired interface type
     * Different types correspond to different possible views or game interfaces
     * @param mode the controller mode
     * @return the Controller
     */
    static Controller getController(InterfaceMode mode) {
		switch (mode){
            case InterfaceMode.VISUAL:
                return new VisualController();
            default:
                return new SimpleController();
        }
    }
}
