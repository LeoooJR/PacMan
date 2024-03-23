package fr.upsaclay.bibs.pacman.view;

/**
 * List the different game phases corresponding to different user interfaces to be shown
 * You can edit depending on your own game
 */
public enum PacManLayout {
    /** initial layout when the application is launched **/
    INIT,
    /** The game is on **/
    GAME_ON,
    /** the game is paused **/
    PAUSE,

    // Step 2 layouts

    LEVEL_OVER,
    GAME_OVER,

    // Step 3 layouts

    LIFE_OVER
}
