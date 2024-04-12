package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

public class Inky extends AbstractGhost{

    private final TilePosition scatterTilePosition = new TilePosition(34,27);

    public Inky(Board board) {
        super(board, GhostType.INKY);
        initializeInky();
    }

    @Override
    public TilePosition getTarget(){
        if(getGhostPenState() == GhostPenState.IN){
            return new TilePosition(14,14);
        }
        else{
            if(getGhostState() == GhostState.CHASE){
                TilePosition tileOffset = new TilePosition(0,0);
                int colPacMan = getBoard().getPacMan().getCurrentTile().getCol();
                int rowPacMan = getBoard().getPacMan().getCurrentTile().getLine();
                switch (getBoard().getPacMan().getDirection()){
                    case LEFT:
                        tileOffset = new TilePosition(rowPacMan,colPacMan - 2);
                        break;
                    case RIGHT:
                        tileOffset = new TilePosition(rowPacMan,colPacMan + 2);
                        break;
                    case DOWN:
                        tileOffset = new TilePosition(rowPacMan + 2, colPacMan);
                        break;
                    case UP:
                        tileOffset = new TilePosition(rowPacMan - 2, colPacMan);
                        break;
                }
                int dX; int dY;
                dX = tileOffset.getLine()- getBoard().getGhost(GhostType.BLINKY).getCurrentTile().getLine();
                dY = tileOffset.getCol() - getBoard().getGhost(GhostType.BLINKY).getCurrentTile().getCol();
                return new TilePosition(tileOffset.getLine() + dX, tileOffset.getCol() + dY);
            }
            else{
                return scatterTilePosition;
            }
        }
    }

    public void initializeInky(){
        setPosition(96,139);
        setDirection(Direction.LEFT);
        setSpeed(.94);
        setStopTime(0);
        setIntention(this.computeDirection());
        setGhostPenState(GhostPenState.IN);
    }
}
