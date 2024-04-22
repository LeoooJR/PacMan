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
//    Check if its wall
    if (getBoard().getMaze().getTile(tile).isWall()) {
        return true;
    }
    return false;
}

    public boolean tryThisWay(Direction dir, TilePosition tile) {
        if (penState == GhostPenState.IN && getBoard().getMaze().getNeighbourTile(tile, dir) == Tile.GD) {
            return true;
        }
        return !getBoard().getMaze().getTile(tile).isWall();
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
                nextTile = getBoard().getMaze().getNeighbourTilePosition(currentTile, dir);
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



    public void ComputeIntentionBlinky(){
        if (getGhostState() == GhostState.CHASE) {
            // Set the target as PacMan's current tile
            target = getBoard().getPacMan().getCurrentTile();

            // Calculate the best direction to move towards PacMan
            setIntention(computeDirectionTowardsTarget());
        }
    }

    private Direction computeDirectionTowardsTarget() {
        TilePosition currentTile = getCurrentTile();
        double minDistance = Double.MAX_VALUE;
        Direction selectedDirection = null;
        Direction currentDirection = getDirection();

        // Check all four directions for the shortest path to the target
        for (Direction dir : Direction.values()) {
            if (dir != currentDirection.reverse()) { // Prevent the ghost from reversing
                TilePosition nextTile = getBoard().getMaze().getNeighbourTilePosition(currentTile, dir);
                if (!isBlocked(nextTile)) {
                    double xDistance = target.getCol() - nextTile.getCol();
                    double yDistance = target.getLine() - nextTile.getLine();
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
        if (isCentered()) {
            System.out.println("Ghost is centered");
            System.out.println(STR."Ghost is in  : \{getCurrentTile()}");
            TilePosition currentTile = getCurrentTile();
            if (getGhostState() == GhostState.FRIGHTENED || getGhostState() == GhostState.FRIGHTENED_END) {
                setRandomIntention();
            } else if (getGhostState() == GhostState.CHASE) {
                ComputeIntentionBlinky(); // Update Blinky's intention based on the CHASE logic
            } else {
                setIntention(computeDirection());
            }

                goThisWay(getIntention());

//            goThisWay(getIntention());

        }
        super.nextMove();
        System.out.println("Intention : " + getIntention());
        System.out.println("Direction : " + getDirection());


        checkPenState();
    }
    private void checkPenState() {
        if (penState == GhostPenState.IN && getCurrentTile().getLine() <= 14 && getCurrentTile().getCol() >= 13) {
            penState = GhostPenState.OUT;
            ghostState = GhostState.SCATTER;
        }
    }

private void setRandomIntention() {
    int resRandom = (int) (Math.random() * 4);
    if (tryThisWay(Direction.values()[resRandom], getCurrentTile())) {
        setIntention(Direction.values()[resRandom]);
    } else {
        setFirstValidDirection(resRandom);
    }
}

private void setFirstValidDirection(int excludedDirection) {
    for (Direction direction : Direction.values()) {
        if (direction.ordinal() != excludedDirection && tryThisWay(direction, getCurrentTile())) {
            setIntention(direction);
            break;
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
