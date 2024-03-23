package fr.upsaclay.bibs.pacman.model;

/**
 * A direction in the maze
 * The direction stores the differences to apply to the x / y  axes
 */
public enum Direction {
    UP(0,-1), LEFT(-1,0), DOWN(0,1), RIGHT(1,0);

    private final int dx;
    private final int dy;

    Direction(int dx,int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * The difference to apply in the x axis when going to that direction
     * @return an integer
     */
    public int getDx() {
        return dx;
    }

    /**
     * The difference to apply in the y axix when going to that direction
     * @return an integer
     */
    public int getDy() {
        return dy;
    }

    /**
     * Return the reverse direction
     * @return a new direction
     */
    public Direction reverse() {
        switch (this) {
            case UP: return DOWN;
            case LEFT: return RIGHT;
            case DOWN: return UP;
            case RIGHT: return LEFT;
        }
        return null;
    }
}
