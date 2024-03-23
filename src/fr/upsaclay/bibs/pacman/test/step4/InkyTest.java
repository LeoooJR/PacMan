package fr.upsaclay.bibs.pacman.test.step4;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.*;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.BoardState;
import fr.upsaclay.bibs.pacman.model.board.Counter;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;
import fr.upsaclay.bibs.pacman.test.Configurations;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InkyTest {

    @Test
    public void testCreateInky() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        List<Ghost> ghosts = board.getGhosts();
        Ghost inky = ghosts.get(2);
        assertTrue(board.hasGhost(GhostType.INKY));
        assertEquals(board.getGhost(GhostType.INKY), inky);
    }

    @Test
    public void testDisableInky() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableGhost(GhostType.INKY);
        board.initialize();
        assertFalse(board.hasGhost(GhostType.INKY));
        assertNull(board.getGhost(GhostType.INKY));
    }

    @Test
    public void testSetGetXY() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Actor inky = board.getGhost(GhostType.INKY);
        inky.setPosition(3,3);
        assertEquals(inky.getX(), 3);
        assertEquals(inky.getY(), 3);
    }

    @Test
    public void testGetCurrentTile() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Actor inky = board.getGhost(GhostType.INKY);
        inky.setPosition(3,3);
        assertEquals(inky.getX(), 3);
        assertEquals(inky.getY(), 3);
        assertEquals(inky.getCurrentTile(), new TilePosition(0,0));
        inky.setPosition(120,115);
        assertEquals(inky.getCurrentTile(), new TilePosition(14,15));
    }

    @Test
    public void testInitialInkyValues() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        assertEquals(inky.getBoard(), board);
        assertEquals(inky.getGhostType(), GhostType.INKY);
        assertEquals(inky.getX(), 96);
        assertEquals(inky.getY(), 139);
        assertEquals(inky.getGhostState(), GhostState.SCATTER);
        assertEquals(inky.getGhostPenState(), GhostPenState.IN);
    }

    @Test
    public void testSetGetGhostState() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        inky.setGhostState(GhostState.CHASE);
        assertEquals(inky.getGhostState(), GhostState.CHASE);
    }

    @Test
    public void testSetGetGhostPenState() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        inky.setGhostPenState(GhostPenState.OUT);
        assertEquals(inky.getGhostPenState(), GhostPenState.OUT);
    }

    @Test
    public void testInkyScatterTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        Configurations.setGhostOut(inky);
        board.startActors();
        assertEquals(inky.getTarget(), new TilePosition(34, 27));
    }

    @Test void testInkyChaseTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        Configurations.setGhostOut(inky);
        inky.setGhostState(GhostState.CHASE);
        board.startActors();
        Actor pacman = board.getPacMan();
        Actor blinky = board.getGhost(GhostType.BLINKY);
        TilePosition pos = board.getMaze().getTilePosition(pacman.getX(), pacman.getY());
        TilePosition bpos = board.getMaze().getTilePosition(blinky.getX(), blinky.getY());
        // Pacman is in 26 14 moving left
        // Blinky is in 14 14
        // Inky should be line = 26 + (26 -14) = 38, col = 12 + (12 - 14) = 10
        assertEquals(inky.getTarget(), new TilePosition(38, 10));
        // When pacman and blinky move, inky's target moves also
        for(int i = 0; i < 8; i++) {
            board.nextFrame();
        }
        // Pacman is in 26 13 moving left
        // Blinky is in 14 13
        // Inky should be line = 26 + (26 -14) = 38, col = 11 + (11 - 13) = 9
        assertEquals(inky.getTarget(), new TilePosition(38, 9));
        // If Pacman changes direction, inky's target changes
        pacman.setDirection(Direction.RIGHT);
        // Inky should be line = 26 + (26 - 14) = 38, col = 15 + (15 - 13) = 17
        assertEquals(inky.getTarget(), new TilePosition(38, 17));
        pacman.setDirection(Direction.DOWN);
        // Inky should be line = 28 + (28 - 14) = 42, col = 13 + (13-13) = 13
        assertEquals(inky.getTarget(), new TilePosition(42, 13));
        // Special case for up direction
        pacman.setDirection(Direction.UP);
        // Inky should be line = 24 + (24 - 14) = 34, col = 11 + (11 - 13) = 9
        assertEquals(inky.getTarget(), new TilePosition(34, 9));
    }

    @Test
    public void testReverseDirectionIntentionOut() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        // We put Inky outsite the pen
        inky.setPosition(112,115);
        inky.setGhostPenState(GhostPenState.OUT);
        inky.setDirection(Direction.LEFT);
        board.startActors();
        inky.reverseDirectionIntention();
        assertEquals(inky.getDirection(), Direction.LEFT);
        assertEquals(inky.getIntention(), Direction.RIGHT);
        while (inky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(inky.getDirection(), Direction.RIGHT);
    }

    @Test
    public void testFrightenTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        Configurations.setGhostOut(inky);
        board.startActors();
        inky.setGhostState(GhostState.FRIGHTENED);
        // no target in frighten mode, should be null
        assertNull(inky.getTarget());
    }

    @Test
    public void testFrightenEndTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        Configurations.setGhostOut(inky);
        board.startActors();
        inky.setGhostState(GhostState.FRIGHTENED_END);
        // no target in frighten end mode, should be null
        assertNull(inky.getTarget());
    }

    @Test
    public void testChangeStateScatterFrightenOut() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        Configurations.setGhostOut(inky);
        board.startActors();
        inky.changeGhostState(GhostState.FRIGHTENED);
        assertEquals(inky.getGhostState(), GhostState.FRIGHTENED);
        assertEquals(inky.getIntention(), Direction.RIGHT);
        while (inky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(inky.getDirection(), Direction.RIGHT);
        inky.changeGhostState(GhostState.FRIGHTENED_END);
        assertEquals(inky.getGhostState(), GhostState.FRIGHTENED_END);
        assertEquals(inky.getIntention(), Direction.RIGHT); // No turning around
        inky.changeGhostState(GhostState.SCATTER);
        assertEquals(inky.getGhostState(), GhostState.SCATTER);
        assertEquals(inky.getIntention(), Direction.RIGHT); // No turning around
    }

    @Test
    public void testChangeStateChaseFrightenOut() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        Configurations.setGhostOut(inky);
        inky.setGhostState(GhostState.CHASE);
        board.startActors();
        inky.changeGhostState(GhostState.FRIGHTENED);
        assertEquals(inky.getGhostState(), GhostState.FRIGHTENED);
        assertEquals(inky.getIntention(), Direction.RIGHT);
        while (inky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(inky.getDirection(), Direction.RIGHT);
        inky.changeGhostState(GhostState.FRIGHTENED_END);
        assertEquals(inky.getGhostState(), GhostState.FRIGHTENED_END);
        assertEquals(inky.getIntention(), Direction.RIGHT); // No turning around
        inky.changeGhostState(GhostState.CHASE);
        assertEquals(inky.getGhostState(), GhostState.CHASE);
        assertEquals(inky.getIntention(), Direction.RIGHT); // No turning around
    }

    @Test
    public void testMovingFrighten() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Configurations.blockGhosts(board.getMaze());
        Ghost inky = board.getGhost(GhostType.INKY);
        Configurations.setGhostOut(inky);
        board.startActors();
        inky.setGhostState(GhostState.FRIGHTENED);
        // We have blocked the ghost, so it can only go in square
        while (inky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(inky.getCurrentTile(), new TilePosition(14, 9));
        assertEquals(inky.getDirection(), Direction.DOWN);
        while (inky.getDirection() == Direction.DOWN) {
            board.nextFrame();
        }
        assertEquals(inky.getCurrentTile(), new TilePosition(20, 9));
        assertEquals(inky.getDirection(), Direction.RIGHT);
        while (inky.getDirection() == Direction.RIGHT) {
            board.nextFrame();
        }
        assertEquals(inky.getCurrentTile(), new TilePosition(20, 18));
        assertEquals(inky.getDirection(), Direction.UP);
        while (inky.getDirection() == Direction.UP) {
            board.nextFrame();
        }
        assertEquals(inky.getCurrentTile(), new TilePosition(14, 18));
        assertEquals(inky.getDirection(), Direction.LEFT);
    }

    @Test
    public void testMovingFrightenEnd() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Configurations.blockGhosts(board.getMaze());
        Ghost inky = board.getGhost(GhostType.INKY);
        Configurations.setGhostOut(inky);
        board.startActors();
        inky.setGhostState(GhostState.FRIGHTENED_END);
        // We have blocked the ghost, so it can only go in square
        while (inky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(inky.getCurrentTile(), new TilePosition(14, 9));
        assertEquals(inky.getDirection(), Direction.DOWN);
        while (inky.getDirection() == Direction.DOWN) {
            board.nextFrame();
        }
        assertEquals(inky.getCurrentTile(), new TilePosition(20, 9));
        assertEquals(inky.getDirection(), Direction.RIGHT);
        while (inky.getDirection() == Direction.RIGHT) {
            board.nextFrame();
        }
        assertEquals(inky.getCurrentTile(), new TilePosition(20, 18));
        assertEquals(inky.getDirection(), Direction.UP);
        while (inky.getDirection() == Direction.UP) {
            board.nextFrame();
        }
        assertEquals(inky.getCurrentTile(), new TilePosition(14, 18));
        assertEquals(inky.getDirection(), Direction.LEFT);
    }

    @Test
    public void testDeadTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        Configurations.setGhostOut(inky);
        board.startActors();
        inky.setGhostState(GhostState.DEAD);
        assertEquals(inky.getTarget(), board.penEntry());
    }

    @Test
    public void testSetGetOutOfPenDirection() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        assertEquals(inky.getOutOfPenDirection(), Direction.LEFT);
        inky.setOutOfPenDirection(Direction.RIGHT);
        assertEquals(inky.getOutOfPenDirection(), Direction.RIGHT);
    }

    @Test
    public void testOutOfPenDirectionReverse() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        board.startActors();
        inky.setDirection(Direction.DOWN);
        inky.setIntention(Direction.DOWN);
        inky.reverseDirectionIntention();
        // Inky is set to be in the ghost pen, its intention should not have been changed
        assertEquals(inky.getIntention(), Direction.DOWN);
        // Its out of pen direction should have been set to right
        assertEquals(inky.getOutOfPenDirection(), Direction.RIGHT);
        inky.reverseDirectionIntention();
        // Still right (it does not turn, it just changes to right once and for all
        assertEquals(inky.getOutOfPenDirection(), Direction.RIGHT);
        // Same with get in
        inky.setGhostPenState(GhostPenState.GET_IN);
        inky.setOutOfPenDirection(Direction.LEFT);
        inky.reverseDirectionIntention();
        // Inky is set to be in the ghost pen, its intention should not have been changed
        assertEquals(inky.getIntention(), Direction.DOWN);
        // Its out of pen direction should have been set to right
        assertEquals(inky.getOutOfPenDirection(), Direction.RIGHT);
        inky.reverseDirectionIntention();
        // Still right (it does not turn, it just changes to right once and for all
        assertEquals(inky.getOutOfPenDirection(), Direction.RIGHT);
        // Same with get out
        inky.setGhostPenState(GhostPenState.GET_OUT);
        inky.setOutOfPenDirection(Direction.LEFT);
        inky.reverseDirectionIntention();
        // Inky is set to be in the ghost pen, its intention should not have been changed
        assertEquals(inky.getIntention(), Direction.DOWN);
        // Its out of pen direction should have been set to right
        assertEquals(inky.getOutOfPenDirection(), Direction.RIGHT);
        inky.reverseDirectionIntention();
        // Still right (it does not turn, it just changes to right once and for all
        assertEquals(inky.getOutOfPenDirection(), Direction.RIGHT);
    }

    @Test
    public void testOutOfPenDirectionReverse2() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        Configurations.setGhostOut(inky);
        board.startActors();
        // inky is out, reversing its direction should not affect out of pen direction
        inky.reverseDirectionIntention();
        assertEquals(inky.getOutOfPenDirection(), Direction.LEFT);
    }

    @Test
    public void testInkyDotCounterLevel1() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        board.startActors();
        assertTrue(inky.hasDotCounter());
        Counter counter = inky.getDotCounter();
        assertEquals(counter.getLimit(), 30);
    }

    @Test
    public void testInkyDotCounterLevels2Up() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.initializeNewLevel(2);
        Ghost inky = board.getGhost(GhostType.INKY);
        board.startActors();
        assertTrue(inky.hasDotCounter());
        Counter counter = inky.getDotCounter();
        assertEquals(counter.getLimit(), 0);
        board.initializeNewLevel(19);
        counter = inky.getDotCounter();
        assertEquals(counter.getLimit(), 0);
    }

    @Test
    public void testCounterLevelReset() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        board.startActors();
        assertTrue(inky.hasDotCounter());
        Counter counter = inky.getDotCounter();
        counter.inc();
        counter.inc();
        board.initializeNewLevel(2);
        counter = inky.getDotCounter();
        assertEquals(counter.getValue(), 0);
    }

    @Test
    public void testCounterNewLifeNotReset() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        board.startActors();
        assertTrue(inky.hasDotCounter());
        Counter counter = inky.getDotCounter();
        counter.inc();
        counter.inc();
        board.initializeNewLife();
        counter = inky.getDotCounter();
        assertEquals(counter.getValue(), 2);
    }

    @Test
    public void testChangeStateChaseFrightenDead() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        Configurations.setGhostOut(inky);
        board.startActors();
        // We move a bit
        for (int i = 0; i < 8; i ++) {
            board.nextFrame();
        }
        inky.setGhostState(GhostState.FRIGHTENED);
        inky.changeGhostState(GhostState.DEAD);
        // Should still be going left
        assertEquals(inky.getIntention(), Direction.LEFT);
    }

    @Test
    public void testChangeStateChaseFrightenEndDead() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Ghost inky = board.getGhost(GhostType.INKY);
        Configurations.setGhostOut(inky);
        board.startActors();
        // We move a bit
        for (int i = 0; i < 8; i ++) {
            board.nextFrame();
        }
        inky.setGhostState(GhostState.FRIGHTENED_END);
        inky.changeGhostState(GhostState.DEAD);
        // Should still be going left
        assertEquals(inky.getIntention(), Direction.LEFT);
    }

    /**
     * We test that Inky actually leaves the ghost pen at the beginning of the game
     * @throws PacManException
     */
    @Test
    public void testInkyGetsOut() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        Ghost inky = board.getGhost(GhostType.INKY);
        board.startActors();
        assertEquals(inky.getGhostPenState(), GhostPenState.IN);
        board.nextFrame();
        // We increase inky's dot counter
        while (!inky.getDotCounter().hasReachedLimit()) {
            inky.getDotCounter().inc();
        }
        board.nextFrame();
        assertEquals(inky.getGhostPenState(), GhostPenState.GET_OUT);
        while (inky.getGhostPenState() == GhostPenState.GET_OUT) {
            board.nextFrame();
        }
        assertEquals(inky.getGhostPenState(), GhostPenState.OUT);
        assertEquals(inky.getX(), board.outPenXPosition());
        assertEquals(inky.getY(), board.outPenYPosition());
        assertEquals(inky.getDirection(), inky.getOutOfPenDirection());
    }

    /** We test that when inky is set to dead, it actually goes to the
     * ghost pen and gets realive and out
     * @throws PacManException
     */
    @Test
    public void testDeadAndAlive() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Maze maze = board.getMaze();
        Configurations.blockGhosts(board.getMaze());
        Ghost inky = board.getGhost(GhostType.INKY);
        Configurations.setGhostOut(inky);
        inky.setGhostState(GhostState.SCATTER);
        board.startActors();
        // Let's move a bit
        for(int i =0; i <50; i++) {
            board.nextFrame();
        }
        // Now we change inky to dead
        inky.setGhostState(GhostState.DEAD);
        // Inky is dead and should go for the pen entry
        // When reaching the pen entry, it should change to GET_IN mode
        while (inky.getGhostPenState() == GhostPenState.OUT) {
            board.nextFrame();
        }

        assertEquals(inky.getGhostPenState(), GhostPenState.GET_IN);
        assertEquals(inky.getGhostState(), GhostState.DEAD);
        assertEquals(inky.getX(), board.outPenXPosition());
        assertEquals(inky.getY(), board.outPenYPosition());
        // Now inky is entering the pen
        // At some point it should be in pen
        while (inky.getGhostPenState() == GhostPenState.GET_IN) {
            board.nextFrame();
        }
        assertEquals(inky.getX(), board.penGhostXPosition(GhostType.INKY));
        assertEquals(inky.getY(), board.penGhostYPosition(GhostType.INKY));
        assertEquals(inky.getGhostPenState(), GhostPenState.IN);
        board.nextFrame();
        // Inky should be realive
        assertEquals(inky.getGhostState(), GhostState.SCATTER);
        // We increase inky's dot counter
        while (! inky.getDotCounter().hasReachedLimit()) {
            inky.getDotCounter().inc();
        }
        board.nextFrame();
        assertEquals(inky.getGhostPenState(), GhostPenState.GET_OUT);
        while (inky.getGhostPenState() == GhostPenState.GET_OUT) {
            board.nextFrame();
        }
        assertEquals(inky.getGhostPenState(), GhostPenState.OUT);
        assertEquals(inky.getDirection(), inky.getOutOfPenDirection());
        assertEquals(inky.getX(), board.outPenXPosition());
        assertEquals(inky.getY(), board.outPenYPosition());
    }

    /**
     * We test that the out of pen direction is actually used
     * when getting out of pen
     * @throws PacManException
     */
    @Test
    public void testOutOfPenDirection() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Maze maze = board.getMaze();
        Configurations.blockGhosts(board.getMaze());
        Ghost inky = board.getGhost(GhostType.INKY);
        inky.setGhostState(GhostState.SCATTER);
        board.startActors();
        // Inky is in the pen and is put in GET_OUT mode
        while (inky.getGhostPenState() != GhostPenState.GET_OUT) {
            board.nextFrame();
        }
        inky.setOutOfPenDirection(Direction.RIGHT);
        while (inky.getGhostPenState() != GhostPenState.OUT) {
            board.nextFrame();
        }
        assertEquals(inky.getDirection(), Direction.RIGHT);
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
        Ghost inky = board.getGhost(GhostType.INKY);
        assertEquals(inky.getBoard(), board);
        assertEquals(inky.getGhostType(), GhostType.INKY);
        assertEquals(inky.getX(), 96);
        assertEquals(inky.getY(), 139);
        assertEquals(inky.getGhostState(), GhostState.SCATTER);
        assertEquals(inky.getGhostPenState(), GhostPenState.IN);
    }

    @Test
    public void testInitializeAfterNewLevel() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        Configurations.blockGhosts(maze);
        Configurations.setGhostOut(board.getGhost(GhostType.INKY));
        // We remove all dots except one
        for(int i =0; i < maze.getHeight(); i++) {
            for(int j=0; j < maze.getWidth(); j++) {
                if(maze.getTile(i,j).hasDot() && (i != 26 || j != 12)) {
                    maze.setTile(i,j,maze.getTile(i,j).clearDot());
                }
            }
        }
        board.startActors();
        while (board.getBoardState() != BoardState.LEVEL_OVER) {
            board.nextFrame();
        }
        board.initializeNewLevel(2);
        Ghost inky = board.getGhost(GhostType.INKY);
        assertEquals(inky.getBoard(), board);
        assertEquals(inky.getGhostType(), GhostType.INKY);
        assertEquals(inky.getX(), 96);
        assertEquals(inky.getY(), 139);
        assertEquals(inky.getGhostState(), GhostState.SCATTER);
        assertEquals(inky.getGhostPenState(), GhostPenState.IN);
    }

    @Test
    public void testInkyInitialSpeed() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        // Inky starts inside the ghost pen it should go at tunnel speed
        assertEquals(board.getGhost(GhostType.INKY).getSpeed(), board.getTunnelGhostSpeed());
        board.initializeNewLevel(4);
        assertEquals(board.getGhost(GhostType.INKY).getSpeed(), board.getTunnelGhostSpeed());
    }

    @Test
    public void testInkyOutThenSlow() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Maze maze = board.getMaze();
        Configurations.blockGhosts(maze);
        Ghost inky = board.getGhost(GhostType.INKY);
        inky.setGhostState(GhostState.SCATTER);
        // We change the NT just left outside of the pen into an SL
        maze.setTile(14,12, Tile.SL);
        board.startActors();
        TilePosition nt = new TilePosition(14,12);
        while (inky.getGhostPenState() != GhostPenState.OUT) {
            board.nextFrame();
        }
        assertEquals(inky.getSpeed(), board.getLevelGhostSpeed());
        while (!inky.getCurrentTile().equals(nt)) {
            board.nextFrame();
        }
        // When it arrives on the tile inky should go slow
        assertEquals(inky.getSpeed(), board.getTunnelGhostSpeed());
    }

    @Test
    public void testInkyOutThenSlowLevel4() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        board.initializeNewLevel(4);
        Maze maze = board.getMaze();
        Configurations.blockGhosts(maze);
        Ghost inky = board.getGhost(GhostType.INKY);
        inky.setGhostState(GhostState.SCATTER);
        // We change the NT just left outside of the pen into an SL
        maze.setTile(14,12, Tile.SL);
        board.startActors();
        TilePosition nt = new TilePosition(14,12);
        while (inky.getGhostPenState() != GhostPenState.OUT) {
            board.nextFrame();
        }
        assertEquals(inky.getSpeed(), board.getLevelGhostSpeed());
        while (!inky.getCurrentTile().equals(nt)) {
            board.nextFrame();
        }
        // When it arrives on the tile inky should go slow
        assertEquals(inky.getSpeed(), board.getTunnelGhostSpeed());
    }

    @Test
    public void testHighSpeed() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Maze maze = board.getMaze();
        Configurations.blockGhosts(board.getMaze());
        Ghost inky = board.getGhost(GhostType.INKY);
        Configurations.setGhostOut(inky);
        inky.setGhostState(GhostState.SCATTER);
        inky.setSpeed(1.5);
        board.startActors();
        while (inky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        // inky changes direction, it should be at the center of the tile
        assertEquals(inky.getX()%Maze.TILE_WIDTH, Maze.TITLE_CENTER_X);
        assertEquals(inky.getY()%Maze.TILE_HEIGHT, Maze.TITLE_CENTER_Y);
        while (inky.getDirection() == Direction.DOWN) {
            board.nextFrame();
        }
        // inky changes direction, it should be at the center of the tile
        assertEquals(inky.getX()%Maze.TILE_WIDTH, Maze.TITLE_CENTER_X);
        assertEquals(inky.getY()%Maze.TILE_HEIGHT, Maze.TITLE_CENTER_Y);
        while (inky.getDirection() == Direction.RIGHT) {
            board.nextFrame();
        }
        // inky changes direction, it should be at the center of the tile
        assertEquals(inky.getX()%Maze.TILE_WIDTH, Maze.TITLE_CENTER_X);
        assertEquals(inky.getY()%Maze.TILE_HEIGHT, Maze.TITLE_CENTER_Y);
        while (inky.getDirection() == Direction.UP) {
            board.nextFrame();
        }
        // inky changes direction, it should be at the center of the tile
        assertEquals(inky.getX()%Maze.TILE_WIDTH, Maze.TITLE_CENTER_X);
        assertEquals(inky.getY()%Maze.TILE_HEIGHT, Maze.TITLE_CENTER_Y);
    }
}
