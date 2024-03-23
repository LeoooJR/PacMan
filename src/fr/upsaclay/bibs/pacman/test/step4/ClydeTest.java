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

public class ClydeTest {

    @Test
    public void testCreateClyde() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        List<Ghost> ghosts = board.getGhosts();
        Ghost clyde = ghosts.get(3);
        assertTrue(board.hasGhost(GhostType.CLYDE));
        assertEquals(board.getGhost(GhostType.CLYDE), clyde);
    }

    @Test
    public void testDisableClyde() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableGhost(GhostType.CLYDE);
        board.initialize();
        assertFalse(board.hasGhost(GhostType.CLYDE));
        assertNull(board.getGhost(GhostType.CLYDE));
    }

    @Test
    public void testSetGetXY() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Actor clyde = board.getGhost(GhostType.CLYDE);
        clyde.setPosition(3,3);
        assertEquals(clyde.getX(), 3);
        assertEquals(clyde.getY(), 3);
    }

    @Test
    public void testGetCurrentTile() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Actor clyde = board.getGhost(GhostType.CLYDE);
        clyde.setPosition(3,3);
        assertEquals(clyde.getX(), 3);
        assertEquals(clyde.getY(), 3);
        assertEquals(clyde.getCurrentTile(), new TilePosition(0,0));
        clyde.setPosition(120,115);
        assertEquals(clyde.getCurrentTile(), new TilePosition(14,15));
    }

    @Test
    public void testInitialClydeValues() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        assertEquals(clyde.getBoard(), board);
        assertEquals(clyde.getGhostType(), GhostType.CLYDE);
        assertEquals(clyde.getX(), 128);
        assertEquals(clyde.getY(), 139);
        assertEquals(clyde.getGhostState(), GhostState.SCATTER);
        assertEquals(clyde.getGhostPenState(), GhostPenState.IN);
    }

    @Test
    public void testSetGetGhostState() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        clyde.setGhostState(GhostState.CHASE);
        assertEquals(clyde.getGhostState(), GhostState.CHASE);
    }

    @Test
    public void testSetGetGhostPenState() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        clyde.setGhostPenState(GhostPenState.OUT);
        assertEquals(clyde.getGhostPenState(), GhostPenState.OUT);
    }

    @Test
    public void testClydeScatterTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        Configurations.setGhostOut(clyde);
        board.startActors();
        assertEquals(clyde.getTarget(), new TilePosition(34, 0));
    }

    @Test void testClydeChaseTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        Configurations.setGhostOut(clyde);
        clyde.setGhostState(GhostState.CHASE);
        board.startActors();
        // Pacman is in 26 14
        // Clyde is in 14 14
        // distance is 12 >= 8
        // target should be pacman
        assertEquals(clyde.getTarget(), new TilePosition(26, 14));
        // When pacman moves, clyde target moves also
        for(int i = 0; i < 8; i++) {
            board.nextFrame();
        }
        // PacMan is in 26 13
        assertEquals(clyde.getTarget(), new TilePosition(26, 13));
        // Now let's put pacman close
        Actor pacman = board.getPacMan();
        pacman.setPosition(112, 163);
        // Pacman is in 20 14
        // Clyde is in 14 13
        // distance square is 6*6 + 1*1 = 37 <= 64
        // Target should be usual scatter target
        assertEquals(clyde.getTarget(), new TilePosition(34, 0));
    }

    @Test
    public void testReverseDirectionIntentionOut() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        // We put Clyde outsite the pen
        clyde.setPosition(112,115);
        clyde.setGhostPenState(GhostPenState.OUT);
        clyde.setDirection(Direction.LEFT);
        board.startActors();
        clyde.reverseDirectionIntention();
        assertEquals(clyde.getDirection(), Direction.LEFT);
        assertEquals(clyde.getIntention(), Direction.RIGHT);
        while (clyde.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(clyde.getDirection(), Direction.RIGHT);
    }

    @Test
    public void testFrightenTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        Configurations.setGhostOut(clyde);
        board.startActors();
        clyde.setGhostState(GhostState.FRIGHTENED);
        // no target in frighten mode, should be null
        assertNull(clyde.getTarget());
    }

    @Test
    public void testFrightenEndTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        Configurations.setGhostOut(clyde);
        board.startActors();
        clyde.setGhostState(GhostState.FRIGHTENED_END);
        // no target in frighten end mode, should be null
        assertNull(clyde.getTarget());
    }

    @Test
    public void testChangeStateScatterFrightenOut() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        Configurations.setGhostOut(clyde);
        board.startActors();
        clyde.changeGhostState(GhostState.FRIGHTENED);
        assertEquals(clyde.getGhostState(), GhostState.FRIGHTENED);
        assertEquals(clyde.getIntention(), Direction.RIGHT);
        while (clyde.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(clyde.getDirection(), Direction.RIGHT);
        clyde.changeGhostState(GhostState.FRIGHTENED_END);
        assertEquals(clyde.getGhostState(), GhostState.FRIGHTENED_END);
        assertEquals(clyde.getIntention(), Direction.RIGHT); // No turning around
        clyde.changeGhostState(GhostState.SCATTER);
        assertEquals(clyde.getGhostState(), GhostState.SCATTER);
        assertEquals(clyde.getIntention(), Direction.RIGHT); // No turning around
    }

    @Test
    public void testChangeStateChaseFrightenOut() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        Configurations.setGhostOut(clyde);
        clyde.setGhostState(GhostState.CHASE);
        board.startActors();
        clyde.changeGhostState(GhostState.FRIGHTENED);
        assertEquals(clyde.getGhostState(), GhostState.FRIGHTENED);
        assertEquals(clyde.getIntention(), Direction.RIGHT);
        while (clyde.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(clyde.getDirection(), Direction.RIGHT);
        clyde.changeGhostState(GhostState.FRIGHTENED_END);
        assertEquals(clyde.getGhostState(), GhostState.FRIGHTENED_END);
        assertEquals(clyde.getIntention(), Direction.RIGHT); // No turning around
        clyde.changeGhostState(GhostState.CHASE);
        assertEquals(clyde.getGhostState(), GhostState.CHASE);
        assertEquals(clyde.getIntention(), Direction.RIGHT); // No turning around
    }

    @Test
    public void testMovingFrighten() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        Configurations.blockGhosts(board.getMaze());
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        Configurations.setGhostOut(clyde);
        board.startActors();
        clyde.setGhostState(GhostState.FRIGHTENED);
        // We have blocked the ghost, so it can only go in square
        while (clyde.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(clyde.getCurrentTile(), new TilePosition(14, 9));
        assertEquals(clyde.getDirection(), Direction.DOWN);
        while (clyde.getDirection() == Direction.DOWN) {
            board.nextFrame();
        }
        assertEquals(clyde.getCurrentTile(), new TilePosition(20, 9));
        assertEquals(clyde.getDirection(), Direction.RIGHT);
        while (clyde.getDirection() == Direction.RIGHT) {
            board.nextFrame();
        }
        assertEquals(clyde.getCurrentTile(), new TilePosition(20, 18));
        assertEquals(clyde.getDirection(), Direction.UP);
        while (clyde.getDirection() == Direction.UP) {
            board.nextFrame();
        }
        assertEquals(clyde.getCurrentTile(), new TilePosition(14, 18));
        assertEquals(clyde.getDirection(), Direction.LEFT);
    }

    @Test
    public void testMovingFrightenEnd() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Configurations.blockGhosts(board.getMaze());
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        Configurations.setGhostOut(clyde);
        board.startActors();
        clyde.setGhostState(GhostState.FRIGHTENED_END);
        // We have blocked the ghost, so it can only go in square
        while (clyde.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(clyde.getCurrentTile(), new TilePosition(14, 9));
        assertEquals(clyde.getDirection(), Direction.DOWN);
        while (clyde.getDirection() == Direction.DOWN) {
            board.nextFrame();
        }
        assertEquals(clyde.getCurrentTile(), new TilePosition(20, 9));
        assertEquals(clyde.getDirection(), Direction.RIGHT);
        while (clyde.getDirection() == Direction.RIGHT) {
            board.nextFrame();
        }
        assertEquals(clyde.getCurrentTile(), new TilePosition(20, 18));
        assertEquals(clyde.getDirection(), Direction.UP);
        while (clyde.getDirection() == Direction.UP) {
            board.nextFrame();
        }
        assertEquals(clyde.getCurrentTile(), new TilePosition(14, 18));
        assertEquals(clyde.getDirection(), Direction.LEFT);
    }

    @Test
    public void testDeadTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        Configurations.setGhostOut(clyde);
        board.startActors();
        clyde.setGhostState(GhostState.DEAD);
        assertEquals(clyde.getTarget(), board.penEntry());
    }

    @Test
    public void testSetGetOutOfPenDirection() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        assertEquals(clyde.getOutOfPenDirection(), Direction.LEFT);
        clyde.setOutOfPenDirection(Direction.RIGHT);
        assertEquals(clyde.getOutOfPenDirection(), Direction.RIGHT);
    }

    @Test
    public void testOutOfPenDirectionReverse() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        board.startActors();
        clyde.setDirection(Direction.DOWN);
        clyde.setIntention(Direction.DOWN);
        clyde.reverseDirectionIntention();
        // Clyde is set to be in the ghost pen, its intention should not have been changed
        assertEquals(clyde.getIntention(), Direction.DOWN);
        // Its out of pen direction should have been set to right
        assertEquals(clyde.getOutOfPenDirection(), Direction.RIGHT);
        clyde.reverseDirectionIntention();
        // Still right (it does not turn, it just changes to right once and for all
        assertEquals(clyde.getOutOfPenDirection(), Direction.RIGHT);
        // Same with get in
        clyde.setGhostPenState(GhostPenState.GET_IN);
        clyde.setOutOfPenDirection(Direction.LEFT);
        clyde.reverseDirectionIntention();
        // Clyde is set to be in the ghost pen, its intention should not have been changed
        assertEquals(clyde.getIntention(), Direction.DOWN);
        // Its out of pen direction should have been set to right
        assertEquals(clyde.getOutOfPenDirection(), Direction.RIGHT);
        clyde.reverseDirectionIntention();
        // Still right (it does not turn, it just changes to right once and for all
        assertEquals(clyde.getOutOfPenDirection(), Direction.RIGHT);
        // Same with get out
        clyde.setGhostPenState(GhostPenState.GET_OUT);
        clyde.setOutOfPenDirection(Direction.LEFT);
        clyde.reverseDirectionIntention();
        // Clyde is set to be in the ghost pen, its intention should not have been changed
        assertEquals(clyde.getIntention(), Direction.DOWN);
        // Its out of pen direction should have been set to right
        assertEquals(clyde.getOutOfPenDirection(), Direction.RIGHT);
        clyde.reverseDirectionIntention();
        // Still right (it does not turn, it just changes to right once and for all
        assertEquals(clyde.getOutOfPenDirection(), Direction.RIGHT);
    }

    @Test
    public void testOutOfPenDirectionReverse2() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        Configurations.setGhostOut(clyde);
        board.startActors();
        // clyde is out, reversing its direction should not affect out of pen direction
        clyde.reverseDirectionIntention();
        assertEquals(clyde.getOutOfPenDirection(), Direction.LEFT);
    }

    @Test
    public void testClydeDotCounterLevel1() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        board.startActors();
        assertTrue(clyde.hasDotCounter());
        Counter counter = clyde.getDotCounter();
        assertEquals(counter.getLimit(), 60);
    }

    @Test
    public void testClydeDotCounterLevel2() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.initializeNewLevel(2);
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        board.startActors();
        assertTrue(clyde.hasDotCounter());
        Counter counter = clyde.getDotCounter();
        assertEquals(counter.getLimit(), 50);
    }

    @Test
    public void testClydeDotCounterLevels3Up() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        board.initializeNewLevel(3);
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        board.startActors();
        assertTrue(clyde.hasDotCounter());
        Counter counter = clyde.getDotCounter();
        assertEquals(counter.getLimit(), 0);
        board.initializeNewLevel(19);
        counter = clyde.getDotCounter();
        assertEquals(counter.getLimit(), 0);
    }

    @Test
    public void testCounterLevelReset() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        board.startActors();
        assertTrue(clyde.hasDotCounter());
        Counter counter = clyde.getDotCounter();
        counter.inc();
        counter.inc();
        board.initializeNewLevel(2);
        counter = clyde.getDotCounter();
        assertEquals(counter.getValue(), 0);
    }

    @Test
    public void testCounterNewLifeNotReset() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        board.startActors();
        assertTrue(clyde.hasDotCounter());
        Counter counter = clyde.getDotCounter();
        counter.inc();
        counter.inc();
        board.initializeNewLife();
        counter = clyde.getDotCounter();
        assertEquals(counter.getValue(), 2);
    }

    @Test
    public void testChangeStateChaseFrightenDead() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        Configurations.setGhostOut(clyde);
        board.startActors();
        // We move a bit
        for (int i = 0; i < 8; i ++) {
            board.nextFrame();
        }
        clyde.setGhostState(GhostState.FRIGHTENED);
        clyde.changeGhostState(GhostState.DEAD);
        // Should still be going left
        assertEquals(clyde.getIntention(), Direction.LEFT);
    }

    @Test
    public void testChangeStateChaseFrightenEndDead() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        Configurations.setGhostOut(clyde);
        board.startActors();
        // We move a bit
        for (int i = 0; i < 8; i ++) {
            board.nextFrame();
        }
        clyde.setGhostState(GhostState.FRIGHTENED_END);
        clyde.changeGhostState(GhostState.DEAD);
        // Should still be going left
        assertEquals(clyde.getIntention(), Direction.LEFT);
    }

    /**
     * We test that Clyde actually leaves the ghost pen at the beginning of the game
     * @throws PacManException
     */
    @Test
    public void testClydeGetsOut() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        board.startActors();
        assertEquals(clyde.getGhostPenState(), GhostPenState.IN);
        board.nextFrame();
        // We increase clyde's dot counter
        while (!clyde.getDotCounter().hasReachedLimit()) {
            clyde.getDotCounter().inc();
        }
        board.nextFrame();
        assertEquals(clyde.getGhostPenState(), GhostPenState.GET_OUT);
        while (clyde.getGhostPenState() == GhostPenState.GET_OUT) {
            board.nextFrame();
        }
        assertEquals(clyde.getGhostPenState(), GhostPenState.OUT);
        assertEquals(clyde.getX(), board.outPenXPosition());
        assertEquals(clyde.getY(), board.outPenYPosition());
        assertEquals(clyde.getDirection(), clyde.getOutOfPenDirection());
    }

    /** We test that when clyde is set to dead, it actually goes to the
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
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        Configurations.setGhostOut(clyde);
        clyde.setGhostState(GhostState.SCATTER);
        board.startActors();
        // Let's move a bit
        for(int i =0; i <50; i++) {
            board.nextFrame();
        }
        // Now we change clyde to dead
        clyde.setGhostState(GhostState.DEAD);
        // Clyde is dead and should go for the pen entry
        // When reaching the pen entry, it should change to GET_IN mode
        while (clyde.getGhostPenState() == GhostPenState.OUT) {
            board.nextFrame();
        }

        assertEquals(clyde.getGhostPenState(), GhostPenState.GET_IN);
        assertEquals(clyde.getGhostState(), GhostState.DEAD);
        assertEquals(clyde.getX(), board.outPenXPosition());
        assertEquals(clyde.getY(), board.outPenYPosition());
        // Now clyde is entering the pen
        // At some point it should be in pen
        while (clyde.getGhostPenState() == GhostPenState.GET_IN) {
            board.nextFrame();
        }
        assertEquals(clyde.getX(), board.penGhostXPosition(GhostType.CLYDE));
        assertEquals(clyde.getY(), board.penGhostYPosition(GhostType.CLYDE));
        assertEquals(clyde.getCurrentTile(), new TilePosition(17, 16));
        assertEquals(clyde.getGhostPenState(), GhostPenState.IN);
        board.nextFrame();
        // Clyde should be realive
        assertEquals(clyde.getGhostState(), GhostState.SCATTER);
        // We increase clyde's dot counter
        while (! clyde.getDotCounter().hasReachedLimit()) {
            clyde.getDotCounter().inc();
        }
        board.nextFrame();
        assertEquals(clyde.getGhostPenState(), GhostPenState.GET_OUT);
        while (clyde.getGhostPenState() == GhostPenState.GET_OUT) {
            board.nextFrame();
        }
        assertEquals(clyde.getGhostPenState(), GhostPenState.OUT);
        assertEquals(clyde.getDirection(), clyde.getOutOfPenDirection());
        assertEquals(clyde.getX(), board.outPenXPosition());
        assertEquals(clyde.getY(), board.outPenYPosition());
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
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        clyde.setGhostState(GhostState.SCATTER);
        board.startActors();
        // Clyde is in the pen and is put in GET_OUT mode
        while (clyde.getGhostPenState() != GhostPenState.GET_OUT) {
            board.nextFrame();
        }
        clyde.setOutOfPenDirection(Direction.RIGHT);
        while (clyde.getGhostPenState() != GhostPenState.OUT) {
            board.nextFrame();
        }
        assertEquals(clyde.getDirection(), Direction.RIGHT);
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
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        assertEquals(clyde.getBoard(), board);
        assertEquals(clyde.getGhostType(), GhostType.CLYDE);
        assertEquals(clyde.getX(), 128);
        assertEquals(clyde.getY(), 139);
        assertEquals(clyde.getGhostState(), GhostState.SCATTER);
        assertEquals(clyde.getGhostPenState(), GhostPenState.IN);
    }

    @Test
    public void testInitializeAfterNewLevel() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        Configurations.blockGhosts(maze);
        Configurations.setGhostOut(board.getGhost(GhostType.CLYDE));
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
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        assertEquals(clyde.getBoard(), board);
        assertEquals(clyde.getGhostType(), GhostType.CLYDE);
        assertEquals(clyde.getX(), 128);
        assertEquals(clyde.getY(), 139);
        assertEquals(clyde.getGhostState(), GhostState.SCATTER);
        assertEquals(clyde.getGhostPenState(), GhostPenState.IN);
    }

    @Test
    public void testClydeInitialSpeed() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        // Clyde starts inside the ghost pen it should go at tunnel speed
        assertEquals(board.getGhost(GhostType.CLYDE).getSpeed(), board.getTunnelGhostSpeed());
        board.initializeNewLevel(4);
        assertEquals(board.getGhost(GhostType.CLYDE).getSpeed(), board.getTunnelGhostSpeed());
    }

    @Test
    public void testClydeOutThenSlow() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Maze maze = board.getMaze();
        Configurations.blockGhosts(maze);
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        clyde.setGhostState(GhostState.SCATTER);
        // We change the NT just left outside of the pen into an SL
        maze.setTile(14,12, Tile.SL);
        board.startActors();
        TilePosition nt = new TilePosition(14,12);
        while (clyde.getGhostPenState() != GhostPenState.OUT) {
            board.nextFrame();
        }
        assertEquals(clyde.getSpeed(), board.getLevelGhostSpeed());
        while (!clyde.getCurrentTile().equals(nt)) {
            board.nextFrame();
        }
        // When it arrives on the tile clyde should go slow
        assertEquals(clyde.getSpeed(), board.getTunnelGhostSpeed());
    }

    @Test
    public void testClydeOutThenSlowLevel4() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        board.initializeNewLevel(4);
        Maze maze = board.getMaze();
        Configurations.blockGhosts(maze);
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        clyde.setGhostState(GhostState.SCATTER);
        // We change the NT just left outside of the pen into an SL
        maze.setTile(14,12, Tile.SL);
        board.startActors();
        TilePosition nt = new TilePosition(14,12);
        while (clyde.getGhostPenState() != GhostPenState.OUT) {
            board.nextFrame();
        }
        assertEquals(clyde.getSpeed(), board.getLevelGhostSpeed());
        while (!clyde.getCurrentTile().equals(nt)) {
            board.nextFrame();
        }
        // When it arrives on the tile clyde should go slow
        assertEquals(clyde.getSpeed(), board.getTunnelGhostSpeed());
    }

    @Test
    public void testHighSpeed() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Maze maze = board.getMaze();
        Configurations.blockGhosts(board.getMaze());
        Ghost clyde = board.getGhost(GhostType.CLYDE);
        Configurations.setGhostOut(clyde);
        clyde.setGhostState(GhostState.SCATTER);
        clyde.setSpeed(1.5);
        board.startActors();
        while (clyde.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        // clyde changes direction, it should be at the center of the tile
        assertEquals(clyde.getX()%Maze.TILE_WIDTH, Maze.TITLE_CENTER_X);
        assertEquals(clyde.getY()%Maze.TILE_HEIGHT, Maze.TITLE_CENTER_Y);
        while (clyde.getDirection() == Direction.DOWN) {
            board.nextFrame();
        }
        // clyde changes direction, it should be at the center of the tile
        assertEquals(clyde.getX()%Maze.TILE_WIDTH, Maze.TITLE_CENTER_X);
        assertEquals(clyde.getY()%Maze.TILE_HEIGHT, Maze.TITLE_CENTER_Y);
        while (clyde.getDirection() == Direction.RIGHT) {
            board.nextFrame();
        }
        // clyde changes direction, it should be at the center of the tile
        assertEquals(clyde.getX()%Maze.TILE_WIDTH, Maze.TITLE_CENTER_X);
        assertEquals(clyde.getY()%Maze.TILE_HEIGHT, Maze.TITLE_CENTER_Y);
        while (clyde.getDirection() == Direction.UP) {
            board.nextFrame();
        }
        // clyde changes direction, it should be at the center of the tile
        assertEquals(clyde.getX()%Maze.TILE_WIDTH, Maze.TITLE_CENTER_X);
        assertEquals(clyde.getY()%Maze.TILE_HEIGHT, Maze.TITLE_CENTER_Y);
    }
}
