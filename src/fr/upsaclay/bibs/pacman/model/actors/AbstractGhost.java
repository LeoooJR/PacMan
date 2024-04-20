package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.Counter;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

import java.lang.Math;
import java.sql.Array;
import java.util.Arrays;

public class AbstractGhost extends AbstractActor implements Ghost {

    private GhostType ghostType;

    private GhostState ghostState;

    private TilePosition target;

    private GhostPenState penState;

    public AbstractGhost(Board board, GhostType type) {
        super(ActorType.GHOST, board);
        ghostType = type;
//        TODO: I set the ghost state to scatter for all the ghosts at the start
        ghostState = GhostState.SCATTER;
    }

    @Override
    public GhostType getGhostType() {
        return ghostType;
    }

    @Override
    public TilePosition getTarget() {
        return target;
    }

@Override
public boolean isBlocked(TilePosition tile) {
    if (penState == GhostPenState.IN && getBoard().getMaze().getNeighbourTile(tile, getDirection()) == Tile.GD) {
        return false;
    }
    return super.isBlocked(tile);
}

    public Direction computeDirection() {
        double minDistance = Double.MAX_VALUE;
        Direction selectedDirection = null;
//        System.out.println("Ghost is "+ghostType);
        TilePosition currentTile = getCurrentTile();
//        System.out.println("Current Tile : " + currentTile);
        Direction currentDirection = getDirection();
//        System.out.println("Current Direction : " + currentDirection);
        TilePosition nextTile = getBoard().getMaze().getNeighbourTilePosition(currentTile, currentDirection);

        for (Direction dir : Direction.values()) {
            if (dir != currentDirection.reverse()) {
                if (tryThisWay(dir, nextTile)) {
                    TilePosition neighbourTile = getBoard().getMaze().getNeighbourTilePosition(nextTile, dir);
                    double xDistance = neighbourTile.getCol() - getTarget().getCol();
                    double yDistance = neighbourTile.getLine() - getTarget().getLine();
                    double distance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

                    if (distance < minDistance) {
                        minDistance = distance;
                        selectedDirection = dir;
                    }
                }
            }
        }
        return selectedDirection;
    }


    @Override
    public void nextMove() {
//        System.out.println("Blinky Move");
        if (isCentered()) {
//            System.out.println("Blinky est centré");
            goThisWay(getIntention());
            if (getGhostState() != GhostState.FRIGHTENED) {
                setIntention(computeDirection());
            } else {
                int resRandom = (int) (Math.random() * (3 + 1));
//                System.out.println("Resultat random : " + resRandom);
                if (tryThisWay(Direction.values()[resRandom], getCurrentTile())) {
                    setIntention(Direction.values()[resRandom]);
                } else {
                    boolean randWay = false;
                    for (Direction direction : Direction.values()) {
                        if (direction != Direction.values()[resRandom] && !randWay) {
                            if (tryThisWay(direction, getCurrentTile())) {
                                setIntention(direction);
                                randWay = true;
                            }
                        }
                    }
                }
            }
//            System.out.println("Blinky intention :" + getIntention());
        }
        super.nextMove();
        if (penState == GhostPenState.IN) {
            if (getCurrentTile().getLine() <= 14 && getCurrentTile().getCol() >= 13) {
                penState = GhostPenState.OUT;
                ghostState = GhostState.SCATTER;
            }
        }
    }

    //Step 3

    @Override
    public void changeGhostState(GhostState state) {
        // Only reverse direction if the current state is not frightened
        GhostState currentState = getGhostState();
        // Something weird happens when you try to simplify this into SWITCH

        if (currentState == GhostState.CHASE && state == GhostState.SCATTER){
            reverseDirectionIntention();
        }
        if(currentState == GhostState.SCATTER && state == GhostState.CHASE){
            reverseDirectionIntention();
        }
        if (currentState == GhostState.CHASE && state == GhostState.FRIGHTENED){
            reverseDirectionIntention();
        }
        if (currentState == GhostState.SCATTER && state == GhostState.FRIGHTENED){
            reverseDirectionIntention();
        }

        setGhostState(state);
    }

    @Override
    public GhostState getGhostState() {
        return ghostState;
    }

    @Override
    public void setGhostState(GhostState state) {
        ghostState = state;
    }

    @Override
    public GhostPenState getGhostPenState() {
        return penState;
    }

    @Override
    public void setGhostPenState(GhostPenState state) {
        penState = state;
    }

    @Override
    public Direction getOutOfPenDirection() {
        return null;
    }

    @Override
    public void setOutOfPenDirection(Direction dir) {

    }

    @Override
    public void reverseDirectionIntention() {
        setIntention(getDirection().reverse());
    }

    @Override
    public boolean hasDotCounter() {
        return false;
    }

    @Override
    public Counter getDotCounter() {
        return null;
    }

    @Override
    public int getElroy() {
        return 0;
    }

    @Override
    public void setElroy(int elroy) {

    }
}
