package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

public class Pinky extends AbstractGhost{

    private final TilePosition scatterTilePosition = new TilePosition(0,2);

    public Pinky(Board board){
        super(board,GhostType.PINKY);
        initializePinky();
    }

    @Override
    public TilePosition getTarget(){
        if(getGhostPenState() == GhostPenState.IN){
            return new TilePosition(14,14);
        }
        else{
            if(getGhostState() == GhostState.CHASE){
                int colPacMan = getBoard().getPacMan().getCurrentTile().getCol();
                int rowPacMan = getBoard().getPacMan().getCurrentTile().getLine();
                switch (getBoard().getPacMan().getDirection()){
                    case LEFT:
                        return new TilePosition(rowPacMan,colPacMan - 4);
                    case RIGHT:
                        return new TilePosition(rowPacMan,colPacMan + 4);
                    case DOWN:
                        return new TilePosition(rowPacMan + 4, colPacMan);
                    case UP:
                        return new TilePosition(rowPacMan - 4, colPacMan);
                }
                return new TilePosition(rowPacMan,colPacMan);

            }
            else{
                return scatterTilePosition;
            }
        }
    }

    public void initializePinky(){
        setPosition(112,139);
        setDirection(Direction.LEFT);
        setSpeed(.94);
        setStopTime(0);
        setIntention(this.computeDirection());
        setGhostPenState(GhostPenState.IN);
    }
}
