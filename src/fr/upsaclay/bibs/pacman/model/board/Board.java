package fr.upsaclay.bibs.pacman.model.board;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.*;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * The interface corresponding to the game board
 * It contains the maze and the actors (pacman and
 * the ghosts)
 *
 * @author Viviane Pons
 */
public interface Board {

    /**
     * Return the type of game of the board
     * Depending on the type, the maze or other initializations might be different
     * @return the game type
     */
    GameType getGameType();

    /**
     * Initialization of the board
     * (loads the maze, create and place the actors, etc.)
     * @throws PacManException in case something went wrong
     */
     void initialize() throws PacManException;

    /**
     * Start the actors
     * Perform all necessary actions to start actors at the beginning of the game
     */
    void startActors();

    /** Return the maze
     * @return the maze
     */
    Maze getMaze();

    /**
     * Return PacMan
     * @return the PacMan actor
     */
    Actor getPacMan();

    /**
     * Perform all necessary actions for the next game frame
     * This might require to move the actors,
     * perform some checks, etc.
     */
    void nextFrame();

    /**
     * Create a board depending on the game type
     * @param type a game type
     * @return the board
     */
    static Board createBoard(GameType type) {
        Board board = (type == GameType.CLASSIC ? new ClassicBoard() : new TestBoard());
        return board;
    }

    // Step 2
    // The methods below won't be used / tested before step 2

    /**
     * Return the current score of the game
     * @return the score
     */
    int getScore();

    /**
     * Return the current state of the board
     * used to detect end of level / end of game
     * @return the board state
     */
    BoardState getBoardState();

    void setBoardState(BoardState state);

    /**
     * Return the ghost of a given type
     * If the board does not contain such a ghost, it returns null
     * @param ghostType, the type of ghost
     * @return a ghost or null
     */
    Ghost getGhost(GhostType ghostType);

    /**
     * Return the list of ghosts present on the board
     * @return a list of ghost (might be empty)
     */
    List<Ghost> getGhosts();

    // Step 3
    // The methods below won't be used / tested before step 3

    /**
     * Return the current level of the game
     * @return a positive integer
     */
    int getLevel();

    /**
     * Perfom all necessary actions to initiliaze a new level
     * (might need to load the maze, to place the actors, etc)
     * @param level, a positive integer
     * @throws PacManException if anything goes wrong
     */
    void initializeNewLevel(int level) throws PacManException;

    /**
     * Sets the number of extra lives that pacman has
     * @param nbLives, a non negative integer
     */
    void setNumberOfLives(int nbLives);

    /**
     * Return the current number of extra lives
     * @return a non negative integer
     */
    int getNumberOfLives();

    /**
     * Perform all necessary actions to initialize the game after a life has been lost
     * (reduce the nb of lives, replace the actors, re-initialize certain values)
     */
    void initializeNewLife();

    /**
     * Return whether the board contain a certain type of ghost
     * @param ghostType the type of ghost
     * @return true if this ghost is on the board
     */
    boolean hasGhost(GhostType ghostType);

    /**
     * Disables a certain ghost before game initialization
     * @param ghostType the type of ghost to disable
     */
    void disableGhost(GhostType ghostType);

    /**
     * Disable the use of state time before game initialization
     */
    void disableStateTime();

    /**
     * Return a pseudo-random direction
     * The random generator is initialized once at the beginning of the game
     * @return a pseudo-random direction
     */
    Direction getRandomDirection();

    /**
     * Re turn the tile position of the pen entry
     * (this corresponds to the ghost target when dead)
     * @return a tile position
     */
    TilePosition penEntry();

    /**
     * Return the minimum y value a ghost can take when inside the pen
     * @return a positive integer
     */
    int minYPen();

    /**
     * Return the maximal y value a ghost can take when inside the pen
     * @return a positive integer
     */
    int maxYPen();

    /**
     * Return the x position of given ghost type inside the pen
     * @param type, a ghost type
     * @return a positive integer
     */
    int penGhostXPosition(GhostType type);

    /**
     * Return the y position of given ghost type inside the pen
     * @param type, a ghost type
     * @return a positive integer
     */
    int penGhostYPosition(GhostType type);

    /**
     * Return the x position used by ghost to enter / leave the ghost pen
     * @return a positive integer
     */
    int outPenXPosition();

    /**
     * Return the y position used by ghost to enter / leave the ghost pen
     * @return a positive integer
     */
    int outPenYPosition();

    /**
     * Return the counter used to count the number of successive frames without eating dots
     * This is a way to count the time passed between 2 dots are eaten
     * @return a counter
     */
    Counter noDotCounter();

    /**
     * Return the board "special counter" used for allowing ghosts out of the pen
     * @return a counter
     */
    Counter specialDotCounter();

    /**
     * Return the normal speed of pacman at the board current level
     * @return a speed as a decimal
     */
    double getLevelPacManSpeed();

    /**
     * Return the speed of pacman in "fright" mode at the board current level
     * @return a speed as a decimal
     */
    double getFrightPacManSpeed();

    /**
     * Return the normal speed of ghost at the board current level
     * @return a speed as a decimal
     */
    double getLevelGhostSpeed();

    /**
     * Return the slow speed of ghosts at current level (inside the tunnel)
     * @return a speed as a decimal
     */
    double getTunnelGhostSpeed();

    /**
     * return the speed of ghosts in "fright" mode at current level
     * @return a speed as a decimal
     */
    double getFrightGhostSpeed();

    /**
     * return the speed of ghosts when dead at current level
     * @return a speed as a decimal
     */
    double getDeadGhostSpeed();

    // Step 4
    // The methods below won't be used / tested before step 4

    /**
     * Sets the score value at which pacman gains a new life
     * @param score, a positive integer
     */
    void setExtraLifeScore(int score);

    /**
     * Return the score value at chich pacman gains a new life
     * @return a positive integer
     */
    int getExtraLifeScore();

    /**
     * Return the current bonus n the board if existing, null otherwise
     * @return a Bonus or null
     */
    Bonus getCurrentBonus();

    /**
     * Return the bonus type associated with given level
     * @param level, a positive integer
     * @return the bonus type of this level
     */
    BonusType getLevelBonusType(int level);

    /**
     * Place the bonus associated with current level on board at its intended position
     */
    void setBonusOnBoard();

    /**
     * Return the ghost speed at given Elroy value
     * Note : if elroyNumber is zero, this is the level ghost speed
     * @param elroyNumber, the ghost elroy value (0, 1 or 2)
     * @return the elroy ghost speed
     */
    double getElroyGhostSpeed(int elroyNumber);

    /**
     * Return the dot value (number of remaining dots) at which Blinky turns to elroy (1 or 2)
     * @param elroyNumber, 1 or 2
     * @return a positive number
     */
    int getElroyDotValue(int elroyNumber);

    /**
     * Return the list of events that occured at the last frame
     * @return a list (might be empty)
     */
    List<BoardEvent> getCurrentEvents();
}
