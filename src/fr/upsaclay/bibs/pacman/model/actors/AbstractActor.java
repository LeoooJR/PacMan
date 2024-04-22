package fr.upsaclay.bibs.pacman.model.actors;

import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

import java.util.Objects;

public class AbstractActor implements Actor {

    private ActorType type;

    private Board board;

    private double x;

    private double y;

    private Direction direction;

    private Direction intention;

    private double movementSpeed = 1;

    private int stopTime;

    AbstractActor(ActorType type, Board board) {
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
        return board.getMaze().getTilePosition(getX(), getY());
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
    public Direction getIntention() {
        return intention;
    }

    @Override
    public void setIntention(Direction direction) {
        this.intention = direction;
    }

    @Override
    public boolean isBlocked() {
        Tile neighbourTile = getBoard().getMaze().getNeighbourTile(this.getCurrentTile(), direction);

        // If intention is not null, check the tile in the intended direction
        if (intention != null && neighbourTile.isWall()) {
            Tile neighbourIntent = getBoard().getMaze().getNeighbourTile(this.getCurrentTile(), intention);
            if (!neighbourIntent.isWall()) {
                direction = intention;
            }
            return neighbourTile.isWall() && neighbourIntent.isWall();
        }

    return neighbourTile.isWall();
    }
//Weird usage to note for ghosts
    public boolean isBlocked(TilePosition tile) {
        return board.getMaze().getNeighbourTile(tile, direction).isWall();
    }


    public boolean isCentered() {
        TilePosition currentTile = getCurrentTile();
        int centerX = currentTile.getCol() * Maze.TILE_WIDTH + Maze.TITLE_CENTER_X;
        int centerY = currentTile.getLine() * Maze.TILE_HEIGHT + Maze.TITLE_CENTER_Y;
        int posX = getX();
        int posY = getY();
        return posX == centerX && posY == centerY;
    }

    public boolean isBeforeCenter() {
        switch (direction) {
            case UP:
                return (y > ((getCurrentTile().getLine() * Maze.TILE_HEIGHT) + Maze.TITLE_CENTER_Y));
            case DOWN:
                return (y < ((getCurrentTile().getLine() * Maze.TILE_HEIGHT) + Maze.TITLE_CENTER_Y));
            case RIGHT:
                return (x < ((getCurrentTile().getCol() * Maze.TILE_WIDTH) + Maze.TITLE_CENTER_X));
            case LEFT:
                return (x > ((getCurrentTile().getCol() * Maze.TILE_WIDTH) + Maze.TITLE_CENTER_X));
        }
        return false;
    }

    public boolean tryThisWay(Direction direction, TilePosition tile) {
        Direction previousDirection = getDirection();
        goThisWay(direction);
        boolean wayBool = !isBlocked(tile);
        goThisWay(previousDirection);
        return wayBool;
    }

    public void goThisWay(Direction intention) {
        direction = intention;
    }

    @Override
    public void nextMove() {
        double newX = x + (direction.getDx() * movementSpeed);
        double newY = y + (direction.getDy() * movementSpeed);

        if (newX >= board.getMaze().getPixelWidth()) {
            newX = 0;
        } else if (newX < 0) {
            newX = board.getMaze().getPixelWidth() - 1;
        }

        if (newY >= board.getMaze().getPixelHeight()) {
            newY = 0;
        } else if (newY < 0) {
            newY = board.getMaze().getPixelHeight() - 1;
        }

        setPosition(newX, newY);
    }

    @Override
    public void nextFrame() {
        if (stopTime == 0) {
            nextMove();
        } else if (stopTime > 0) {
            stopTime--;
        }
    }

    //Step 2

    @Override
    public double getSpeed() {
        return movementSpeed;
    }

    @Override
    public void setSpeed(double speed) {
        movementSpeed = speed;
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
