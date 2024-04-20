package fr.upsaclay.bibs.pacman.test.step3;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.Actor;
import fr.upsaclay.bibs.pacman.model.actors.GhostState;
import fr.upsaclay.bibs.pacman.model.actors.GhostType;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;
import fr.upsaclay.bibs.pacman.test.Configurations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PacManTest {

    /*********************************************************************/
    /**                    Step 1 Tests                                 **/
    /**              Adapted for speed                                  **/
    /*********************************************************************/

    @Test
    public void testSetGetXY() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        Actor pacman = testBoard.getPacMan();
        pacman.setPosition(3,3);
        assertEquals(pacman.getX(), 3);
        assertEquals(pacman.getY(), 3);
    }

    @Test
    public void testGetCurrentTile() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        Actor pacman = testBoard.getPacMan();
        pacman.setPosition(3,3);
        assertEquals(pacman.getCurrentTile(), new TilePosition(0,0));
        pacman.setPosition(35, 75);
        assertEquals(pacman.getCurrentTile(), new TilePosition(9,4));
    }

    @Test
    public void testSetGetDirection() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        Actor pacman = testBoard.getPacMan();
        pacman.setDirection(Direction.UP);
        assertEquals(pacman.getDirection(), Direction.UP);
    }

    /**
     * We test : PacMan starts going left then goes right then left again
     * @throws PacManException
     */
    @Test
    public void testSetIntentionTurnAroundLeftRight() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        Actor pacman = testBoard.getPacMan();
        pacman.setSpeed(1);
        Maze maze = testBoard.getMaze();
        int x = pacman.getX(); // x position at start
        int y = pacman.getY();
        pacman.nextMove();
        // PacMan directio is to the left
        assertEquals(pacman.getDirection(), Direction.LEFT);
        // It has moved one pixel to the left
        assertEquals(pacman.getX(), x-1);
        assertEquals(pacman.getY(), y);
        // We ask Pacman to go right
        pacman.setIntention(Direction.RIGHT);
        pacman.nextMove();
        // PacMan direction should now be right
        assertEquals(pacman.getDirection(), Direction.RIGHT);
        // The intention should be back to null
        assertNull(pacman.getIntention());
        // Pacman has moved one pixel the right
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y);
        // We move one more time
        pacman.nextMove();
        assertEquals(pacman.getDirection(), Direction.RIGHT);
        assertEquals(pacman.getX(), x+1);
        assertEquals(pacman.getY(), y);
        // Now we give the order to go left
        pacman.setIntention(Direction.LEFT);
        pacman.nextMove();
        // PacMan direction should now be left
        assertEquals(pacman.getDirection(), Direction.LEFT);
        // The intention should be back to null
        assertNull(pacman.getIntention());
        // Pacman has moved one pixel the left
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y);
        // We move one more time
        pacman.nextMove();
        assertEquals(pacman.getDirection(), Direction.LEFT);
        assertEquals(pacman.getX(), x-1);
        assertEquals(pacman.getY(), y);
    }

    /**
     * We test : PacMan starts up then goes down then up again
     * @throws PacManException
     */
    @Test
    public void testSetIntentionTurnAroundUpDown() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        Actor pacman = testBoard.getPacMan();
        pacman.setDirection(Direction.UP); // We set the direction up before starting the actor
        pacman.setSpeed(1);
        testBoard.startActors();

        Maze maze = testBoard.getMaze();
        int x = pacman.getX(); // x position at start
        int y = pacman.getY();
        pacman.nextMove();
        // PacMan direction is up
        assertEquals(pacman.getDirection(), Direction.UP);
        // It has moved one pixel up
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-1);
        // We ask Pacman to go down
        pacman.setIntention(Direction.DOWN);
        pacman.nextMove();
        // PacMan direction should now be down
        assertEquals(pacman.getDirection(), Direction.DOWN);
        // The intention should be back to null
        assertNull(pacman.getIntention());
        // Pacman has moved one pixel down
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y);
        // We move one more time
        pacman.nextMove();
        assertEquals(pacman.getDirection(), Direction.DOWN);
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y+1);
        // Now we give the order to go UP
        pacman.setIntention(Direction.UP);
        pacman.nextMove();
        // PacMan direction should now be up
        assertEquals(pacman.getDirection(), Direction.UP);
        // The intention should be back to null
        assertNull(pacman.getIntention());
        // Pacman has moved one pixel up
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y);
        // We move one more time
        pacman.nextMove();
        assertEquals(pacman.getDirection(), Direction.UP);
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-1);
    }

    /**
     * We test when we ask pacman to turn but it's not possible due to a wall
     * Pacman starts to the left, moves one pixel then is asked to up
     * It cannot go up and continues left
     * Then is asked to go down
     * It cannot go down and continues left
     * @throws PacManException
     */
    @Test
    public void testSetIntentionNotTurningLeftUpDown() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        Actor pacman = testBoard.getPacMan();
        pacman.setSpeed(1);
        Maze maze = testBoard.getMaze();
        int x = pacman.getX(); // x position at start
        int y = pacman.getY();
        System.out.println(pacman.getCurrentTile());
        System.out.println(STR."X = \{pacman.getX()} ;; Y = \{pacman.getY()}");
        pacman.nextMove();
        System.out.println(pacman.getCurrentTile());
        System.out.println(STR."X = \{pacman.getX()} ;; Y = \{pacman.getY()}");

        // PacMan direction is to the left
        assertEquals(pacman.getDirection(), Direction.LEFT);
        // It has moved one pixel to the left
        assertEquals(pacman.getX(), x-1);
        assertEquals(pacman.getY(), y);
        // We ask pacman to go up
        pacman.setIntention(Direction.UP);
        System.out.println(pacman.getCurrentTile());
        // Pacman has started at the tile center and has gone one pixel left
        // We move enough so that it reaches the next tile and tries its intention
        for(int i =0; i < 8; i++) {
            pacman.nextMove();
//            assertEquals(pacman.getX(), x-1);
//            System.out.println("Pacman X is "+pacman.getX()+" and intended x is " + (x-2-i));
//            System.out.println(pacman.getCurrentTile());
//            System.out.println(STR."X = \{pacman.getX()} ;; Y = \{pacman.getY()}");
            System.out.println("Intention : " + pacman.getIntention());
            System.out.println("Direction : " + pacman.getDirection());
//            System.out.println();
        }

        // Pacman should have tried to go up, failed and kept going left
        assertEquals(pacman.getDirection(), Direction.LEFT);
