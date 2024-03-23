package fr.upsaclay.bibs.pacman.test.step4;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.Actor;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
public class PacManClassicTurnTest {

    /**
     * We test an actual turn on pacman
     * Initially pacman goes some pixels left
     * We then ask pacman to turn around
     * Then we ask to go up and check that the intention is kept until reaching the tile center.
     * When reaching the tile center, pacman's direction changes to up
     * @throws PacManException
     */
    @Test
    public void testSetIntentionTurnUp() throws PacManException, FileNotFoundException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        Actor pacman = testBoard.getPacMan();
        pacman.setSpeed(1);
        Maze maze = testBoard.getMaze();
        int x = pacman.getX(); // x position at start
        int y = pacman.getY();
        pacman.nextMove();;
        // PacMan direction is to the left
        assertEquals(pacman.getDirection(), Direction.LEFT);
        // It has moved one pixel to the left
        assertEquals(pacman.getX(), x-1);
        assertEquals(pacman.getY(), y);
        // We move 4 more pixels to the left to go to the next tile
        pacman.nextMove();;
        pacman.nextMove();;
        pacman.nextMove();;
        pacman.nextMove();;
        // PacMan direction is to the left
        assertEquals(pacman.getDirection(), Direction.LEFT);
        // It has moved 4 pixels to the left
        assertEquals(pacman.getX(), x-5);
        assertEquals(pacman.getY(), y);
        // We turn around and go right
        pacman.setIntention(Direction.RIGHT);
        pacman.nextMove();;
        // PacMan direction is to the Right
        assertEquals(pacman.getDirection(), Direction.RIGHT);
        // It has moved one pixel to the right
        assertEquals(pacman.getX(), x-4);
        assertEquals(pacman.getY(), y);
        // We give an intention to go up
        // The intention should be kept until we reach the next tile center
        // (the original x position)
        pacman.setIntention(Direction.UP);
        pacman.nextMove();;
        assertEquals(pacman.getIntention(), Direction.UP);
        // PacMan direction is to the Right
        assertEquals(pacman.getDirection(), Direction.RIGHT);
        // It has moved one pixel to the right
        assertEquals(pacman.getX(), x-3);
        assertEquals(pacman.getY(), y);
        pacman.nextMove();;
        assertEquals(pacman.getIntention(), Direction.UP);
        // PacMan direction is to the Right
        assertEquals(pacman.getDirection(), Direction.RIGHT);
        // It has moved one pixel to the right
        assertEquals(pacman.getX(), x-2);
        assertEquals(pacman.getY(), y);
        pacman.nextMove();;
        assertEquals(pacman.getIntention(), Direction.UP);
        // PacMan direction is to the Right
        assertEquals(pacman.getDirection(), Direction.RIGHT);
        // It has moved one pixel to the right
        assertEquals(pacman.getX(), x-1);
        assertEquals(pacman.getY(), y);
        // At the next move, pacman reaches the tile center and change its direction
        pacman.nextMove();;
        // The intention should be empty
        assertNull(pacman.getIntention());
        // PacMan direction is Up
        assertEquals(pacman.getDirection(), Direction.UP);
        // It has moved one pixel to the right (previous direction)
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y);
        // Next move : pacman moves up
        pacman.nextMove();;
        // The intention should be empty
        assertNull(pacman.getIntention());
        // PacMan direction is Up
        assertEquals(pacman.getDirection(), Direction.UP);
        // It has moved one pixel up
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-1);
    }

    /**
     * We test an actual turn on pacman
     * Initially pacman goes some pixels left
     * We then ask pacman to turn around
     * Then we ask to go down and check that the intention is kept until reaching the tile center.
     * When reaching the tile center, pacman's direction changes to down
     * @throws PacManException
     */
    @Test
    public void testSetIntentionTurnDown() throws PacManException, FileNotFoundException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        Actor pacman = testBoard.getPacMan();
        pacman.setSpeed(1);
        Maze maze = testBoard.getMaze();
        int x = pacman.getX(); // x position at start
        int y = pacman.getY();
        pacman.nextMove();;
        // PacMan direction is to the left
        assertEquals(pacman.getDirection(), Direction.LEFT);
        // It has moved one pixel to the left
        assertEquals(pacman.getX(), x-1);
        assertEquals(pacman.getY(), y);
        // We move 4 more pixels to the left to go to the next tile
        pacman.nextMove();;
        pacman.nextMove();;
        pacman.nextMove();;
        pacman.nextMove();;
        // PacMan direction is to the left
        assertEquals(pacman.getDirection(), Direction.LEFT);
        // It has moved 4 pixels to the left
        assertEquals(pacman.getX(), x-5);
        assertEquals(pacman.getY(), y);
        // We turn around and go right
        pacman.setIntention(Direction.RIGHT);
        pacman.nextMove();;
        // PacMan direction is to the Right
        assertEquals(pacman.getDirection(), Direction.RIGHT);
        // It has moved one pixel to the right
        assertEquals(pacman.getX(), x-4);
        assertEquals(pacman.getY(), y);
        // We give an intention to go down
        // The intention should be kept until we reach the next tile center
        // (the original x position)
        pacman.setIntention(Direction.DOWN);
        pacman.nextMove();;
        assertEquals(pacman.getIntention(), Direction.DOWN);
        // PacMan direction is to the Right
        assertEquals(pacman.getDirection(), Direction.RIGHT);
        // It has moved one pixel to the right
        assertEquals(pacman.getX(), x-3);
        assertEquals(pacman.getY(), y);
        pacman.nextMove();;
        assertEquals(pacman.getIntention(), Direction.DOWN);
        // PacMan direction is to the Right
        assertEquals(pacman.getDirection(), Direction.RIGHT);
        // It has moved one pixel to the right
        assertEquals(pacman.getX(), x-2);
        assertEquals(pacman.getY(), y);
        pacman.nextMove();;
        assertEquals(pacman.getIntention(), Direction.DOWN);
        // PacMan direction is to the Right
        assertEquals(pacman.getDirection(), Direction.RIGHT);
        // It has moved one pixel to the right
        assertEquals(pacman.getX(), x-1);
        assertEquals(pacman.getY(), y);
        // At the next move, pacman reaches the tile center and change its direction
        pacman.nextMove();;
        // The intention should be empty
        assertNull(pacman.getIntention());
        // PacMan direction is down
        assertEquals(pacman.getDirection(), Direction.DOWN);
        // It has moved one pixel to the right (previous direction)
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y);
        // Next move : pacman moves down
        pacman.nextMove();;
        // The intention should be empty
        assertNull(pacman.getIntention());
        // PacMan direction is down
        assertEquals(pacman.getDirection(), Direction.DOWN);
        // It has moved one pixel down
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y+1);
    }

    /**
     * We test an actual turn on pacman
     * Initially pacman goes some pixels up
     * We then ask pacman to turn around
     * Then we ask to go left and check that the intention is kept until reaching the tile center.
     * When reaching the tile center, pacman's direction changes to left
     * @throws PacManException
     */
    @Test
    public void testSetIntentionTurnLeft() throws PacManException, FileNotFoundException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        Actor pacman = testBoard.getPacMan();
        pacman.setDirection(Direction.UP); // We set the direction up before starting the actor
        pacman.setSpeed(1);
        testBoard.startActors();

        Maze maze = testBoard.getMaze();
        int x = pacman.getX(); // x position at start
        int y = pacman.getY();
        pacman.nextMove();;
        // PacMan direction is up
        assertEquals(pacman.getDirection(), Direction.UP);
        // It has moved one pixel up
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-1);
        // We move 4 more pixels up to go to the next tile
        pacman.nextMove();;
        pacman.nextMove();;
        pacman.nextMove();;
        pacman.nextMove();;
        // PacMan direction is ip
        assertEquals(pacman.getDirection(), Direction.UP);
        // It has moved 4 pixels up
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-5);
        // We turn around and go down
        pacman.setIntention(Direction.DOWN);
        pacman.nextMove();;
        // PacMan direction is down
        assertEquals(pacman.getDirection(), Direction.DOWN);
        // It has moved one pixel down
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-4);
        // We give an intention to go left
        // The intention should be kept until we reach the next tile center
        // (the original y position)
        pacman.setIntention(Direction.LEFT);
        pacman.nextMove();;
        assertEquals(pacman.getIntention(), Direction.LEFT);
        // PacMan direction is down
        assertEquals(pacman.getDirection(), Direction.DOWN);
        // It has moved one pixel down
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-3);
        pacman.nextMove();;
        assertEquals(pacman.getIntention(), Direction.LEFT);
        // PacMan direction is down
        assertEquals(pacman.getDirection(), Direction.DOWN);
        // It has moved one pixel down
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-2);
        pacman.nextMove();;
        assertEquals(pacman.getIntention(), Direction.LEFT);
        // PacMan direction is down
        assertEquals(pacman.getDirection(), Direction.DOWN);
        // It has moved one pixel down
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-1);
        // At the next move, pacman reaches the tile center and change its direction
        pacman.nextMove();;
        // The intention should be empty
        assertNull(pacman.getIntention());
        // PacMan direction is left
        assertEquals(pacman.getDirection(), Direction.LEFT);
        // It has moved one pixel down (previous direction)
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y);
        // Next move : pacman moves left
        pacman.nextMove();;
        // The intention should be empty
        assertNull(pacman.getIntention());
        // PacMan direction is left
        assertEquals(pacman.getDirection(), Direction.LEFT);
        // It has moved one pixel left
        assertEquals(pacman.getX(), x - 1);
        assertEquals(pacman.getY(), y);
    }

    /**
     * We test an actual turn on pacman
     * Initially pacman goes some pixels up
     * We then ask pacman to turn around
     * Then we ask to go right and check that the intention is kept until reaching the tile center.
     * When reaching the tile center, pacman's direction changes to left
     * @throws PacManException
     */
    @Test
    public void testSetIntentionTurnRight() throws PacManException, FileNotFoundException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        Actor pacman = testBoard.getPacMan();
        pacman.setSpeed(1);
        pacman.setDirection(Direction.UP); // We set the direction up before starting the actor
        testBoard.startActors();

        Maze maze = testBoard.getMaze();
        int x = pacman.getX(); // x position at start
        int y = pacman.getY();
        pacman.nextMove();;
        // PacMan direction is up
        assertEquals(pacman.getDirection(), Direction.UP);
        // It has moved one pixel up
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-1);
        // We move 4 more pixels up to go to the next tile
        pacman.nextMove();;
        pacman.nextMove();;
        pacman.nextMove();;
        pacman.nextMove();;
        // PacMan direction is ip
        assertEquals(pacman.getDirection(), Direction.UP);
        // It has moved 4 pixels up
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-5);
        // We turn around and go down
        pacman.setIntention(Direction.DOWN);
        pacman.nextMove();;
        // PacMan direction is down
        assertEquals(pacman.getDirection(), Direction.DOWN);
        // It has moved one pixel down
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-4);
        // We give an intention to go right
        // The intention should be kept until we reach the next tile center
        // (the original y position)
        pacman.setIntention(Direction.RIGHT);
        pacman.nextMove();;
        assertEquals(pacman.getIntention(), Direction.RIGHT);
        // PacMan direction is down
        assertEquals(pacman.getDirection(), Direction.DOWN);
        // It has moved one pixel down
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-3);
        pacman.nextMove();;
        assertEquals(pacman.getIntention(), Direction.RIGHT);
        // PacMan direction is down
        assertEquals(pacman.getDirection(), Direction.DOWN);
        // It has moved one pixel down
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-2);
        pacman.nextMove();;
        assertEquals(pacman.getIntention(), Direction.RIGHT);
        // PacMan direction is down
        assertEquals(pacman.getDirection(), Direction.DOWN);
        // It has moved one pixel down
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-1);
        // At the next move, pacman reaches the tile center and change its direction
        pacman.nextMove();;
        // The intention should be empty
        assertNull(pacman.getIntention());
        // PacMan direction is right
        assertEquals(pacman.getDirection(), Direction.RIGHT);
        // It has moved one pixel down (previous direction)
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y);
        // Next move : pacman moves right
        pacman.nextMove();;
        // The intention should be empty
        assertNull(pacman.getIntention());
        // PacMan direction is right
        assertEquals(pacman.getDirection(), Direction.RIGHT);
        // It has moved one pixel right
        assertEquals(pacman.getX(), x + 1);
        assertEquals(pacman.getY(), y);
    }

    /**
     * We test an actual turn on pacman at high speed
     * Initially pacman goes some pixels left
     * We then ask pacman to turn around
     * Then we ask to go up and check that the intention is kept until reaching the tile center.
     * When reaching the tile center, pacman's direction changes to up
     * @throws PacManException
     */
    @Test
    public void testHighSpeedSetIntentionTurnUp() throws PacManException, FileNotFoundException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        Actor pacman = testBoard.getPacMan();
        pacman.setSpeed(1.5);
        Maze maze = testBoard.getMaze();
        int x = pacman.getX(); // x position at start
        int y = pacman.getY();
        System.out.println(x);
        pacman.nextMove();;
        // PacMan direction is to the left
        assertEquals(pacman.getDirection(), Direction.LEFT);
        // It has moved 2 pixels to the left (due to speed = 1.5)
        assertEquals(pacman.getX(), x-2);
        assertEquals(pacman.getY(), y);
        // We move some more pixels to the left to go to the next tile
        pacman.nextMove();
        pacman.nextMove();
        // We turn around and go right
        pacman.setIntention(Direction.RIGHT);
        pacman.nextMove();
        // We give an intention to go up
        // The intention should be kept until we reach the next tile center

        pacman.setIntention(Direction.UP);
        pacman.nextMove();
        while (pacman.getDirection() == Direction.RIGHT) {
            pacman.nextMove();
        }
        assertEquals(pacman.getDirection(), Direction.UP);
        // Pacman starts moving up, it should be at the tile center
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y);
    }
}


