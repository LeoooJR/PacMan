package fr.upsaclay.bibs.pacman.test.step2;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.Actor;
import fr.upsaclay.bibs.pacman.model.actors.Ghost;
import fr.upsaclay.bibs.pacman.model.actors.GhostType;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlinkyTest {

    @Test
    public void testCreateBlinky() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        List<Ghost> ghosts = board.getGhosts();
        Ghost blinky = ghosts.get(0);
        assertTrue(board.hasGhost(GhostType.BLINKY));
        assertEquals(board.getGhost(GhostType.BLINKY), blinky);
    }

    @Test
    public void testSetGetXY() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Actor blinky = board.getGhost(GhostType.BLINKY);
        blinky.setPosition(3,3);
        assertEquals(blinky.getX(), 3);
        assertEquals(blinky.getY(), 3);
    }

    @Test
    public void testGetCurrentTile() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Actor blinky = board.getGhost(GhostType.BLINKY);
        blinky.setPosition(3,3);
        assertEquals(blinky.getX(), 3);
        assertEquals(blinky.getY(), 3);
        assertEquals(blinky.getCurrentTile(), new TilePosition(0,0));
        blinky.setPosition(120,115);
        assertEquals(blinky.getCurrentTile(), new TilePosition(14,15));
    }

    @Test
    public void testInitialBlinkyValues() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        board.startActors();
        assertEquals(blinky.getBoard(), board);
        assertEquals(blinky.getGhostType(), GhostType.BLINKY);
        assertEquals(blinky.getX(), 112);
        assertEquals(blinky.getY(), 115);
        assertEquals(blinky.getDirection(), Direction.LEFT);
        assertEquals(blinky.getSpeed(), .94);
    }


    @Test
    public void testBlinkyTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        Actor pacman = board.getPacMan();
        board.startActors();
        // Initial target
        TilePosition pacmacPos = board.getMaze().getTilePosition(pacman.getX(), pacman.getY());
        assertEquals(blinky.getTarget(), pacmacPos);
        // We move a bit
        for(int i = 0; i < 20; i++) {
            board.nextFrame();
        }
        // Pacman position has changed and the target should have also changed
        pacmacPos = board.getMaze().getTilePosition(pacman.getX(), pacman.getY());
        assertEquals(blinky.getTarget(), pacmacPos);
    }

    @Test
    public void testBlinkyMoves() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        Actor pacman = board.getPacMan();
        board.startActors();
        // We test that blinky moves on new frames
        int x = blinky.getX();
        board.nextFrame();
        assertEquals(blinky.getX(), x - 1);
        // We move again 1 frame, with blinky speed this should move one more x to left
        board.nextFrame();
        assertEquals(blinky.getX(), x - 2);
    }

    @Test
    public void testBlinkyNewIntention() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        blinky.setSpeed(1);
        // We move one tile to the left
        for(int i = 0; i < 8; i++) {
            board.nextFrame();
        }
        // Blinky has changed tile, it should get a new intention (which is left)
        assertEquals(blinky.getIntention(), Direction.LEFT);
    }

    /**
     * We let PacMan go left and stop and wait for Blinky to arrive at PacMan
     * We test the different directions and positions blinky reaches
     * @throws PacManException
     */
    @Test
    public void testBlinkyMoveToPacMan() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        Actor pacman = board.getPacMan();
        board.startActors();
        // We move as long as blinky goes left.
        // If this is an infinite loop, it means something is wrong
        while (blinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        // The next direction should be down
        assertEquals(blinky.getDirection(), Direction.DOWN);
        // Blnky should be in tile (14,9) (corner top left above the ghost pen)
        assertEquals(blinky.getCurrentTile(), new TilePosition(14, 9));
        // We move as long as its down
        while (blinky.getDirection() == Direction.DOWN) {
            board.nextFrame();
        }
        // It should be left again
        assertEquals(blinky.getDirection(), Direction.LEFT);
        // Blinky should be in (23, 9)
        assertEquals(blinky.getCurrentTile(), new TilePosition(23, 9));
        while (blinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        // It should be down again
        assertEquals(blinky.getDirection(), Direction.DOWN);
        // It should be in (23, 6) (just above pacman)
        assertEquals(blinky.getCurrentTile(), new TilePosition(23, 6));

    }

    /**
     * We let PacMan go right and stop and wait for Blinky to arrive at PacMan
     * We test the different directions and positions blinky reaches
     * @throws PacManException
     */
    @Test
    public void testBlinkyMoveToPacMan2() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        Actor pacman = board.getPacMan();
        pacman.setIntention(Direction.RIGHT);
        board.startActors();
        // We move as long as blinky goes left.
        // If this is an infinite loop, it means something is wrong
        while (blinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        // The next direction should be down
        assertEquals(blinky.getDirection(), Direction.DOWN);
        // Blnky should be in tile (14,9) (corner top left above the ghost pen)
        assertEquals(blinky.getCurrentTile(), new TilePosition(14, 9));
        // We move as long as its down
        while (blinky.getDirection() == Direction.DOWN) {
            board.nextFrame();
        }
        // It should be right
        assertEquals(blinky.getDirection(), Direction.RIGHT);
        // Blinky should be in (20, 9)
        assertEquals(blinky.getCurrentTile(), new TilePosition(20, 9));
        while (blinky.getDirection() == Direction.RIGHT) {
            board.nextFrame();
        }
        // It should be down
        assertEquals(blinky.getDirection(), Direction.DOWN);
        // It should be in (20, 18)
        assertEquals(blinky.getCurrentTile(), new TilePosition(20, 18));
        while (blinky.getDirection() == Direction.DOWN) {
            board.nextFrame();
        }
        assertEquals(blinky.getDirection(), Direction.RIGHT);
        assertEquals(blinky.getCurrentTile(), new TilePosition(23, 18));
        while (blinky.getDirection() == Direction.RIGHT) {
            board.nextFrame();
        }
        assertEquals(blinky.getDirection(), Direction.DOWN);
        assertEquals(blinky.getCurrentTile(), new TilePosition(23, 21));
    }

    @Test
    public void testBlinkyNoTurnUp() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        Actor pacman = board.getPacMan();
        pacman.setPosition(208, 35); // (top right corner)
        board.startActors();
        TilePosition nt = new TilePosition(14,12);
        while (!blinky.getCurrentTile().equals(nt)) {
            board.nextFrame();
        }
        assertEquals(maze.getTile(14,12), Tile.NT);
        assertEquals(maze.getTile(13,12), Tile.EE);
        // Blinky is on tile of type NT. It cannot turn up even  though pacman is up
        assertEquals(blinky.getDirection(), Direction.LEFT);
        while (blinky.getCurrentTile().equals(nt)) {
            board.nextFrame();
        }
        // It should stil go left
        assertEquals(blinky.getDirection(), Direction.LEFT);
    }

    /**
     * Same test than testBlinkyNoTurnUp but with SD
     * @throws PacManException
     */
    @Test
    public void testBlinkyNoTurnUp2() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        maze.setTile(14,12,Tile.ND);
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        Actor pacman = board.getPacMan();
        pacman.setPosition(208, 35); // (top right corner)
        board.startActors();
        TilePosition nt = new TilePosition(14,12);
        while (!blinky.getCurrentTile().equals(nt)) {
            board.nextFrame();
        }
        assertEquals(maze.getTile(14,12), Tile.ND);
        assertEquals(maze.getTile(13,12), Tile.EE);
        // Blinky is on tile of type ND. It cannot turn up even  though pacman is up
        assertEquals(blinky.getDirection(), Direction.LEFT);
        while (blinky.getCurrentTile().equals(nt)) {
            board.nextFrame();
        }
        // It should stil go left
        assertEquals(blinky.getDirection(), Direction.LEFT);
    }

    @Test
    public void testBlinkyTurnUp() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        // We change the NT in front of blinky into an EE
        maze.setTile(14,12,Tile.EE);
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        Actor pacman = board.getPacMan();
        pacman.setPosition(208, 35); // (top right corner)
        board.startActors();
        TilePosition nt = new TilePosition(14,12);
        while (!blinky.getCurrentTile().equals(nt)) {
            board.nextFrame();
        }
        // When it arrives on the tile Blinky should be going left
        assertEquals(blinky.getDirection(), Direction.LEFT);
        // Pacman is up and it's a normal EE tile
        while (blinky.getCurrentTile().equals(nt)) {
            board.nextFrame();
        }
        // Blinky should be going up
        assertEquals(blinky.getDirection(), Direction.UP);
    }

    @Test
    public void testBlinkySlow() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        // We change the NT in front of blinky into an SL
        maze.setTile(14,12,Tile.SL);
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        Actor pacman = board.getPacMan();
        board.startActors();
        TilePosition nt = new TilePosition(14,12);
        while (!blinky.getCurrentTile().equals(nt)) {
            board.nextFrame();
        }
        // When it arrives on the tile Blinky should go slow
        assertEquals(blinky.getSpeed(), .5);
    }
}
