package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

public class Blinky extends AbstractGhost{

    public Blinky(Board board){
        super(board,GhostType.BLINKY);
        setPosition(112,115);
        setDirection(Direction.LEFT);
        setSpeed(.94);
    }

    @Override
    public TilePosition getTarget(){
        return getBoard().getPacMan().getCurrentTile();
    }
}
