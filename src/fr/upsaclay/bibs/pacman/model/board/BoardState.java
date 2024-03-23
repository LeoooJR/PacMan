package fr.upsaclay.bibs.pacman.model.board;

/**
 * List of possible state the board can be in
 *
 * (not used before step 2)
 */
public enum BoardState {
    /** Before the game is started **/
    INITIAL,
    /** Game started, everything normal **/
    STARTED,
    /** All the dots have been eaten **/
    LEVEL_OVER,
    /** PacMan looses a life (eaten by a ghost) **/
    LIFE_OVER,
    /** PacMan has lost all his lives **/
    GAME_OVER
}
