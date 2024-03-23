package fr.upsaclay.bibs.pacman.view;

import fr.upsaclay.bibs.pacman.control.Controller;
import fr.upsaclay.bibs.pacman.model.board.Board;

/**
 * The Interface of a PacMan visual user interface
 */
public interface PacManView {

    /**
     * Sets the current board
     * the view should never perform any action on the board
     * the board is set only so that the view has the information to show the game
     * it should NOT be modified by the view
     * @param board a PacMan board
     */
    void setBoard(Board board);

    /**
     * Sets the controller
     * this is used to send action to the controller when the user interacts
     * with the view
     * @param controller, the controller of the application
     */
    void setController(Controller controller);

    /**
     * View initialization
     */
    void initialize();

    /**
     * Sets the current layout
     * layouts correspond to game phases
     * when a new layout is set, the view should take all necessary actions
     * for updating the user interface
     * @param layout, the game layout to show
     */
    void setLayout(PacManLayout layout);

    /**
     * Update the view (for example, because something has changed on the board)
     */
    void update();

}
