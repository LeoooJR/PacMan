package fr.upsaclay.bibs.pacman.test.step1;

import static org.junit.jupiter.api.Assertions.*;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.Actor;
import fr.upsaclay.bibs.pacman.model.actors.ActorType;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;
import org.junit.jupiter.api.Test;


public class BoardTest {

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

}
