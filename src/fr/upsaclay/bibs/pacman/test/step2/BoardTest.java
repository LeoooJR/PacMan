package fr.upsaclay.bibs.pacman.test.step2;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.Actor;
import fr.upsaclay.bibs.pacman.model.actors.ActorType;
import fr.upsaclay.bibs.pacman.model.actors.Ghost;
import fr.upsaclay.bibs.pacman.model.actors.GhostType;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.BoardState;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class BoardTest {

    /*********************************************************************/
    /**                    Step 1 Tests                                 **/
    /**              should still work                                  **/
    /*********************************************************************/


    @Test
    public void testTestBoardCreation() {
        Board testBoard = Board.createBoard(GameType.TEST);
        assertEquals(testBoard.getGameType(), GameType.TEST);
    }

    @Test
    public void testClassicBoardCreation() {
        Board classicBoard = Board.createBoard(GameType.CLASSIC);
        assertEquals(classicBoard.getGameType(), GameType.CLASSIC);
    }

    @Test
    public void testTestBoardInitialization() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        /** test that PacMan is not null after initialization **/
        assertNotNull(testBoard.getPacMan());
        /** test that PacMan is an initial position **/
        assertEquals(testBoard.getPacMan().getX(), 35);
        assertEquals(testBoard.getPacMan().getY(), 75);
        assertEquals(testBoard.getPacMan().getCurrentTile(), new TilePosition(9,4));
        /** test that the maze is not null after initialization **/
        assertNotNull(testBoard.getMaze());
        assertEquals(testBoard.getMaze().getWidth(),9);
        assertEquals(testBoard.getMaze().getHeight(), 17);
    }

    @Test
    public void testClassicBoardInitialization() throws PacManException {
        Board classicBoard = Board.createBoard(GameType.CLASSIC);
        classicBoard.initialize();
        /** test that PacMan is not null after initialization **/
        assertNotNull(classicBoard.getPacMan());
        assertEquals(classicBoard.getPacMan().getType(), ActorType.PACMAN);
        /** test that PacMan is an initial position **/
        assertEquals(classicBoard.getPacMan().getX(), 112);
        assertEquals(classicBoard.getPacMan().getY(), 211);
        assertEquals(classicBoard.getPacMan().getCurrentTile(), new TilePosition(26, 14));
        /** test that the maze is not null after initialization **/
        assertNotNull(classicBoard.getMaze());
        assertEquals(classicBoard.getMaze().getWidth(),28);
        assertEquals(classicBoard.getMaze().getHeight(), 36);
    }

    @Test
    public void testStartActor() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        /** test that PacMan has a direction when the game starts **/
        assertEquals(testBoard.getPacMan().getDirection(), Direction.LEFT);
        Board classicBoard = Board.createBoard(GameType.CLASSIC);
        classicBoard.initialize();
        classicBoard.startActors();
        assertEquals(classicBoard.getPacMan().getDirection(), Direction.LEFT);
        assertNull(classicBoard.getPacMan().getIntention());
    }

    @Test
    public void testNextFrameMoving() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        Actor pacman = testBoard.getPacMan();
        int x = pacman.getX(); // x position at start
        int y = pacman.getY();
        TilePosition pos = pacman.getCurrentTile(); // initial tile position
        testBoard.nextFrame(); // PacMan moves one step to the left
        assertEquals(pacman.getX(), x-1);
        assertEquals(pacman.getY(), y);
        assertEquals(pacman.getCurrentTile(), pos); // the tile is still the same
        // We move 3 more pixels to the left
        testBoard.nextFrame();
        testBoard.nextFrame();
        testBoard.nextFrame();
        TilePosition newPos = new TilePosition(pos.getLine(), pos.getCol() - 1);
        assertEquals(pacman.getX(), x-4);
        assertEquals(pacman.getY(), y);
        assertEquals(pacman.getCurrentTile(), newPos); // new tile
    }



    /*********************************************************************/
    /**                    Step 2 Tests                                 **/
    /*********************************************************************/


    @Test
    public void testInitialScore() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        assertEquals(testBoard.getScore(), 0);
        Board classicBoard = Board.createBoard(GameType.CLASSIC);
        classicBoard.initialize();
        assertEquals(classicBoard.getScore(), 0);
    }

    /**
     * Test pacman / board behaviour when eating a regular dot
     * the score must be increased by 10
     * the dot should dispaear
     * pacman should be stopped for 1 frame
     * @throws PacManException
     */
    @Test
    public void testEatDot() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        Actor pacman = testBoard.getPacMan();
        pacman.setSpeed(1);
        Maze maze = testBoard.getMaze();
        int nbdots = maze.getNumberOfDots();
        // Pacman is in (9,4)
        // There is a small dot in (9,2)
        assertEquals(maze.getTile(9,2), Tile.SD);
        // We move to the left
        testBoard.nextFrame();
        // The dot is still here, the score is zero
        assertEquals(maze.getTile(9,2), Tile.SD);
        assertEquals(testBoard.getScore(), 0);
        while (!pacman.getCurrentTile().equals(new TilePosition(9,2))) {
            testBoard.nextFrame(); // we move until we reach the tile
        }
        // we have reached the tile
        assertEquals(maze.getTile(9,2), Tile.EE); // Pacman has eater the dot
        assertEquals(testBoard.getScore(), 10);
        assertEquals(maze.getNumberOfDots(), nbdots - 1);
        // Pacman stops for 1 frame
        int x = pacman.getX();
        testBoard.nextFrame();
        assertEquals(pacman.getX(), x);
        // then moves again
        testBoard.nextFrame();
        assertEquals(pacman.getX(), x-1);
    }

    /**
     * Test pacman / board behaviour when eating a dot on a "no up turn" tile (ND tile)
     * the score must be increased by 10
     * the dot should dispaear (the tile is replaced by NT)
     * pacman should be stopped for 1 frame
     * @throws PacManException
     */
    @Test
    public void testEatDotNoUpTurn() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        Actor pacman = testBoard.getPacMan();
        pacman.setDirection(Direction.RIGHT); // we go right
        testBoard.startActors();

        Maze maze = testBoard.getMaze();
        int nbdots = maze.getNumberOfDots();
        // Pacman is in (9,4)
        // There is a small dot of type ND in (9,6)
        assertEquals(maze.getTile(9,6), Tile.ND);
        // We move to the right
        testBoard.nextFrame();
        // The dot is still here, the score is zero
        assertEquals(maze.getTile(9,6), Tile.ND);
        assertEquals(testBoard.getScore(), 0);
        while (!pacman.getCurrentTile().equals(new TilePosition(9,6))) {
            testBoard.nextFrame(); // we move until we reach the tile
        }
        // we have reached the tile
        assertEquals(maze.getTile(9,6), Tile.NT); // Pacman has eaten the dot
        assertEquals(testBoard.getScore(), 10);
        assertEquals(maze.getNumberOfDots(), nbdots - 1);
        // Pacman stops for 1 frame
        int x = pacman.getX();
        testBoard.nextFrame();
        assertEquals(pacman.getX(), x);
        // then moves again
        testBoard.nextFrame();
        assertEquals(pacman.getX(), x+1);
    }

    /**
     * We test eating a big dot
     * Similar as small dot but score is increased by 50
     * pacman is stopped for 3 frames
     * @throws PacManException
     */
    @Test
    public void testEatBigDot() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        testBoard.startActors();
        Actor pacman = testBoard.getPacMan();
        pacman.setSpeed(1);
        Maze maze = testBoard.getMaze();
        // Pacman is in (9,4)
        // We put a big dot in (9,2)
        maze.setTile(9,2, Tile.BD);
        assertEquals(maze.getTile(9,2), Tile.BD);
        int nbdots = maze.getNumberOfDots();
        // We move to the left
        testBoard.nextFrame();
        // The dot is still here, the score is zero
        assertEquals(maze.getTile(9,2), Tile.BD);
        assertEquals(testBoard.getScore(), 0);
        while (!pacman.getCurrentTile().equals(new TilePosition(9,2))) {
            testBoard.nextFrame(); // we move until we reach the tile
        }
        // we have reached the tile
        assertEquals(maze.getTile(9,2), Tile.EE); // Pacman has eater the dot
        assertEquals(testBoard.getScore(), 50);
        assertEquals(maze.getNumberOfDots(), nbdots -1);
        // Pacman stops for 3 frames
        int x = pacman.getX();
        testBoard.nextFrame();
        assertEquals(pacman.getX(), x);
        testBoard.nextFrame();
        assertEquals(pacman.getX(), x);
        testBoard.nextFrame();
        assertEquals(pacman.getX(), x);
        // then moves again
        testBoard.nextFrame();
        assertEquals(pacman.getX(), x-1);
    }

    @Test
    public void testEatManyDots() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        Actor pacman = testBoard.getPacMan();
        pacman.setDirection(Direction.UP);
        testBoard.startActors();
        Maze maze = testBoard.getMaze();
        int nbdots = maze.getNumberOfDots();
        // Pacman is in (9,4)
        // We eat all the dots up to (3,4)
        while (!pacman.getCurrentTile().equals(new TilePosition(3,4))) {
            testBoard.nextFrame();
        }
        assertEquals(testBoard.getScore(), 90);
        assertEquals(maze.getNumberOfDots(), nbdots - 5);
    }

    @Test
    public void testBoardState() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        assertEquals(board.getBoardState(), BoardState.INITIAL);
        board.startActors();
        assertEquals(board.getBoardState(), BoardState.STARTED);
    }

    @Test
    public void testBoardStateEndLevel() throws PacManException {
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        Actor pacman = testBoard.getPacMan();
        Maze maze = testBoard.getMaze();
        // We remove all dots except one
        for(int i =0; i < maze.getHeight(); i++) {
            for(int j=0; j < maze.getWidth(); j++) {
                if(maze.getTile(i,j).hasDot() && (i != 9 || j != 2)) {
                    maze.setTile(i,j,maze.getTile(i,j).clearDot());
                }
            }
        }
        testBoard.startActors();
        assertEquals(testBoard.getBoardState(), BoardState.STARTED);
        while (!pacman.getCurrentTile().equals(new TilePosition(9,2))) {
            testBoard.nextFrame(); // we move until we reach the tile
        }
        assertEquals(testBoard.getBoardState(), BoardState.LEVEL_OVER);
    }

    @Test
    public void testCreateGhosts() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        List<Ghost> ghosts = board.getGhosts();
        assertEquals(ghosts.size(), 1);
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        assertEquals(blinky, ghosts.get(0));
        Board testBoard = Board.createBoard(GameType.TEST);
        testBoard.initialize();
        ghosts = testBoard.getGhosts();
        assertEquals(ghosts.size(), 0);
        assertNull(testBoard.getGhost(GhostType.BLINKY));
    }

    @Test
    public void testLifeOver() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        Actor pacman = board.getPacMan();
        board.startActors();
        // At some point, Blinky should reach PacMan and the game is over
        while(!pacman.getCurrentTile().equals(blinky.getCurrentTile())) {
            board.nextFrame();
        }
        // They are on the same time, the game should be over
        assertEquals(board.getBoardState(), BoardState.LIFE_OVER);
    }
}
