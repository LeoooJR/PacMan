package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Counter;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

/**
 * The interface representing the ghosts of the game.
 * A ghost is an actor, just like pacman, but it also has
 * some extra methods.
 * (not used before Step 2)
 */
public interface Ghost extends Actor {

    /**
     * Return the type of ghost it is
     * @return a ghost type
     */
    GhostType getGhostType();

    /**
     * Return the target of the ghost at this current moment
     * Sometimes, it is a fix target or it can depend on some other actors
     * of the board.
     * The target is used by the ghost to decide its next move
     * @return the current target as a tile position
     */
    TilePosition getTarget();

    // Step 3
    // The methods below won't be used / tested before step 3

    /**
     * Sets the Ghost state, which defines in particular its target and moves
     * @param state, a ghost state
     */
    void setGhostState(GhostState state);

    /**
     * Perform all necessary actions for changing the ghost state from it current state to the new one
     * @param state, the new state of the ghost
     */
    void changeGhostState(GhostState state);

    /**
     * Return the current ghost state
     * @return a ghost state
     */
    GhostState getGhostState();

    /**
     * Sets the ghost pen state,
     * i.e. whether the ghost is in the pen, out or getting in / getting out
     * @param state, a ghost pen state
     */
    void setGhostPenState(GhostPenState state);

    /**
     * Return the current ghost pen state
     * @return the ghost pe state
     */
    GhostPenState getGhostPenState();

    /**
     * Sets the direction that the ghost should take when its gets out of the ghost pen
     * @param dir a direction
     */
    void setOutOfPenDirection(Direction dir);

    /**
     * Return the direction taken by the ghost when getting out of the pen
     * @return a direction
     */
    Direction getOutOfPenDirection();

    /**
     * If out of pen : sends the intention to reverse direction
     * If in pen or getting in or out of pen : set the out of pen direction to right
     */
    void reverseDirectionIntention();

    /**
     * Return whether the ghost has a "dot counter" to decide when it gets out of the ghost pen
     * @return true if the ghost uses a dot counter
     */
    boolean hasDotCounter();

    /**
     * Return the dot counter of the ghost if it uses one
     * @return a dot counter or null
     */
    Counter getDotCounter();

    // Step 4
    // The methods below won't be used / tested before step 3

    /**
     * Sets the "Elroy" value of the ghost
     * typically, 0 (not in elroy mode), 1, or 2
     * @param elroy the elroy value
     */
    void setElroy(int elroy);

    /**
     * Return the "Elroy" value of the ghost
     * @return the Elroy value
     */
    int getElroy();
}