package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

public class Blinky extends AbstractGhost{

    private final TilePosition scatterTilePosition = new TilePosition(0,25);

    public Blinky(Board board){
        super(board,GhostType.BLINKY);
        initializeBlinky();
    }

    @Override
    public TilePosition getTarget(){
        if(getGhostState() == GhostState.CHASE){
            return getBoard().getPacMan().getCurrentTile();
        }
        else{
            return scatterTilePosition;
        }
    }

    public void initializeBlinky(){
        setPosition(112,115);
        setDirection(Direction.LEFT);
        setSpeed(.94);
        setStopTime(0);
        setIntention(this.computeDirection());
        setGhostState(GhostState.SCATTER);
    }
}
