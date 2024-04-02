package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

import javax.naming.directory.DirContext;

public class PacMan extends AbstractActor{

    private final int stopTimePacMan = 1;

    private int lifePoint = 3;

    public PacMan(Board board){
        super(ActorType.PACMAN, board);
        initializePacMan();
    }

    @Override
    public void start() {
          setIntention(null);
    }

    public boolean tryThisWay(){
        if(getIntention() != null) {
            Direction previousDirection = getDirection();
            goThisWay(getIntention());
            if(isBlocked()){
                System.out.println("Bloqué");
                goThisWay(previousDirection);
                return false;
            }
            setIntention(null);
        }
        else {
            if(isBlocked()){
                return false;
            }
        }
        return true;
    }

    @Override
    public void nextMove(){
        System.out.println("PacMan Move");
        if(getIntention() == getDirection().reverse()) {
            System.out.println("reverse");
            goThisWay(getIntention());
            setIntention(null);
        }
        if((isCentered() ? tryThisWay() : true)){
            super.nextMove();
        }
        if(isCentered()){
            tryThisWay();
        }
    }
    /**
    @Override
    public void nextFrame() {
        nextMove();
    }
    **/

    public void reseatStopTime(){
        super.setStopTime(stopTimePacMan);
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public void initializePacMan(){
        if(getBoard().getGameType() == GameType.CLASSIC) {
            setPosition(112,211);
        }
        else {
            setPosition(35,75);
        }
        setDirection(Direction.LEFT);
        setSpeed(1);
        setStopTime(0);
    }
}
