package fr.upsaclay.bibs.pacman.model.actors;

/**
 * An interface to represent bonuses on the board
 * Bonus are considered Actors even though they don't move
 * (not used before Step 4 of the project)
 */
public interface Bonus extends Actor {

    /**
     * Return the type of bonus it is
     * @return a bonus type
     */
    BonusType getBonusType();

    /**
     * Return whether this bonus is still active
     * @return true if the bonus is active
     */
    boolean isActive();
}