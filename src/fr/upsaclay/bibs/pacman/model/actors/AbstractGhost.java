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
        double min = Double.MAX_VALUE;
        int minId = -1; double x; double y;
        TilePosition nextTile = (getBoard().getMaze().getNeighbourTilePosition(getCurrentTile(), getDirection()));
        for(Direction dir: Direction.values()){
            if(dir != getDirection().reverse()){
                if(tryThisWay(dir,nextTile)){
                    System.out.println("Direction testée : " + dir);
                    /**x = ((getBoard().getMaze().getNeighbourTilePosition(nextTile,dir).getCol() * getBoard().getMaze().TILE_WIDTH) + getBoard().getMaze().TITLE_CENTER_X) - ((getTarget().getCol() * getBoard().getMaze().TILE_WIDTH) + getBoard().getMaze().TITLE_CENTER_X);
                    y = ((getBoard().getMaze().getNeighbourTilePosition(nextTile,dir).getLine() * getBoard().getMaze().TILE_HEIGHT) + getBoard().getMaze().TITLE_CENTER_Y) - ((getTarget().getLine() * getBoard().getMaze().TILE_HEIGHT) + getBoard().getMaze().TITLE_CENTER_Y);
                    distArray[dir.ordinal()] = Math.sqrt((x*x)+(y*y));**/
                    x = getBoard().getMaze().getNeighbourTilePosition(nextTile,dir).getCol() - getTarget().getCol();
                    System.out.println("X : " + x);
                    y = getBoard().getMaze().getNeighbourTilePosition(nextTile,dir).getLine() - getTarget().getLine();
                    System.out.println("Y : " + y);
                    distArray[dir.ordinal()] = Math.sqrt((x*x)+(y*y));
                    if(distArray[dir.ordinal()] < min){
                        minId = dir.ordinal();
                        min = distArray[dir.ordinal()];
                    }
                }
            }
        }
        System.out.println(Arrays.toString(distArray));
        return minId >= 0 ? Direction.values()[minId] : null;
    }
    @Override
    public void nextMove() {
        System.out.println("Blinky Move");
        if(isCentered()){
            System.out.println("Blinky est centré");
            goThisWay(getIntention());
            setIntention(computeDirection());
            System.out.println("Blinky intention :" + getIntention());
        }
        super.nextMove();
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
