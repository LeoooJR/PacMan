package fr.upsaclay.bibs.pacman.test.step4;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.Actor;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PacManCorneringTurnTest {

    /**
     * We test an actual turn on pacman
     * Initially pacman goes some pixels left
     * We then ask pacman to turn around
     * Then we ask to go up and check the cornering turn is happening
     * @throws PacManException
     */
    @Test
    public void testCorneringSetIntentionTurnUp() throws PacManException {
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
        // We move 5 more pixels to the left to go to the next tile
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        // PacMan direction is to the left
        assertEquals(pacman.getDirection(), Direction.LEFT);
        // It has moved 6 pixels to the left
        assertEquals(pacman.getX(), x-6);
        assertEquals(pacman.getY(), y);
        // We turn around and go right
        pacman.setIntention(Direction.RIGHT);
        pacman.nextMove();;
        // PacMan direction is to the Right
        assertEquals(pacman.getDirection(), Direction.RIGHT);
        // It has moved one pixel to the right
        assertEquals(pacman.getX(), x-5);
        assertEquals(pacman.getY(), y);
        // We give an intention to go up
        // Pacman is still on the same tile, it does not apply the intention yet
        pacman.setIntention(Direction.UP);
        pacman.nextMove();;
        assertEquals(pacman.getIntention(), Direction.UP);
        assertEquals(pacman.getDirection(), Direction.RIGHT);
        assertEquals(pacman.getX(), x-4);
        assertEquals(pacman.getY(), y);
        // On the next move, pacman arrives on the new tile and applies the intention
        pacman.nextMove();
        assertEquals(pacman.getIntention(), null);
        assertEquals(pacman.getDirection(), Direction.UP);
        // It has moves both right and up
        assertEquals(pacman.getX(), x-3);
        assertEquals(pacman.getY(), y-1);
        // We keep moving / turning
        pacman.nextMove();
        assertEquals(pacman.getX(), x-2);
        assertEquals(pacman.getY(), y-2);

        pacman.nextMove();
        assertEquals(pacman.getX(), x-1);
        assertEquals(pacman.getY(), y-3);

        pacman.nextMove();
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-4);

        // Now Pacman has reached the center horizontally
        // and keeps going up

        pacman.nextMove();
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-5);
    }

    /**
     * We test an actual turn on pacman
     * Initially pacman goes some pixels left
     * We then ask pacman to turn around
     * Then we ask to go up and check the cornering turn is happening
     * @throws PacManException
     */
    @Test
    public void testCorneringSetIntentionTurnDown() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        Actor pacman = testBoard.getPacMan();
        pacman.setSpeed(1);
        Maze maze = testBoard.getMaze();
        int x = pacman.getX(); // x position at start
        int y = pacman.getY();
        pacman.nextMove();
        // PacMan direction is to the left
        assertEquals(pacman.getDirection(), Direction.LEFT);
        // It has moved one pixel to the left
        assertEquals(pacman.getX(), x-1);
        assertEquals(pacman.getY(), y);
        // We move 5 more pixels to the left to go to the next tile
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        // PacMan direction is to the left
        assertEquals(pacman.getDirection(), Direction.LEFT);
        // It has moved 6 pixels to the left
        assertEquals(pacman.getX(), x-6);
        assertEquals(pacman.getY(), y);
        // We turn around and go right
        pacman.setIntention(Direction.RIGHT);
        pacman.nextMove();;
        // PacMan direction is to the Right
        assertEquals(pacman.getDirection(), Direction.RIGHT);
        // It has moved one pixel to the right
        assertEquals(pacman.getX(), x-5);
        assertEquals(pacman.getY(), y);
        // We give an intention to go down
        // Pacman is still on the same tile, it does not apply the intention yet
        pacman.setIntention(Direction.DOWN);
        pacman.nextMove();;
        assertEquals(pacman.getIntention(), Direction.DOWN);
        assertEquals(pacman.getDirection(), Direction.RIGHT);
        assertEquals(pacman.getX(), x-4);
        assertEquals(pacman.getY(), y);
        // On the next move, pacman arrives on the new tile and applies the intention
        pacman.nextMove();
        assertEquals(pacman.getIntention(), null);
        assertEquals(pacman.getDirection(), Direction.DOWN);
        // It has moves both right and down
        assertEquals(pacman.getX(), x-3);
        assertEquals(pacman.getY(), y+1);
        // We keep moving / turning
        pacman.nextMove();
        assertEquals(pacman.getX(), x-2);
        assertEquals(pacman.getY(), y+2);

        pacman.nextMove();
        assertEquals(pacman.getX(), x-1);
        assertEquals(pacman.getY(), y+3);

        pacman.nextMove();
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y+4);

        // Now Pacman has reached the center horizontally
        // and keeps going down

        pacman.nextMove();
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y+5);
    }

    /**
     * We test an actual turn on pacman
     * Initially pacman goes some pixels up
     * We then ask pacman to turn around
     * Then we ask to go left and check that the cornering turn is happening
     * @throws PacManException
     */
    @Test
    public void testCorneringSetIntentionTurnLeft() throws PacManException {
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
        // We move 5 more pixels up to go to the next tile
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        // PacMan direction is up
        assertEquals(pacman.getDirection(), Direction.UP);
        // It has moved 6 pixels up
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-6);
        // We turn around and go down
        pacman.setIntention(Direction.DOWN);
        pacman.nextMove();;
        // PacMan direction is down
        assertEquals(pacman.getDirection(), Direction.DOWN);
        // It has moved one pixel down
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-5);
        // We give an intention to go left
        // Pacman is still on the same tile, it does not apply the intention yet
        pacman.setIntention(Direction.LEFT);
        pacman.nextMove();
        assertEquals(pacman.getIntention(), Direction.LEFT);
        assertEquals(pacman.getDirection(), Direction.DOWN);
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-4);
        // On the next move, pacman arrives on the new tile and applies the intention
        pacman.nextMove();
        assertEquals(pacman.getIntention(), null);
        assertEquals(pacman.getDirection(), Direction.LEFT);
        // It has moves both left and down
        assertEquals(pacman.getX(), x-1);
        assertEquals(pacman.getY(), y-3);
        // We keep moving / turning
        pacman.nextMove();
        assertEquals(pacman.getX(), x-2);
        assertEquals(pacman.getY(), y-2);

        pacman.nextMove();
        assertEquals(pacman.getX(), x-3);
        assertEquals(pacman.getY(), y-1);

        pacman.nextMove();
        assertEquals(pacman.getX(), x-4);
        assertEquals(pacman.getY(), y);

        // Now Pacman has reached the center vertically
        // and keeps going left

        pacman.nextMove();
        assertEquals(pacman.getX(), x-5);
        assertEquals(pacman.getY(), y);
    }

    /**
     * We test an actual turn on pacman
     * Initially pacman goes some pixels down
     * We then ask pacman to turn around
     * Then we ask to go left and check that the cornering turn is happening
     * @throws PacManException
     */
    @Test
    public void testCorneringSetIntentionTurnLeftFromDown() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        Actor pacman = testBoard.getPacMan();
        pacman.setDirection(Direction.DOWN); // We set the direction up before starting the actor
        pacman.setSpeed(1);
        testBoard.startActors();

        Maze maze = testBoard.getMaze();
        int x = pacman.getX(); // x position at start
        int y = pacman.getY();
        pacman.nextMove();;
        // PacMan direction is down
        assertEquals(pacman.getDirection(), Direction.DOWN);
        // It has moved one pixel down
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y+1);
        // We move 6 more pixels up to go to the next tile
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        // PacMan direction is down
        assertEquals(pacman.getDirection(), Direction.DOWN);
        // It has moved 7 pixels down
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y+7);
        // We turn around and go up
        pacman.setIntention(Direction.UP);
        pacman.nextMove();;
        // PacMan direction is up
        assertEquals(pacman.getDirection(), Direction.UP);
        // It has moved one pixel up
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y+6);
        // We give an intention to go left
        // Pacman is still on the same tile, it does not apply the intention yet
        pacman.setIntention(Direction.LEFT);
        pacman.nextMove();
        assertEquals(pacman.getIntention(), Direction.LEFT);
        assertEquals(pacman.getDirection(), Direction.UP);
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y+5);
        // On the next move, pacman arrives on the new tile and applies the intention
        pacman.nextMove();
        assertEquals(pacman.getIntention(), null);
        assertEquals(pacman.getDirection(), Direction.LEFT);
        // It has moves both left and up
        assertEquals(pacman.getX(), x-1);
        assertEquals(pacman.getY(), y+4);
        // We keep moving / turning
        pacman.nextMove();
        assertEquals(pacman.getX(), x-2);
        assertEquals(pacman.getY(), y+3);

        pacman.nextMove();
        assertEquals(pacman.getX(), x-3);
        assertEquals(pacman.getY(), y+2);

        pacman.nextMove();
        assertEquals(pacman.getX(), x-4);
        assertEquals(pacman.getY(), y+1);

        pacman.nextMove();
        assertEquals(pacman.getX(), x-5);
        assertEquals(pacman.getY(), y);

        // Now Pacman has reached the center vertically
        // and keeps going left

        pacman.nextMove();
        assertEquals(pacman.getX(), x-6);
        assertEquals(pacman.getY(), y);
    }


    /**
     * We test an actual turn on pacman
     * Initially pacman goes some pixels up
     * We then ask pacman to turn around
     * Then we ask to go right and check that the cornering turn is happening
     * @throws PacManException
     */
    @Test
    public void testCorneringSetIntentionTurnRight() throws PacManException {
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
        // We move 5 more pixels up to go to the next tile
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        // PacMan direction is up
        assertEquals(pacman.getDirection(), Direction.UP);
        // It has moved 6 pixels up
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-6);
        // We turn around and go down
        pacman.setIntention(Direction.DOWN);
        pacman.nextMove();
        // PacMan direction is down
        assertEquals(pacman.getDirection(), Direction.DOWN);
        // It has moved one pixel down
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-5);
        // We give an intention to go right
        // Pacman is still on the same tile, it does not apply the intention yet
        pacman.setIntention(Direction.RIGHT);
        pacman.nextMove();
        assertEquals(pacman.getIntention(), Direction.RIGHT);
        assertEquals(pacman.getDirection(), Direction.DOWN);
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-4);
        // On the next move, pacman arrives on the new tile and applies the intention
        pacman.nextMove();
        assertEquals(pacman.getIntention(), null);
        assertEquals(pacman.getDirection(), Direction.RIGHT);
        // It has moves both left and down
        assertEquals(pacman.getX(), x+1);
        assertEquals(pacman.getY(), y-3);
        // We keep moving / turning
        pacman.nextMove();
        assertEquals(pacman.getX(), x+2);
        assertEquals(pacman.getY(), y-2);

        pacman.nextMove();
        assertEquals(pacman.getX(), x+3);
        assertEquals(pacman.getY(), y-1);

        pacman.nextMove();
        assertEquals(pacman.getX(), x+4);
        assertEquals(pacman.getY(), y);

        // Now Pacman has reached the center vertically
        // and keeps going right

        pacman.nextMove();
        assertEquals(pacman.getX(), x+5);
        assertEquals(pacman.getY(), y);
    }

    /**
     * We test an actual turn on pacman at high speed
     * Initially pacman goes some pixels left
     * We then ask pacman to turn around
     * Then we ask to go up and check that the cornering turn is happening
     * @throws PacManException
     */
    @Test
    public void testCorneringHighSpeedSetIntentionTurnUp() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        Actor pacman = testBoard.getPacMan();
        pacman.setSpeed(1.5);
        Maze maze = testBoard.getMaze();
        int x = pacman.getX(); // x position at start
        int y = pacman.getY();
        pacman.nextMove();
        // PacMan direction is to the left
        assertEquals(pacman.getDirection(), Direction.LEFT);
        // It has moved 2 pixels to the left (due to speed = 1.5)
        assertEquals(pacman.getX(), x-2);
        assertEquals(pacman.getY(), y);
        // We move some more pixels to the left to go to the next tile
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        // We turn around and go right
        pacman.setIntention(Direction.RIGHT);
        pacman.nextMove();
        // We give an intention to go up
        // The intention should be kept until we reach the next tile
        TilePosition pos = pacman.getCurrentTile();
        pacman.setIntention(Direction.UP);
        while (pacman.getCurrentTile().equals(pos)) {
            pacman.nextMove();
        }
        // The direction should be up
        assertEquals(pacman.getDirection(), Direction.UP);
        // But it has not reached the tile center horizontally yet
        assertTrue(pacman.getX() < x);
        // And it has started going up
        assertTrue(pacman.getY() < y);

        // We move until the x position is stabilized
        int prev;
        do {
            prev = pacman.getX();
            pacman.nextMove();
        }while (pacman.getX() != prev);
        // Pacman x position should be at the center of the tile
        assertEquals(pacman.getX(), x);
    }

    /**
     * We test an actual turn on pacman at high speed
     * Initially pacman goes some pixels left
     * We then ask pacman to turn around
     * Then we ask to go up and check that the cornering turn is happening
     * @throws PacManException
     */
    @Test
    public void testCorneringHighSpeedSetIntentionTurnLeft() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        Actor pacman = testBoard.getPacMan();
        pacman.setSpeed(1.5);
        pacman.setDirection(Direction.UP);
        Maze maze = testBoard.getMaze();
        int x = pacman.getX(); // x position at start
        int y = pacman.getY();
        pacman.nextMove();;
        // PacMan direction is to the left
        assertEquals(pacman.getDirection(), Direction.UP);
        // It has moved 2 pixels up (due to speed = 1.5)
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-2);
        // We move some more pixels up to go to the next tile
        pacman.nextMove();
        pacman.nextMove();
        pacman.nextMove();
        // We turn around and go down
        pacman.setIntention(Direction.DOWN);
        pacman.nextMove();
        // We give an intention to go left
        // The intention should be kept until we reach the next tile
        TilePosition pos = pacman.getCurrentTile();
        pacman.setIntention(Direction.LEFT);
        while (pacman.getCurrentTile().equals(pos)) {
            pacman.nextMove();
        }
        // The direction should be left
        assertEquals(pacman.getDirection(), Direction.LEFT);
        // But it has not reached the tile center vertically yet
        assertTrue(pacman.getY() < y);
        // And it has started going left
        assertTrue(pacman.getX() < x);

        // We move until the y position is stabilized
        int prev;
        do {
            prev = pacman.getY();
            pacman.nextMove();
        }while (pacman.getY() != prev);
        // Pacman y position should be at the center of the tile
        assertEquals(pacman.getY(), y);
    }
}
