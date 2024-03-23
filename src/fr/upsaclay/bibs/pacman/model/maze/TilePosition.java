package fr.upsaclay.bibs.pacman.model.maze;

import java.util.Objects;

/**
 * Represent a tile position in the maze
 */
public class TilePosition {

    /**
     * The line in the maze
     */
    private final int line;

    /**
     * The col in the maze
     */
    private final int col;

    /**
     * Create a tile position
     * @param line, a non negative integer
     * @param col, a non negative integer
     */
    public TilePosition(int line, int col) {
        this.line = line;
        this.col = col;
    }

    /**
     * Return the line
     * @return an non negative integer
     */
    public int getLine() {
        return line;
    }

    /**
     * Return the col
     * @return a non negative integer
     */
    public int getCol() {
        return col;
    }

    /**
     * An equal method for tile positions
     * @param o, another object
     * @return true if the objects are equal (same line and col)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TilePosition that = (TilePosition) o;
        return line == that.line && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, col);
    }

    @Override
    public String toString() {
        return "TilePosition{" +
                "line=" + line +
                ", col=" + col +
                '}';
    }
}
