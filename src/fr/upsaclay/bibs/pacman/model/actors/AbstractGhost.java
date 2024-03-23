package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.Counter;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;
import java.lang.Math;
import java.sql.Array;
import java.util.Arrays;

public class AbstractGhost extends AbstractActor implements Ghost{

    private GhostType ghostType;

    private TilePosition target;

    public AbstractGhost(Board board,GhostType type){
        super(ActorType.GHOST, board);
        ghostType = type;

    }
    @Override
    public GhostType getGhostType() {
        return ghostType;
    }

    @Override
    public TilePosition getTarget() {
        return null;
    }

    public Direction computeDirection(){
        double[] distArray = new double[4];
        int minId = 0; double min = Integer.MAX_VALUE;
        for(Direction dir: Direction.values()){
            if(dir != getDirection().reverse()){
                TilePosition nextTile = (getBoard().getMaze().getNeighbourTilePosition(getCurrentTile(), getDirection()));
                if(tryThisWay(dir,nextTile)){
                    distArray[dir.ordinal()] = Math.sqrt(((nextTile.getCol() - getTarget().getCol())*(nextTile.getCol() - getTarget().getCol())) + ((nextTile.getLine() - getTarget().getLine())*(nextTile.getLine() - getTarget().getLine())));
                    if(distArray[dir.ordinal()] < min){
                        minId = dir.ordinal();
                    }
                }
            }
        }
        return Direction.values()[minId];
    }
    @Override
    public void nextMove() {
        if(isCentered()){
            goThisWay(getIntention());
            computeDirection();
        }
    }

    //Step 3

    @Override
    public void setGhostState(GhostState state) {

    }

    @Override
    public void changeGhostState(GhostState state) {

    }

    @Override
    public GhostState getGhostState() {
        return null;
    }

    @Override
    public void setGhostPenState(GhostPenState state) {

    }

    @Override
    public GhostPenState getGhostPenState() {
        return null;
    }

    @Override
    public void setOutOfPenDirection(Direction dir) {

    }

    @Override
    public Direction getOutOfPenDirection() {
        return null;
    }

    @Override
    public void reverseDirectionIntention() {

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
    public void setElroy(int elroy) {

    }

    @Override
    public int getElroy() {
        return 0;
    }
}
