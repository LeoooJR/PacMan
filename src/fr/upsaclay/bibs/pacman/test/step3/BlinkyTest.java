package fr.upsaclay.bibs.pacman.test.step3;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.*;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.BoardState;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;
import fr.upsaclay.bibs.pacman.test.Configurations;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testDisableBlinky() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableGhost(GhostType.BLINKY);

        board.initialize();

        assertFalse(board.hasGhost(GhostType.BLINKY));
        assertNull(board.getGhost(GhostType.BLINKY));
    }

    @Test
    public void testSetGetXY() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Actor blinky = board.getPacMan();
        blinky.setPosition(3, 3);
        assertEquals(blinky.getX(), 3);
        assertEquals(blinky.getY(), 3);
    }

    @Test
    public void testGetCurrentTile() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Actor blinky = board.getPacMan();
        blinky.setPosition(3, 3);
        assertEquals(blinky.getX(), 3);
        assertEquals(blinky.getY(), 3);
        assertEquals(blinky.getCurrentTile(), new TilePosition(0, 0));
        blinky.setPosition(120, 115);
        assertEquals(blinky.getCurrentTile(), new TilePosition(14, 15));
    }

    @Test
    public void testInitialBlinkyValues() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        assertEquals(blinky.getBoard(), board);
        assertEquals(blinky.getGhostType(), GhostType.BLINKY);
        assertEquals(blinky.getX(), 112);
        assertEquals(blinky.getY(), 115);
        assertEquals(blinky.getCurrentTile(), new TilePosition(14, 14));
        assertEquals(blinky.getDirection(), Direction.LEFT);
        assertEquals(blinky.getGhostState(), GhostState.SCATTER);
        assertEquals(blinky.getGhostPenState(), GhostPenState.OUT);
    }

    @Test
    public void testSetGetGhostState() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        blinky.setGhostState(GhostState.CHASE);
        assertEquals(blinky.getGhostState(), GhostState.CHASE);
    }

    @Test
    public void testSetGetGhostPenState() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        blinky.setGhostPenState(GhostPenState.IN);
        assertEquals(blinky.getGhostPenState(), GhostPenState.IN);
    }

    @Test
    public void testBlinkyMoves() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
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
//        TODO: Leo the direction thing enjoy
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        Direction intent = blinky.getIntention();
//        Change state of the board
        blinky.setGhostState(GhostState.CHASE);
        blinky.setSpeed(1);
        System.out.println("****" + blinky.getCurrentTile());
        // We move one tile to the left
        for (int i = 0; i < 8; i++) {
            System.out.println("*****" + i);

            board.nextFrame();
            blinky.setGhostState(GhostState.CHASE);
            System.out.println(blinky.getCurrentTile());
            intent = blinky.getIntention();

            System.out.println(STR."Direction = \{blinky.getDirection()}");
            System.out.println(STR."Intention = \{blinky.getIntention()}");
        }
        System.out.println(blinky.getCurrentTile());
        intent = blinky.getIntention();
        TilePosition pacman = board.getMaze().getTilePosition(board.getPacMan().getX(), board.getPacMan().getY());
        System.out.println(STR."Pacman = \{pacman}");
        // Blinky has changed tile, it should get a new intention (which is left)
        assertEquals(blinky.getIntention(), Direction.LEFT);
    }

    @Test
    public void testBlinkyChaseTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        blinky.setGhostState(GhostState.CHASE);
        Actor pacman = board.getPacMan();
        board.startActors();
        // Initial target
        TilePosition pacmacPos = board.getMaze().getTilePosition(pacman.getX(), pacman.getY());
        assertEquals(blinky.getTarget(), pacmacPos);
        // We move a bit
        for (int i = 0; i < 20; i++) {
            System.out.println(STR."Blinky position : \{blinky.getCurrentTile()} Target : \{blinky.getTarget()}");
            System.out.println(STR."Pacman position : \{pacman.getCurrentTile()}");
            board.nextFrame();
            blinky.setGhostState(GhostState.CHASE);
            System.out.println("Ghost state is : " + blinky.getGhostState());
        }
        // Pacman position has changed and the target should have also changed
        pacmacPos = board.getMaze().getTilePosition(pacman.getX(), pacman.getY());
        assertEquals(blinky.getTarget(), pacmacPos);
    }

    /**
     * We let PacMan go left and stop and wait for Blinky to arrive at PacMan
     * We test the different directions and positions blinky reaches
     *
     * @throws PacManException
     */
    @Test
    public void testBlinkyMoveToPacMan() throws PacManException {
//        TODO: here too
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Maze maze = board.getMaze();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        blinky.setGhostState(GhostState.CHASE);
        Actor pacman = board.getPacMan();
        board.startActors();
        // We move as long as blinky goes left.
        // If this is an infinite loop, it means something is wrong
        while (blinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
//            blinky.setGhostState(GhostState.CHASE);

            System.out.println(blinky.getDirection());
            System.out.println(blinky.getCurrentTile());
            System.out.println(pacman.getCurrentTile());
            System.out.println(blinky.getTarget());
//            blinky.setGhostState(GhostState.CHASE);
            System.out.println(blinky.getGhostState());
        }
//
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
     *
     * @throws PacManException
     */
    @Test
    public void testBlinkyMoveToPacMan2() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        blinky.setGhostState(GhostState.CHASE);
        Actor pacman = board.getPacMan();
        pacman.setIntention(Direction.RIGHT);
        board.startActors();
        // We move as long as blinky goes left.
        // If this is an infinite loop, it means something is wrong
        while (blinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
            System.out.println("Blinky's Direction: " + blinky.getDirection());
            System.out.println("Blinky's Current Tile: " + blinky.getCurrentTile());
            System.out.println(STR."Pacman's Current Tile: \{pacman.getCurrentTile()}Pacman's Direction: \{pacman.getDirection()}");
            System.out.println("Blinky's Target: " + blinky.getTarget());

        }
        // The next direction should be down
        assertEquals(blinky.getDirection(), Direction.DOWN);
        // Blnky should be in tile (14,9) (corner top left above the ghost pen)
        assertEquals(blinky.getCurrentTile(), new TilePosition(14, 9));
        // We move as long as its down
        while (blinky.getDirection() == Direction.DOWN) {
            board.nextFrame();
            System.out.println("Blinky's Direction: " + blinky.getDirection());
            System.out.println("Blinky's Current Tile: " + blinky.getCurrentTile());
            System.out.println("Pacman's Current Tile: " + pacman.getCurrentTile());
            System.out.println("Blinky's Target: " + blinky.getTarget());
        }
        // It should be right
        TilePosition pos = blinky.getCurrentTile();
        TilePosition tar = blinky.getTarget();
        Direction dir = blinky.getDirection();
        TilePosition pac = pacman.getCurrentTile();
        System.out.println(STR."Pacman's Current Tile: \{pacman.getCurrentTile()}");
        Direction inten = blinky.getIntention();
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
//        TODO: Imfinite loop here
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Maze maze = board.getMaze();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        blinky.setGhostState(GhostState.CHASE);
        Actor pacman = board.getPacMan();
        pacman.setPosition(208, 35); // (top right corner)
        board.startActors();
        TilePosition nt = new TilePosition(14, 12);
        while (!blinky.getCurrentTile().equals(nt)) {
            board.nextFrame();
        }
        assertEquals(maze.getTile(14, 12), Tile.NT);
        assertEquals(maze.getTile(13, 12), Tile.EE);
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
     *
     * @throws PacManException
     */
    @Test
    public void testBlinkyNoTurnUp2() throws PacManException {
//        //        TODO: Leo the direction thing enjoy there is a loop here
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Maze maze = board.getMaze();
        maze.setTile(14, 12, Tile.ND);
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        blinky.setGhostState(GhostState.CHASE);
        Actor pacman = board.getPacMan();
        pacman.setPosition(208, 35); // (top right corner)
        board.startActors();
        TilePosition nt = new TilePosition(14, 12);
        while (!blinky.getCurrentTile().equals(nt)) {
            board.nextFrame();
        }
        assertEquals(maze.getTile(14, 12), Tile.ND);
        assertEquals(maze.getTile(13, 12), Tile.EE);
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
        maze.setTile(14, 12, Tile.EE);
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        board.startActors();
        TilePosition nt = new TilePosition(14, 12);
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
    public void testBlinkyScatterTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.startActors();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        TilePosition target = new TilePosition(0, 25);
        assertEquals(blinky.getTarget(), target);
    }

    @Test
    public void testReverseDirectionIntentionOut() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        board.startActors();
        blinky.reverseDirectionIntention();
        assertEquals(blinky.getDirection(), Direction.LEFT);
        assertEquals(blinky.getIntention(), Direction.RIGHT);
        while (blinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(blinky.getDirection(), Direction.RIGHT);
    }

    @Test
    public void testChangeStateScatterChaseOut() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        board.startActors();
        blinky.changeGhostState(GhostState.CHASE);
        assertEquals(blinky.getGhostState(), GhostState.CHASE);
        assertEquals(blinky.getIntention(), Direction.RIGHT);
        while (blinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(blinky.getDirection(), Direction.RIGHT);
        blinky.changeGhostState(GhostState.SCATTER);
        assertEquals(blinky.getGhostState(), GhostState.SCATTER);
        assertEquals(blinky.getIntention(), Direction.LEFT);
    }

    @Test
    public void testFrightenTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        board.startActors();
        blinky.setGhostState(GhostState.FRIGHTENED);
        // no target in frighten mode, should be null
        assertNull(blinky.getTarget());
    }

    @Test
    public void testFrightenEndTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        board.startActors();
        blinky.setGhostState(GhostState.FRIGHTENED_END);
        // no target in frighten end mode, should be null
        assertNull(blinky.getTarget());
    }

    @Test
    public void testChangeStateScatterFrightenOut() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        board.startActors();
        blinky.changeGhostState(GhostState.FRIGHTENED);
        assertEquals(blinky.getGhostState(), GhostState.FRIGHTENED);
        assertEquals(blinky.getIntention(), Direction.RIGHT);
        while (blinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(blinky.getDirection(), Direction.RIGHT);
        blinky.changeGhostState(GhostState.FRIGHTENED_END);
        assertEquals(blinky.getGhostState(), GhostState.FRIGHTENED_END);
        //        TODO: Leo the direction thing enjoy
        assertEquals(blinky.getIntention(), Direction.RIGHT); // No turning around
        blinky.changeGhostState(GhostState.SCATTER);
        assertEquals(blinky.getGhostState(), GhostState.SCATTER);
        assertEquals(blinky.getIntention(), Direction.RIGHT); // No turning around
    }

    @Test
    public void testChangeStateChaseFrightenOut() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        blinky.setGhostState(GhostState.CHASE);
        board.startActors();
        blinky.changeGhostState(GhostState.FRIGHTENED);
        assertEquals(blinky.getGhostState(), GhostState.FRIGHTENED);
        assertEquals(blinky.getIntention(), Direction.RIGHT);
        while (blinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(blinky.getDirection(), Direction.RIGHT);
        blinky.changeGhostState(GhostState.FRIGHTENED_END);
        assertEquals(blinky.getGhostState(), GhostState.FRIGHTENED_END);
        //        TODO: Leo the direction thing enjoy
        assertEquals(blinky.getIntention(), Direction.RIGHT); // No turning around
        blinky.changeGhostState(GhostState.CHASE);
        assertEquals(blinky.getGhostState(), GhostState.CHASE);
        assertEquals(blinky.getIntention(), Direction.RIGHT); // No turning around
    }

    @Test
    public void testMovingFrighten() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Configurations.blockGhosts(board.getMaze());
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        board.startActors();
        blinky.setGhostState(GhostState.FRIGHTENED);
        // We have blocked the ghost, so it can only go in square
        while (blinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        //        TODO: Leo the direction thing enjoy
        assertEquals(blinky.getCurrentTile(), new TilePosition(14, 9));
        assertEquals(blinky.getDirection(), Direction.DOWN);
        while (blinky.getDirection() == Direction.DOWN) {
            board.nextFrame();
        }
        assertEquals(blinky.getCurrentTile(), new TilePosition(20, 9));
        assertEquals(blinky.getDirection(), Direction.RIGHT);
        while (blinky.getDirection() == Direction.RIGHT) {
            board.nextFrame();
        }
        assertEquals(blinky.getCurrentTile(), new TilePosition(20, 18));
        assertEquals(blinky.getDirection(), Direction.UP);
        while (blinky.getDirection() == Direction.UP) {
            board.nextFrame();
        }
        assertEquals(blinky.getCurrentTile(), new TilePosition(14, 18));
        assertEquals(blinky.getDirection(), Direction.LEFT);
    }

    @Test
    public void testMovingFrightenEnd() throws PacManException {

        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Configurations.blockGhosts(board.getMaze());
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        board.startActors();
        blinky.setGhostState(GhostState.FRIGHTENED_END);
        // We have blocked the ghost, so it can only go in square
        while (blinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
//        TODO: Leo the direction thing enjoy
        assertEquals(blinky.getCurrentTile(), new TilePosition(14, 9));
        assertEquals(blinky.getDirection(), Direction.DOWN);
        while (blinky.getDirection() == Direction.DOWN) {
            board.nextFrame();
        }
        assertEquals(blinky.getCurrentTile(), new TilePosition(20, 9));
        assertEquals(blinky.getDirection(), Direction.RIGHT);
        while (blinky.getDirection() == Direction.RIGHT) {
            board.nextFrame();
        }
        assertEquals(blinky.getCurrentTile(), new TilePosition(20, 18));
        assertEquals(blinky.getDirection(), Direction.UP);
        while (blinky.getDirection() == Direction.UP) {
            board.nextFrame();
        }
        assertEquals(blinky.getCurrentTile(), new TilePosition(14, 18));
        assertEquals(blinky.getDirection(), Direction.LEFT);
    }

    @Test
    public void testDeadTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        board.startActors();
        blinky.setGhostState(GhostState.DEAD);
//      TODO: Still not sure about this --- Jaffar will come back to it
        assertEquals(blinky.getTarget(), board.penEntry());
    }

    @Test
    public void testSetGetOutOfPenDirection() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        assertEquals(blinky.getOutOfPenDirection(), Direction.LEFT);
        blinky.setOutOfPenDirection(Direction.RIGHT);
        assertEquals(blinky.getOutOfPenDirection(), Direction.RIGHT);
    }

    @Test
    public void testOutOfPenDirectionReverse() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        board.startActors();
        blinky.setGhostPenState(GhostPenState.IN);
        blinky.setDirection(Direction.DOWN);
        blinky.setIntention(Direction.DOWN);
        blinky.reverseDirectionIntention();
        // Blinky is set to be in the ghost pen, its intention should not have been changed
        assertEquals(blinky.getIntention(), Direction.DOWN);
        // Its out of pen direction should have been set to right
        assertEquals(blinky.getOutOfPenDirection(), Direction.RIGHT);
        blinky.reverseDirectionIntention();
        // Still right (it does not turn, it just changes to right once and for all
        assertEquals(blinky.getOutOfPenDirection(), Direction.RIGHT);
        // Same with get in
        blinky.setGhostPenState(GhostPenState.GET_IN);
        blinky.setOutOfPenDirection(Direction.LEFT);
        blinky.reverseDirectionIntention();
        // Blinky is set to be in the ghost pen, its intention should not have been changed
        assertEquals(blinky.getIntention(), Direction.DOWN);
        // Its out of pen direction should have been set to right
        assertEquals(blinky.getOutOfPenDirection(), Direction.RIGHT);
        blinky.reverseDirectionIntention();
        // Still right (it does not turn, it just changes to right once and for all
        assertEquals(blinky.getOutOfPenDirection(), Direction.RIGHT);
        // Same with get out
        blinky.setGhostPenState(GhostPenState.GET_OUT);
        blinky.setOutOfPenDirection(Direction.LEFT);
        blinky.reverseDirectionIntention();
        // Blinky is set to be in the ghost pen, its intention should not have been changed
        assertEquals(blinky.getIntention(), Direction.DOWN);
        // Its out of pen direction should have been set to right
        assertEquals(blinky.getOutOfPenDirection(), Direction.RIGHT);
        blinky.reverseDirectionIntention();
        // Still right (it does not turn, it just changes to right once and for all
        assertEquals(blinky.getOutOfPenDirection(), Direction.RIGHT);
    }

    @Test
    public void testOutOfPenDirectionReverse2() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        board.startActors();
        // blinky is out, reversing its direction should not affect out of pen direction
        blinky.reverseDirectionIntention();
        assertEquals(blinky.getOutOfPenDirection(), Direction.LEFT);
    }

    @Test
    public void testBlinkyDotCounter() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        board.startActors();
        assertFalse(blinky.hasDotCounter());
        assertNull(blinky.getDotCounter());
    }

    @Test
    public void testChangeStateChaseFrightenDead() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        board.startActors();
        // We move a bit
        for (int i = 0; i < 8; i++) {
            board.nextFrame();
        }
        blinky.setGhostState(GhostState.FRIGHTENED);
        blinky.changeGhostState(GhostState.DEAD);
        // Should still be going left
        assertEquals(blinky.getIntention(), Direction.LEFT);
    }

    @Test
    public void testChangeStateChaseFrightenEndDead() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        board.startActors();
        // We move a bit
        for (int i = 0; i < 8; i++) {
            board.nextFrame();
        }
        blinky.setGhostState(GhostState.FRIGHTENED_END);
        blinky.changeGhostState(GhostState.DEAD);
        // Should still be going left
        assertEquals(blinky.getIntention(), Direction.LEFT);
    }

    /**
     * We test that when blinky is set to dead, it actually goes to the
     * ghost pen and gets realive and out
     *
     * @throws PacManException
     */
    @Test
    public void testDeadAndAlive() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Maze maze = board.getMaze();
        Configurations.blockGhosts(board.getMaze());
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        blinky.setGhostState(GhostState.SCATTER);
        board.startActors();
        // Let's move a bit
        for (int i = 0; i < 50; i++) {
            board.nextFrame();
        }
        // Now we change blinky to dead
        blinky.setGhostState(GhostState.DEAD);
        // Blinky is dead and should go for the pen entry
        // When reaching the pen entry, it should change to GET_IN mode
        while (blinky.getGhostPenState() == GhostPenState.OUT) {
            board.nextFrame();
        }

        assertEquals(blinky.getGhostPenState(), GhostPenState.GET_IN);
        assertEquals(blinky.getGhostState(), GhostState.DEAD);
        assertEquals(blinky.getX(), board.outPenXPosition());
        assertEquals(blinky.getY(), board.outPenYPosition());
        // Now blinky is entering the pen
        // At some point it should be in pen
        while (blinky.getGhostPenState() == GhostPenState.GET_IN) {
            board.nextFrame();
        }
        assertEquals(blinky.getX(), board.penGhostXPosition(GhostType.BLINKY));
        assertEquals(blinky.getY(), board.penGhostYPosition(GhostType.BLINKY));
        // AS Blinky gets out immediatly, it might be in IN or GET_OUT state
        // After one more frame, it should be alive and getting out
        board.nextFrame();
        assertEquals(blinky.getGhostPenState(), GhostPenState.GET_OUT);
        assertEquals(blinky.getGhostState(), GhostState.SCATTER);
        while (blinky.getGhostPenState() == GhostPenState.GET_OUT) {
            board.nextFrame();
        }
        assertEquals(blinky.getGhostPenState(), GhostPenState.OUT);
        assertEquals(blinky.getDirection(), blinky.getOutOfPenDirection());
        assertEquals(blinky.getX(), board.outPenXPosition());
        assertEquals(blinky.getY(), board.outPenYPosition());
    }

    /**
     * We test that when blinky is set to dead, it actually goes to the
     * ghost pen and gets realive
     * Test in the "Second life" case
     *
     * @throws PacManException
     */
    @Test
    public void testDeadAndAliveNewLife() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        board.initializeNewLife();
        Maze maze = board.getMaze();
        Configurations.blockGhosts(board.getMaze());
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        blinky.setGhostState(GhostState.SCATTER);
        board.startActors();
        // Let's move a bit
        for (int i = 0; i < 50; i++) {
            board.nextFrame();
        }
        // Now we change blinky to dead
        blinky.setGhostState(GhostState.DEAD);
        // Blinky is dead and should go for the pen entry
        // When reaching the pen entry, it should change to GET_IN mode
        while (blinky.getGhostPenState() == GhostPenState.OUT) {
            board.nextFrame();
        }

        assertEquals(blinky.getGhostPenState(), GhostPenState.GET_IN);
        assertEquals(blinky.getGhostState(), GhostState.DEAD);
        assertEquals(blinky.getX(), board.outPenXPosition());
        assertEquals(blinky.getY(), board.outPenYPosition());
        // Now blinky is entering the pen
        // At some point it should be in pen
        while (blinky.getGhostPenState() == GhostPenState.GET_IN) {
            board.nextFrame();
        }
        assertEquals(blinky.getX(), board.penGhostXPosition(GhostType.BLINKY));
        assertEquals(blinky.getY(), board.penGhostYPosition(GhostType.BLINKY));
        // AS Blinky gets out immediatly, it might be in IN or GET_OUT state
        // After one more frame, it should be alive and getting out
        board.nextFrame();
        assertEquals(blinky.getGhostPenState(), GhostPenState.GET_OUT);
        assertEquals(blinky.getGhostState(), GhostState.SCATTER);
        while (blinky.getGhostPenState() == GhostPenState.GET_OUT) {
            board.nextFrame();
        }
        assertEquals(blinky.getGhostPenState(), GhostPenState.OUT);
        assertEquals(blinky.getDirection(), blinky.getOutOfPenDirection());
        assertEquals(blinky.getX(), board.outPenXPosition());
        assertEquals(blinky.getY(), board.outPenYPosition());
    }

    /**
     * We test that the out of pen direction is actually used
     * when getting out of pen
     *
     * @throws PacManException
     */
    @Test
    public void testOutOfPenDirection() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Maze maze = board.getMaze();
        Configurations.blockGhosts(board.getMaze());
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        blinky.setGhostState(GhostState.SCATTER);
        board.startActors();
        // Let's move a bit
        for (int i = 0; i < 50; i++) {
            board.nextFrame();
        }
        // Now we change blinky to dead
        blinky.setGhostState(GhostState.DEAD);

        // Blinky goes go the pen and is put in GET_OUT mode
        while (blinky.getGhostPenState() != GhostPenState.GET_OUT) {
            board.nextFrame();
        }
        blinky.setOutOfPenDirection(Direction.RIGHT);
        while (blinky.getGhostPenState() != GhostPenState.OUT) {
            board.nextFrame();
        }
        assertEquals(blinky.getDirection(), Direction.RIGHT);
    }

    @Test
    public void testInitializeAfterNewLife() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Configurations.smallSquareWithPacman(board);
        board.startActors();
        while (board.getBoardState() != BoardState.LIFE_OVER) {
            board.nextFrame();
        }
        board.initializeNewLife();
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        assertEquals(blinky.getBoard(), board);
        assertEquals(blinky.getGhostType(), GhostType.BLINKY);
        assertEquals(blinky.getX(), 112);
        assertEquals(blinky.getY(), 115);
        assertEquals(blinky.getDirection(), Direction.LEFT);
        assertEquals(blinky.getGhostState(), GhostState.SCATTER);
        assertEquals(blinky.getGhostPenState(), GhostPenState.OUT);
    }

    @Test
    public void testInitializeAfterNewLevel() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        Configurations.blockGhosts(maze);
        // We remove all dots except one
        for (int i = 0; i < maze.getHeight(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                if (maze.getTile(i, j).hasDot() && (i != 26 || j != 12)) {
                    maze.setTile(i, j, maze.getTile(i, j).clearDot());
                }
            }
        }
        board.startActors();
        while (board.getBoardState() != BoardState.LEVEL_OVER) {
            board.nextFrame();
        }
        board.initializeNewLevel(2);
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        assertEquals(blinky.getBoard(), board);
        assertEquals(blinky.getGhostType(), GhostType.BLINKY);
        assertEquals(blinky.getX(), 112);
        assertEquals(blinky.getY(), 115);
        assertEquals(blinky.getDirection(), Direction.LEFT);
        assertEquals(blinky.getGhostState(), GhostState.SCATTER);
        assertEquals(blinky.getGhostPenState(), GhostPenState.OUT);
    }

    @Test
    public void testBlinkyInitialSpeed() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        assertEquals(board.getGhost(GhostType.BLINKY).getSpeed(), board.getLevelGhostSpeed());
        board.initializeNewLevel(4);
        assertEquals(board.getGhost(GhostType.BLINKY).getSpeed(), board.getLevelGhostSpeed());
    }

    @Test
    public void testBlinkySlow() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        // We change the NT in front of blinky into an SL
        maze.setTile(14, 12, Tile.SL);
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        Actor pacman = board.getPacMan();
        board.startActors();
        TilePosition nt = new TilePosition(14, 12);
        while (!blinky.getCurrentTile().equals(nt)) {
            board.nextFrame();
        }
        // When it arrives on the tile Blinky should go slow
        assertEquals(blinky.getSpeed(), board.getTunnelGhostSpeed());
    }

    @Test
    public void testBlinkySlowLevel4() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.initializeNewLevel(4);
        Maze maze = board.getMaze();
        // We change the NT in front of blinky into an SL
        maze.setTile(14, 12, Tile.SL);
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        board.startActors();
        TilePosition nt = new TilePosition(14, 12);
        while (!blinky.getCurrentTile().equals(nt)) {
            board.nextFrame();
        }
        // When it arrives on the tile Blinky should go slow
        assertEquals(blinky.getSpeed(), board.getTunnelGhostSpeed());
    }

    /**
     * We test that after being dead and alive again,
     * the ghost returns to normal speed
     * when getting out of pen
     *
     * @throws PacManException
     */
    @Test
    public void testOutOfPenSpeed() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Configurations.blockGhosts(board.getMaze());
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        blinky.setGhostState(GhostState.SCATTER);
        board.startActors();
        // Let's move a bit
        for (int i = 0; i < 50; i++) {
            board.nextFrame();
        }
        // Now we change blinky to dead
        blinky.setGhostState(GhostState.DEAD);

        // Blinky goes go the pen and is put in GET_OUT mode
        while (blinky.getGhostPenState() != GhostPenState.GET_OUT) {
            board.nextFrame();
        }
        blinky.setOutOfPenDirection(Direction.RIGHT);
        while (blinky.getGhostPenState() != GhostPenState.OUT) {
            board.nextFrame();
        }
        assertEquals(blinky.getSpeed(), board.getLevelGhostSpeed());
    }

    @Test
    public void testHighSpeed() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Maze maze = board.getMaze();
        Configurations.blockGhosts(board.getMaze());
        Ghost blinky = board.getGhost(GhostType.BLINKY);
        blinky.setGhostState(GhostState.SCATTER);
        blinky.setSpeed(1.5);
        board.startActors();
        while (blinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        // Blinky changes direction, it should be at the center of the tile
        assertEquals(blinky.getX() % Maze.TILE_WIDTH, Maze.TITLE_CENTER_X);
        assertEquals(blinky.getY() % Maze.TILE_HEIGHT, Maze.TITLE_CENTER_Y);
        while (blinky.getDirection() == Direction.DOWN) {
            board.nextFrame();
        }
        // Blinky changes direction, it should be at the center of the tile
        assertEquals(blinky.getX() % Maze.TILE_WIDTH, Maze.TITLE_CENTER_X);
        assertEquals(blinky.getY() % Maze.TILE_HEIGHT, Maze.TITLE_CENTER_Y);
        while (blinky.getDirection() == Direction.RIGHT) {
            board.nextFrame();
        }
        // Blinky changes direction, it should be at the center of the tile
        assertEquals(blinky.getX() % Maze.TILE_WIDTH, Maze.TITLE_CENTER_X);
        assertEquals(blinky.getY() % Maze.TILE_HEIGHT, Maze.TITLE_CENTER_Y);
        while (blinky.getDirection() == Direction.UP) {
            board.nextFrame();
        }
        // Blinky changes direction, it should be at the center of the tile
        assertEquals(blinky.getX() % Maze.TILE_WIDTH, Maze.TITLE_CENTER_X);
        assertEquals(blinky.getY() % Maze.TILE_HEIGHT, Maze.TITLE_CENTER_Y);
    }


}
