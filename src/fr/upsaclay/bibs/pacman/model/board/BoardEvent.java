package fr.upsaclay.bibs.pacman.model.board;

import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

/**
 * Represent an event happening on the board
 *
 * (Not used before step 4)
 */
public class BoardEvent {

    private final BoardEventType type;
    private final int value;
    private final TilePosition pos;

    public BoardEvent(BoardEventType type, TilePosition pos, int value) {
        this.type = type;
        this.pos = pos;
        this.value = value;
    }

    /**
     * Return the type of event
     * @return a board event type
     */
    public BoardEventType getType() {
        return type;
    }

    /**
     * Return the value of the event
     * The value has different meaning depending on event type, it might not always be relevant
     * It can be the number points gained, or other value associated with the event
     * @return a number
     */
    public int getValue() {
        return value;
    }

    /**
     * Return the position on board where the event happened
     * @return a tile position
     */
    public TilePosition getPos() {
        return pos;
    }
}
