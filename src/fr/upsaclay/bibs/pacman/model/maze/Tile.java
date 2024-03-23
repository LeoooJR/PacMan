package fr.upsaclay.bibs.pacman.model.maze;

/**
 * List of possible tiles in the maze
 * Each possible graphical representation or generating different behaviour gives a different type of tile
 * in order to reproduce the original PacMan design
 * For practical use, tile store if they represent a wall or not and we have implemented different useful methods
 */
public enum Tile {

    /** Empty **/
    EE(false),

    /** Walls **/

    /* simple walls (top, bottom, left, right) */
    WT(true), WB(true), WL(true), WR(true),
    /* corners */
    C1(true),  C2(true), C3(true),  C4(true),  C5(true), C6(true), C7(true), C8(true),
    /* double walls */
    DT(true), DB(true), DL(true), DR(true),
    /* double corners */
    D1(true), D2(true),  D3(true), D4(true), D5(true), D6(true), D7(true), D8(true), D9(true), DA(true),
    /* rectangular corners */
    R1(true), R2(true), R3(true), R4(true), R5(true), R6(true),

    /* ghost door (considered a wall for convenience) */
    GD(true),

    /** non wall tiles **/
    SD(false), /* small dot */
    ND(false), /* small dot with no upward turn for ghosts */
    BD(false), /* big dot */
    NT(false), /* empty with no upward turn for ghosts */
    SL(false); /* slow tile */


    private final boolean wall;

    Tile(boolean wall) {
        this.wall = wall;
    }

    /**
     * Return if the tile is wall or not
     * @return true if it is a wall
     */
    public boolean isWall() {
        return wall;
    }

    /**
     * Return if the tile contains a dot or not
     * @return true if it contains a dot
     */
    public boolean hasDot() {
        return (this == Tile.SD || this == Tile.BD || this == Tile.ND);
    }

    /**
     * Return if the tile contains a big dot or not
     * @return true if it contains a big dot
     */
    public boolean hasBigDot() {
        return this == Tile.BD;
    }

    /**
     * Return if ghost is allowed to up when on this tile
     * @return true if the ghost is allowed to go up
     */
    public boolean ghostCanGoUp() {
        switch (this) {
            case ND:
            case NT: return false;
            default: return true;
        }
    }

    /**
     * Return the equivalent tile where the dot has been removed
     * @return a new tile
     */
    public Tile clearDot() {
        switch (this) {
            case SD:
            case BD: return EE;
            case ND: return NT;
            default: return this;
        }
    }
}