//        System.out.println(pacman.getCurrentTile());
//        System.out.println(STR."X = \{pacman.getX()} ;; Y = \{pacman.getY()}");
//        System.out.println(STR."Tile is : \{pacman.getBoard().getMaze().getTile(pacman.getCurrentTile())}");
        assertEquals(pacman.getY(), y);
        assertEquals(pacman.getX(), x-9);

        // The intention should be back to null
        assertNull(pacman.getIntention());
        // We ask pacman to go down
        pacman.setIntention(Direction.DOWN);
        // We move enough so that it reaches the next tile and tries its intention
        for(int i =0; i < 8; i++) {
            pacman.nextMove();;
        }
        // Pacman should have tried to go down, failed and kept going left
        assertEquals(pacman.getDirection(), Direction.LEFT);
        assertEquals(pacman.getX(), x-17);
        assertEquals(pacman.getY(), y);
        // The intention should be back to null
        assertNull(pacman.getIntention());
    }

    /**
     * We test when we ask pacman to turn but it's not possible due to a wall
     * Pacman starts up, moves one pixel then is asked to go left
     * It cannot go left and continues up
     * Then is asked to go right
     * It cannot go right and continues up
     * @throws PacManException
     */
    @Test
    public void testSetIntentionNotTurningUpLeftRight() throws PacManException {
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
        // We ask pacman to go left
        pacman.setIntention(Direction.LEFT);
        // Pacman has started at the tile center and has gone one pixel up
        // We move enough so that it reaches the next tile and tries its intention
        for(int i =0; i < 8; i++) {
            pacman.nextMove();;
        }
        // Pacman should have tried to go left, failed and kept going up
        assertEquals(pacman.getDirection(), Direction.UP);
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-9);
        // The intention should be back to null
        assertNull(pacman.getIntention());
        // We ask pacman to go right
        pacman.setIntention(Direction.RIGHT);
        // We move enough so that it reaches the next tile and tries its intention
        for(int i =0; i < 8; i++) {
            pacman.nextMove();;
        }
        // Pacman should have tried to go right, failed and kept going up
        assertEquals(pacman.getDirection(), Direction.UP);
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y-17);
        // The intention should be back to null
        assertNull(pacman.getIntention());
    }

    /**
     * We test an actual turn on pacman
     * Initially pacman goes some pixels left
     * We then ask pacman to turn around
     * Then we ask to go up and check that the intention is kept until reaching the tile center.
     * When reaching the tile center, pacman's direction changes to up
     * @throws PacManException
     */
    @Test
    public void testSetIntentionTurnUp() throws PacManException {
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
        System.out.println("Pacman is at "+pacman.getCurrentTile());
        System.out.println("Pacman is at "+pacman.getX()+" "+pacman.getY());
        System.out.println("Direction is "+pacman.getDirection()+" and intention is "+pacman.getIntention());

        // At the next move, pacman reaches the tile center and changes its direction
        pacman.nextMove();
        System.out.println("Pacman is at "+pacman.getCurrentTile());
        System.out.println("Pacman is at "+pacman.getX()+" "+pacman.getY());
        System.out.println("Direction is "+pacman.getDirection()+" and intention is "+pacman.getIntention());
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
    public void testSetIntentionTurnDown() throws PacManException {
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
    public void testSetIntentionTurnLeft() throws PacManException {
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
    public void testSetIntentionTurnRight() throws PacManException {
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
     * We test that if pacman reaches the end of the screen from the left,
     * it arrives on the other side
     * @throws PacManException
     */
    @Test
    public void testMovesLeftCircularScreen() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        Actor pacman = testBoard.getPacMan();
        pacman.setSpeed(1);
        Maze maze = testBoard.getMaze();
        int x = pacman.getX(); // x position at start
        int y = pacman.getY();
        // We move 4 tiles to the left
        for(int i = 0; i < 4*Maze.TILE_WIDTH; i ++) {
            pacman.nextMove();;
        }
        assertEquals(pacman.getX(), x - 4*Maze.TILE_WIDTH);
        assertEquals(pacman.getY(), y);
        // We are at the center of the leftmost tile
        assertEquals(pacman.getCurrentTile().getCol(), 0);
        x = pacman.getX();
        assertEquals(x, 3);
        // We should still be able to move left
        pacman.nextMove();;
        assertEquals(pacman.getX(), 2);
        pacman.nextMove();;
        assertEquals(pacman.getX(), 1);
        pacman.nextMove();;
        assertEquals(pacman.getX(), 0);
        // When we move one more pixel to the left, we go to the right end of the board
        pacman.nextMove();;
        assertEquals(pacman.getX(), maze.getPixelWidth() - 1);
        // the tile is the last tile of the line
        assertEquals(pacman.getCurrentTile().getCol(), maze.getWidth() - 1);
    }

    /**
     * We test that if pacman reaches the end of the screen from the right,
     * it arrives on the other side
     * @throws PacManException
     */
    @Test
    public void testMovesRightCircularScreen() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        Actor pacman = testBoard.getPacMan();
        pacman.setDirection(Direction.RIGHT);
        testBoard.startActors();
        pacman.setSpeed(1);
        Maze maze = testBoard.getMaze();
        int x = pacman.getX(); // x position at start
        int y = pacman.getY();
        // We move 4 tiles to the left
        for(int i = 0; i < 4*Maze.TILE_WIDTH; i ++) {
            pacman.nextMove();;
        }
        assertEquals(pacman.getX(), x + 4*Maze.TILE_WIDTH);
        assertEquals(pacman.getY(), y);
        // We are at the center of the rightmost tile
        assertEquals(pacman.getCurrentTile().getCol(), maze.getWidth() - 1);
        x = pacman.getX();
        assertEquals(x, maze.getPixelWidth() - 5);
        // We should still be able to move right
        pacman.nextMove();;
        assertEquals(pacman.getX(), maze.getPixelWidth() - 4);
        pacman.nextMove();;
        assertEquals(pacman.getX(), maze.getPixelWidth() - 3);
        pacman.nextMove();;
        assertEquals(pacman.getX(), maze.getPixelWidth() - 2);
        pacman.nextMove();;
        assertEquals(pacman.getX(), maze.getPixelWidth() - 1);
        // When we move one more pixel to the right, we go to the left end of the board
        pacman.nextMove();;
        assertEquals(pacman.getX(), 0);
        // the tile is the last tile of the line
        assertEquals(pacman.getCurrentTile().getCol(), 0);
    }

    @Test
    public void testMoveAndStopAtWall() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        Actor pacman = testBoard.getPacMan();
        pacman.setDirection(Direction.UP); // We set the direction up before starting the actor
        pacman.setSpeed(1);
        testBoard.startActors();
        assertFalse(pacman.isBlocked());
        Maze maze = testBoard.getMaze();
        // we move 6 tiles up
        for(int i =0; i < Maze.TILE_HEIGHT * 6; i++) {
            pacman.nextMove();;
        }
        int x = pacman.getX();
        int y = pacman.getY();
        boolean cond = pacman.isBlocked();
//        System.out.println("Pacman is at "+pacman.getCurrentTile());
//        System.out.println("Pacman is at "+pacman.getX()+" "+pacman.getY());
//        System.out.println("Direction is "+pacman.getDirection()+" and intention is "+pacman.getIntention());
//        assertFalse(pacman.isBlocked()); // not blocked yet
        // On the next move, we are stuck because of the wall
        pacman.nextMove();;
//
//        System.out.println("Pacman is at "+pacman.getCurrentTile());
//        System.out.println("Pacman is at "+pacman.getX()+" "+pacman.getY());
//        System.out.println("Direction is "+pacman.getDirection()+" and intention is "+pacman.getIntention());
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y);
        assertTrue(pacman.isBlocked());
        // still stuck
        pacman.nextMove();;
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y);
        assertTrue(pacman.isBlocked());
        // We ask pacman to go up, and it's still stuck
        pacman.setIntention(Direction.UP);
        pacman.nextMove();;
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y);
        assertTrue(pacman.isBlocked());
        // Now let's ask him to go down and unstuck
        pacman.setIntention(Direction.DOWN);
        pacman.nextMove();;
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y+1);
        assertFalse(pacman.isBlocked());
        // Let's ask him to go up again
        pacman.setIntention(Direction.UP);
        pacman.nextMove();;
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y);
        // Now we're again stuck
        pacman.nextMove();;
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y);
        assertTrue(pacman.isBlocked());
        // Let's ask him to go left -- that should unstuck him
        pacman.setIntention(Direction.LEFT);

        pacman.nextMove();;

        assertFalse(pacman.isBlocked());
        assertEquals(pacman.getX(), x-1);
        assertEquals(pacman.getY(), y);
