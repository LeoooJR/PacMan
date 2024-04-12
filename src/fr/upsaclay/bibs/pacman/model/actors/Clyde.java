package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

public class Clyde extends AbstractGhost{

    private final TilePosition scatterTilePosition = new TilePosition(34,0);

    public Clyde(Board board){
        super(board,GhostType.CLYDE);
        initializeClyde();
    }

    @Override
    public TilePosition getTarget(){
        if(getGhostPenState() == GhostPenState.IN){
            return new TilePosition(14,14);
        }
        else{
            if(getGhostState() == GhostState.CHASE){
                double distanceCol = getBoard().getPacMan().getCurrentTile().getCol() - getCurrentTile().getCol();
                double distanceRow = getBoard().getPacMan().getCurrentTile().getLine() - getCurrentTile().getLine();
                double distanceFromPacMan = Math.sqrt((distanceRow*distanceRow)+(distanceCol*distanceCol));
                if(distanceFromPacMan >= 8.0){
                    return getBoard().getPacMan().getCurrentTile();
                }
            }
            return scatterTilePosition;
        }
    }

    public void initializeClyde(){
        setPosition(128,139);
        setDirection(Direction.LEFT);
        setSpeed(.94);
        setStopTime(0);
        setIntention(this.computeDirection());
        setGhostPenState(GhostPenState.IN);
    }
}
