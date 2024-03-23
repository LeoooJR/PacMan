package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

/**
 * The interface corresponding to all actors on the board
 * i.e.: PacMan and the ghosts
 *
 * @author Viviane Pons
 */
public interface Actor {

    /**
     * Return the type of actor it is
     * i.e. either PacMan, a Ghost, or a bonus
     * @return an ActorType
     */
    ActorType getType();

    /**
     * Return the board on which this actor
     * is placed
     * @return the game board
     */
    Board getBoard();

    /**
     * Return the x position of the actor
     * (in pixels)
     * @return the x position in pixels
     */
    int getX();

    /**
     * Return the y position of the actor
     * (in pixels)
     * @return the y position in pixels
     */
    int getY();

    /**
     * Set the actor position on the maze
     * @param x, the x position in pixels
     * @param y, the y position in pixels
     */
    void setPosition(double x, double y);

    /**
     * Return the current tile position corresponding
     * to the actor position the board
     * @return a tile position
     */
    TilePosition getCurrentTile();

    /**
     * Start the actor at the beginning of the game
     * Perform all necessary action to start the actor at the beginning of the game
     * (this often require to initialize some internal paramaters of the actor)
     */
    void start();

    /**
     * Return the current direction
     * the actor is moving
     * @return a direction
     */
    Direction getDirection();

    /**
     * Sets the actor direction
     * @param direction a direction
     */
    void setDirection(Direction direction);

    /**
     * Set an intended direction
     * for the actor to change direction
     * if possible
     * @param direction, a direction
     */
    void setIntention(Direction direction);

    /**
     * Return the current intention
     * of the actor for its next direction
     * @return a direction
     */
    Direction getIntention();

    /**
     * Return if the actor is blocked by a wall
     * An actor is blocked if they have tried to move and
     * could not because of the wall
     * @return true if the actor is blocked
     */
    boolean isBlocked();

    /**
     * Perform the next move on the actor
     */
    void nextMove();

    /**
     * Perform necessary actions for the next game
     * frame (this could include a move but not always)
     */
    void nextFrame();

    // Step 2
    // The methods below won't be used / tested before step 2

    /**
     * Set the actor speed
     * The speed is the number of pixels moved at each frame
     * as a decimal number
     * @param speed, a deciman number
     */
    void setSpeed(double speed);

    /**
     * Return the speed of the actor
     * @return the speed
     */
    double getSpeed();

    /**
     * The actor x position as a decimal number
     * @return  the x position of the actor
     */
    double getRealX();

    /**
     * The actor y position as a decimal number
     * @return the y position of the actor
     */
    double getRealY();

    /**
     * Stops the actor for a certain number of frames
     * @param nbFrames a positive integer
     */
    void setStopTime(int nbFrames);


}