//        System.out.println("Pacman is at "+pacman.getCurrentTile());
//        System.out.println("Pacman is at "+pacman.getX()+" "+pacman.getY());
//        System.out.println("Direction is "+pacman.getDirection()+" and intention is "+pacman.getIntention());
        // Now let's move to the left wall and get stuck again
        for(int i = 0; i < Maze.TILE_WIDTH; i++) {
            pacman.nextMove();;
            System.out.println("x is "+x+" and pacman x is "+pacman.getX());
            System.out.println("y is "+y+" and pacman y is "+pacman.getY());
        }
//        System.out.println("Pacman is at "+pacman.getCurrentTile());
//        System.out.println("Pacman is at X "+pacman.getX()+" Y"+pacman.getY());
//        System.out.println("Direction is "+pacman.getDirection()+" and intention is "+pacman.getIntention());
        assertEquals(pacman.getX(), x-8);
        assertEquals(pacman.getY(), y);
        assertTrue(pacman.isBlocked());
        // If we tell pacman to g right, it gets unstuck
        pacman.setIntention(Direction.RIGHT);
        pacman.nextMove();
        assertEquals(pacman.getX(), x-7);
        assertEquals(pacman.getY(), y);
        assertFalse(pacman.isBlocked());
    }

    /*********************************************************************/
    /**                    Step 2 Tests                                 **/
    /**                                                                 **/
    /*********************************************************************/

    @Test
    public void testSetGetSpeed() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        Actor pacman = testBoard.getPacMan();
        pacman.setSpeed(.5);
        assertEquals(pacman.getSpeed(), .5);
    }

    @Test
    public void testSpeedMove() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        Actor pacman = testBoard.getPacMan();
        pacman.setSpeed(.5);
        testBoard.startActors();
        int x = pacman.getX();
        int y = pacman.getY();
        pacman.nextMove();
        // AS we reduce x by .5, the int is reduced by 1
        assertEquals(pacman.getX(), x - 1);
        // Its position as decimal should be reduced by .5
        assertEquals(pacman.getRealX(), x - .5);
        assertEquals(pacman.getRealY(), y);
        pacman.nextMove();
        // On the next move, the int position is still x - 1
        assertEquals(pacman.getX(), x - 1);
        assertEquals(pacman.getRealX(), x - 1);
        assertEquals(pacman.getRealY(), y);
    }

    @Test
    public void testInitialSpeed() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        assertEquals(board.getPacMan().getSpeed(), 1);
    }

    @Test
    public void testStop() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        Actor pacman = testBoard.getPacMan();
        pacman.setSpeed(1);
        Maze maze = testBoard.getMaze();
        int x = pacman.getX();
        int y = pacman.getY();
        pacman.setStopTime(2);
        pacman.nextFrame(); // should not move because of stop time
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y);
        pacman.nextFrame(); // should not move because of stop time
        assertEquals(pacman.getX(), x);
        assertEquals(pacman.getY(), y);
        pacman.nextFrame(); // should move
        assertEquals(pacman.getX(), x - 1);
        assertEquals(pacman.getY(), y);
        pacman.setStopTime(1);
        pacman.nextFrame(); // should not move because of stop time
        assertEquals(pacman.getX(), x - 1);
        assertEquals(pacman.getY(), y);
        pacman.nextFrame(); // should move
        assertEquals(pacman.getX(), x - 2);
        assertEquals(pacman.getY(), y);
    }


    /*********************************************************************/
    /**                    Step 3 Tests                                 **/
    /**                                                                 **/
    /*********************************************************************/

    @Test
    public void testHighSpeed() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Configurations.blockGhosts(board.getMaze());
        Actor pacman = board.getPacMan();
        pacman.setSpeed(1.5);
        board.startActors();
        while (!pacman.isBlocked()) {
            board.nextFrame();
        }
        // Pacman is blocked, he should be at the center of the tile
        assertEquals(pacman.getX()%Maze.TILE_WIDTH, Maze.TITLE_CENTER_X);
        assertEquals(pacman.getY()%Maze.TILE_HEIGHT, Maze.TITLE_CENTER_Y);
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
    public void testHighSpeedSetIntentionTurnUp() throws PacManException {
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

    @Test
    public void testPacManLevelSpeedValues() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Actor pacman = board.getPacMan();
        assertEquals(pacman.getSpeed(), board.getLevelPacManSpeed());
        board.initializeNewLevel(2);
        pacman = board.getPacMan();
        assertEquals(pacman.getSpeed(), board.getLevelPacManSpeed());
    }

    @Test
    public void testPacManFrightSpeedValues() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        Actor pacman = board.getPacMan();
        TilePosition pos = pacman.getCurrentTile();
        TilePosition next = new TilePosition(pos.getLine(), pos.getCol() -1);
        // We place a big dot in front of pacman
        maze.setTile(next, Tile.BD);
        board.startActors();
        while(!pacman.getCurrentTile().equals(next)) {
            board.nextFrame();
        }
        // Pacman has eaten the ghost
        assertEquals(pacman.getSpeed(), board.getFrightPacManSpeed());
    }

}
