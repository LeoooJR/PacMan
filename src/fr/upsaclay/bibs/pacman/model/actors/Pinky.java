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
public TilePosition getTarget() {
    if (getGhostPenState() == GhostPenState.IN) {
        return new TilePosition(14, 14);
    }

    if(getGhostState() == GhostState.FRIGHTENED || getGhostState() == GhostState.FRIGHTENED_END){
        return null;
    }

    if (getGhostState() == GhostState.CHASE) {
        TilePosition pacManPosition = getBoard().getPacMan().getCurrentTile();
        int colPacMan = pacManPosition.getCol();
        int rowPacMan = pacManPosition.getLine();
        Direction pacManDirection = getBoard().getPacMan().getDirection();

        switch (pacManDirection) {
            case LEFT:
                return new TilePosition(rowPacMan, colPacMan - 4);
            case RIGHT:
                return new TilePosition(rowPacMan, colPacMan + 4);
            case DOWN:
                return new TilePosition(rowPacMan + 4, colPacMan);
            case UP:
                return new TilePosition(rowPacMan - 4, colPacMan - 4);
            default:
                return new TilePosition(rowPacMan, colPacMan);
        }
    }

    return scatterTilePosition;
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
