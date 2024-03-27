package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

public class AbstractActor implements Actor{

    private ActorType type;

    private Board board;

    private double x;

    private double y;

    private Direction direction;

    private Direction intention;

    private double movementSpeed = 1;

    private int stopTime;

    AbstractActor(ActorType type, Board board){
        this.type = type;
        this.board = board;
    }

    @Override
    public ActorType getType() {
        return type;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public int getX() {
        return (int) x;
    }

    @Override
    public int getY() {
        return (int) y;
    }

    @Override
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public TilePosition getCurrentTile() {
        return board.getMaze().getTilePosition(getX(),getY());
    }

    @Override
    public void start() {
        intention = null;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void setIntention(Direction direction) {
        this.intention = direction;
    }

    @Override
    public Direction getIntention() {
        return intention;
    }

    @Override
    public boolean isBlocked() {
        return getBoard().getMaze().getNeighbourTile(this.getCurrentTile(),direction).isWall();
        /**if(x + direction.getDx() >= getBoard().getMaze().getPixelWidth())
            return board.getMaze().getTile(getBoard().getMaze().getTilePosition(0,y + direction.getDy())).isWall();
        else if(x + direction.getDx() < 0){
            return board.getMaze().getTile(getBoard().getMaze().getTilePosition(board.getMaze().getPixelWidth() - 1,y + direction.getDy())).isWall();
        }
        else if(y + direction.getDy() >= getBoard().getMaze().getPixelHeight()){
            return board.getMaze().getTile(getBoard().getMaze().getTilePosition(x + direction.getDx(),0)).isWall();
        }
        else if(y + direction.getDy() < 0){
            return board.getMaze().getTile(getBoard().getMaze().getTilePosition(x + direction.getDx(),board.getMaze().getPixelHeight() - 1)).isWall();
        }
        else {
            System.out.println(board.getMaze().getTile(getBoard().getMaze().getTilePosition(x + direction.getDx(),y + direction.getDy())));
            return board.getMaze().getTile(getBoard().getMaze().getTilePosition(x + direction.getDx(),y + direction.getDy())).isWall();
        }**/
    }

    public boolean isBlocked(TilePosition tile){
        return board.getMaze().getNeighbourTile(tile,direction).isWall();
    }

    public boolean isCentered() {
        return (getX() == ((getCurrentTile().getCol() * Maze.TILE_WIDTH) + Maze.TITLE_CENTER_X) && getY() == ((getCurrentTile().getLine() * Maze.TILE_HEIGHT) + Maze.TITLE_CENTER_Y));
    }

    public boolean isBeforeCenter(){
        switch(direction){
            case UP: return (y > ((getCurrentTile().getLine() * Maze.TILE_HEIGHT) + Maze.TITLE_CENTER_Y));
            case DOWN: return (y < ((getCurrentTile().getLine() * Maze.TILE_HEIGHT) + Maze.TITLE_CENTER_Y));
            case RIGHT: return  (x < ((getCurrentTile().getCol() * Maze.TILE_WIDTH) + Maze.TITLE_CENTER_X));
            case LEFT: return (x > ((getCurrentTile().getCol() * Maze.TILE_WIDTH) + Maze.TITLE_CENTER_X));
        }
        return false;
    }
    public boolean tryThisWay(Direction direction, TilePosition tile){
        Direction previousDirection = getDirection();
        goThisWay(direction);
        boolean wayBool = !isBlocked(tile);
        goThisWay(previousDirection);
        return wayBool;
    }
    public void goThisWay(Direction intention){
        direction = intention;
    }
    @Override
    public void nextMove() {
        if(x + (direction.getDx()*movementSpeed) >= board.getMaze().getPixelWidth())
            setPosition(0,(y + (direction.getDy()*movementSpeed)));
        else if(x + (direction.getDx()*movementSpeed) < 0){
            setPosition(board.getMaze().getPixelWidth()-1,(y + (direction.getDy()*movementSpeed)));
        }
        else if((y + (direction.getDy()*movementSpeed)) >= board.getMaze().getPixelHeight()){
            setPosition(x + (direction.getDx()*movementSpeed), 0);
        }
        else if((y + (direction.getDy()*movementSpeed)) < 0){
            setPosition(x + (direction.getDx()*movementSpeed), board.getMaze().getPixelHeight() - 1);
        }
        else {
            setPosition(x + (direction.getDx()*movementSpeed), (y + (direction.getDy()*movementSpeed)));
        }
        System.out.println("X " + x + " Y " + y);
        System.out.println(getCurrentTile());
    }

    @Override
    public void nextFrame() {
        if(stopTime == 0){
            nextMove();
        }
        else if(stopTime > 0){
            stopTime--;
        }
    }

    //Step 2

    @Override
    public void setSpeed(double speed) {
        movementSpeed = speed;
    }

    @Override
    public double getSpeed() {
        return movementSpeed;
    }

    @Override
    public double getRealX() {
        return x;
    }

    @Override
    public double getRealY() {
        return y;
    }

    @Override
    public void setStopTime(int nbFrames) {
        stopTime = nbFrames;
    }
}
