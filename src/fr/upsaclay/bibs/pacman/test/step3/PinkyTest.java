package fr.upsaclay.bibs.pacman.test.step3;

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

public class PinkyTest {

    @Test
    public void testCreatePinky() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        List<Ghost> ghosts = board.getGhosts();
        Ghost pinky = ghosts.get(1);
        assertTrue(board.hasGhost(GhostType.PINKY));
        assertEquals(board.getGhost(GhostType.PINKY), pinky);
    }

    @Test
    public void testDisablePinky() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableGhost(GhostType.PINKY);
        board.initialize();
        assertFalse(board.hasGhost(GhostType.PINKY));
        assertNull(board.getGhost(GhostType.PINKY));
    }

    @Test
    public void testSetGetXY() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Actor pinky = board.getGhost(GhostType.PINKY);
        pinky.setPosition(3,3);
        assertEquals(pinky.getX(), 3);
        assertEquals(pinky.getY(), 3);
    }

    @Test
    public void testGetCurrentTile() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Actor pinky = board.getGhost(GhostType.PINKY);
        pinky.setPosition(3,3);
        assertEquals(pinky.getX(), 3);
        assertEquals(pinky.getY(), 3);
        assertEquals(pinky.getCurrentTile(), new TilePosition(0,0));
        pinky.setPosition(120,115);
        assertEquals(pinky.getCurrentTile(), new TilePosition(14,15));
    }

    @Test
    public void testInitialPinkyValues() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        assertEquals(pinky.getBoard(), board);
        assertEquals(pinky.getGhostType(), GhostType.PINKY);
        assertEquals(pinky.getX(), 112);
        assertEquals(pinky.getY(), 139);
        assertEquals(pinky.getGhostState(), GhostState.SCATTER);
        assertEquals(pinky.getGhostPenState(), GhostPenState.IN);
    }

    @Test
    public void testSetGetGhostState() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        pinky.setGhostState(GhostState.CHASE);
        assertEquals(pinky.getGhostState(), GhostState.CHASE);
    }

    @Test
    public void testSetGetGhostPenState() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        pinky.setGhostPenState(GhostPenState.OUT);
        assertEquals(pinky.getGhostPenState(), GhostPenState.OUT);
    }

    @Test
    public void testPinkyScatterTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        Configurations.setGhostOut(pinky);
        board.startActors();
        assertEquals(pinky.getTarget(), new TilePosition(0, 2));
    }

    @Test void testPinkyChaseTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        Configurations.setGhostOut(pinky);
        pinky.setGhostState(GhostState.CHASE);
        board.startActors();
        // Pacman is in 26 14
        assertEquals(pinky.getTarget(), new TilePosition(26, 10));
        System.out.println(STR."Pacman is in \{board.getPacMan().getCurrentTile()} Pinky target is \{pinky.getTarget()}");
        // When pacman moves, pinky target moves also
        for(int i = 0; i < 8; i++) {

            board.nextFrame();
            pinky.setGhostState(GhostState.CHASE);
            TilePosition target = pinky.getTarget();
            System.out.println(STR."Pacman is in \{board.getPacMan().getCurrentTile()} Pinky target is \{pinky.getTarget()}");
        }
        // PacMan is in 26 13
        TilePosition target = pinky.getTarget();

        assertEquals(pinky.getTarget(), new TilePosition(26, 9));
        // If Pacman changes direction, pinky's target changes
        Actor pacman = board.getPacMan();
        pacman.setDirection(Direction.RIGHT);
        assertEquals(pinky.getTarget(), new TilePosition(26, 17));
        pacman.setDirection(Direction.DOWN);
        assertEquals(pinky.getTarget(), new TilePosition(30, 13));
        // Special case for up direction
        pacman.setDirection(Direction.UP);
        assertEquals(pinky.getTarget(), new TilePosition(22, 9));
    }

    @Test
    public void testReverseDirectionIntentionOut() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        // We put Pinky outsite the pen
        Configurations.setGhostOut(pinky);
        board.startActors();
        pinky.reverseDirectionIntention();
        assertEquals(pinky.getDirection(), Direction.LEFT);
        assertEquals(pinky.getIntention(), Direction.RIGHT);
        while (pinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(pinky.getDirection(), Direction.RIGHT);
    }

    @Test
    public void testFrightenTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        Configurations.setGhostOut(pinky);
        board.startActors();
        pinky.setGhostState(GhostState.FRIGHTENED);
        // no target in frighten mode, should be null
        assertNull(pinky.getTarget());
    }

    @Test
    public void testFrightenEndTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        Configurations.setGhostOut(pinky);
        board.startActors();
        pinky.setGhostState(GhostState.FRIGHTENED_END);
        // no target in frighten end mode, should be null
        assertNull(pinky.getTarget());
    }

    @Test
    public void testChangeStateScatterFrightenOut() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        Configurations.setGhostOut(pinky);
        board.startActors();
        pinky.changeGhostState(GhostState.FRIGHTENED);
        assertEquals(pinky.getGhostState(), GhostState.FRIGHTENED);
        assertEquals(pinky.getIntention(), Direction.RIGHT);
        while (pinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(pinky.getDirection(), Direction.RIGHT);
        pinky.changeGhostState(GhostState.FRIGHTENED_END);
        assertEquals(pinky.getGhostState(), GhostState.FRIGHTENED_END);
        assertEquals(pinky.getIntention(), Direction.RIGHT); // No turning around
        pinky.changeGhostState(GhostState.SCATTER);
        assertEquals(pinky.getGhostState(), GhostState.SCATTER);
        assertEquals(pinky.getIntention(), Direction.RIGHT); // No turning around
    }

    @Test
    public void testChangeStateChaseFrightenOut() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        Configurations.setGhostOut(pinky);
        pinky.setGhostState(GhostState.CHASE);
        board.startActors();
        pinky.changeGhostState(GhostState.FRIGHTENED);
        assertEquals(pinky.getGhostState(), GhostState.FRIGHTENED);
        assertEquals(pinky.getIntention(), Direction.RIGHT);
        while (pinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(pinky.getDirection(), Direction.RIGHT);
        pinky.changeGhostState(GhostState.FRIGHTENED_END);
        assertEquals(pinky.getGhostState(), GhostState.FRIGHTENED_END);
        assertEquals(pinky.getIntention(), Direction.RIGHT); // No turning around
        pinky.changeGhostState(GhostState.CHASE);
        assertEquals(pinky.getGhostState(), GhostState.CHASE);
        assertEquals(pinky.getIntention(), Direction.RIGHT); // No turning around
    }

    @Test
    public void testMovingFrighten() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Configurations.blockGhosts(board.getMaze());
        Ghost pinky = board.getGhost(GhostType.PINKY);
        Configurations.setGhostOut(pinky);
        board.startActors();
        pinky.setGhostState(GhostState.FRIGHTENED);
        // We have blocked the ghost, so it can only go in square
        while (pinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
            System.out.println(STR."Pinky is in \{pinky.getCurrentTile()} and is going \{pinky.getDirection()}");
        }
        assertEquals(pinky.getCurrentTile(), new TilePosition(14, 9));
        assertEquals(pinky.getDirection(), Direction.DOWN);
        while (pinky.getDirection() == Direction.DOWN) {
            board.nextFrame();
        }
        assertEquals(pinky.getCurrentTile(), new TilePosition(20, 9));
        assertEquals(pinky.getDirection(), Direction.RIGHT);
        while (pinky.getDirection() == Direction.RIGHT) {
            board.nextFrame();
        }
        assertEquals(pinky.getCurrentTile(), new TilePosition(20, 18));
        assertEquals(pinky.getDirection(), Direction.UP);
        while (pinky.getDirection() == Direction.UP) {
            board.nextFrame();
        }
        assertEquals(pinky.getCurrentTile(), new TilePosition(14, 18));
        assertEquals(pinky.getDirection(), Direction.LEFT);
    }

    @Test
    public void testMovingFrightenEnd() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Configurations.blockGhosts(board.getMaze());
        Ghost pinky = board.getGhost(GhostType.PINKY);
        Configurations.setGhostOut(pinky);
        board.startActors();
        pinky.setGhostState(GhostState.FRIGHTENED_END);
        // We have blocked the ghost, so it can only go in square
        while (pinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        assertEquals(pinky.getCurrentTile(), new TilePosition(14, 9));
        assertEquals(pinky.getDirection(), Direction.DOWN);
        while (pinky.getDirection() == Direction.DOWN) {
            board.nextFrame();
        }
        assertEquals(pinky.getCurrentTile(), new TilePosition(20, 9));
        assertEquals(pinky.getDirection(), Direction.RIGHT);
        while (pinky.getDirection() == Direction.RIGHT) {
            board.nextFrame();
        }
        assertEquals(pinky.getCurrentTile(), new TilePosition(20, 18));
        assertEquals(pinky.getDirection(), Direction.UP);
        while (pinky.getDirection() == Direction.UP) {
            board.nextFrame();
        }
        assertEquals(pinky.getCurrentTile(), new TilePosition(14, 18));
        assertEquals(pinky.getDirection(), Direction.LEFT);
    }

    @Test
    public void testDeadTarget() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        Configurations.setGhostOut(pinky);
        board.startActors();
        pinky.setGhostState(GhostState.DEAD);
        assertEquals(pinky.getTarget(), board.penEntry());
    }

    @Test
    public void testSetGetOutOfPenDirection() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        assertEquals(pinky.getOutOfPenDirection(), Direction.LEFT);
        pinky.setOutOfPenDirection(Direction.RIGHT);
        assertEquals(pinky.getOutOfPenDirection(), Direction.RIGHT);
    }

    @Test
    public void testOutOfPenDirectionReverse() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        board.startActors();
        pinky.setDirection(Direction.DOWN);
        pinky.setIntention(Direction.DOWN);
        pinky.reverseDirectionIntention();
        // Pinky is set to be in the ghost pen, its intention should not have been changed
        assertEquals(pinky.getIntention(), Direction.DOWN);
        // Its out of pen direction should have been set to right
        assertEquals(pinky.getOutOfPenDirection(), Direction.RIGHT);
        pinky.reverseDirectionIntention();
        // Still right (it does not turn, it just changes to right once and for all
        assertEquals(pinky.getOutOfPenDirection(), Direction.RIGHT);
        // Same with get in
        pinky.setGhostPenState(GhostPenState.GET_IN);
        pinky.setOutOfPenDirection(Direction.LEFT);
        pinky.reverseDirectionIntention();
        // Pinky is set to be in the ghost pen, its intention should not have been changed
        assertEquals(pinky.getIntention(), Direction.DOWN);
        // Its out of pen direction should have been set to right
        assertEquals(pinky.getOutOfPenDirection(), Direction.RIGHT);
        pinky.reverseDirectionIntention();
        // Still right (it does not turn, it just changes to right once and for all
        assertEquals(pinky.getOutOfPenDirection(), Direction.RIGHT);
        // Same with get out
        pinky.setGhostPenState(GhostPenState.GET_OUT);
        pinky.setOutOfPenDirection(Direction.LEFT);
        pinky.reverseDirectionIntention();
        // Pinky is set to be in the ghost pen, its intention should not have been changed
        assertEquals(pinky.getIntention(), Direction.DOWN);
        // Its out of pen direction should have been set to right
        assertEquals(pinky.getOutOfPenDirection(), Direction.RIGHT);
        pinky.reverseDirectionIntention();
        // Still right (it does not turn, it just changes to right once and for all
        assertEquals(pinky.getOutOfPenDirection(), Direction.RIGHT);
    }

    @Test
    public void testOutOfPenDirectionReverse2() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        Configurations.setGhostOut(pinky);
        board.startActors();
        // pinky is out, reversing its direction should not affect out of pen direction
        pinky.reverseDirectionIntention();
        assertEquals(pinky.getOutOfPenDirection(), Direction.LEFT);
    }

    @Test
    public void testPinkyDotCounter() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        board.startActors();
        assertTrue(pinky.hasDotCounter());
        Counter counter = pinky.getDotCounter();
        assertEquals(counter.getLimit(), 0);
    }

    @Test
    public void testChangeStateChaseFrightenDead() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        Configurations.setGhostOut(pinky);
        board.startActors();
        // We move a bit
        for (int i = 0; i < 8; i ++) {
            board.nextFrame();
        }
        pinky.setGhostState(GhostState.FRIGHTENED);
        pinky.changeGhostState(GhostState.DEAD);
        // Should still be going left
        assertEquals(pinky.getIntention(), Direction.LEFT);
    }

    @Test
    public void testChangeStateChaseFrightenEndDead() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        Configurations.setGhostOut(pinky);
        board.startActors();
        // We move a bit
        for (int i = 0; i < 8; i ++) {
            board.nextFrame();
        }
        pinky.setGhostState(GhostState.FRIGHTENED_END);
        pinky.changeGhostState(GhostState.DEAD);
        // Should still be going left
        assertEquals(pinky.getIntention(), Direction.LEFT);
    }

    /**
     * We test that Pinky actually leaves the ghost pen at the beginning of the game
     * @throws PacManException
     */
    @Test
    public void testPinkyGetsOut() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        Ghost pinky = board.getGhost(GhostType.PINKY);
        board.startActors();
        assertEquals(pinky.getGhostPenState(), GhostPenState.IN);
        board.nextFrame();
        // Pinky's dot counter has a zero limit. Pinky should get out immeditaly
        assertEquals(pinky.getGhostPenState(), GhostPenState.GET_OUT);
        while (pinky.getGhostPenState() == GhostPenState.GET_OUT) {
            board.nextFrame();
        }
        assertEquals(pinky.getGhostPenState(), GhostPenState.OUT);
        assertEquals(pinky.getX(), board.outPenXPosition());
        assertEquals(pinky.getY(), board.outPenYPosition());
        assertEquals(pinky.getDirection(), pinky.getOutOfPenDirection());
    }

    /** We test that when pinky is set to dead, it actually goes to the
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
        Ghost pinky = board.getGhost(GhostType.PINKY);
        Configurations.setGhostOut(pinky);
        pinky.setGhostState(GhostState.SCATTER);
        board.startActors();
        // Let's move a bit
        for(int i =0; i <50; i++) {
            board.nextFrame();
        }
        // Now we change pinky to dead
        pinky.setGhostState(GhostState.DEAD);
        // Pinky is dead and should go for the pen entry
        // When reaching the pen entry, it should change to GET_IN mode
        while (pinky.getGhostPenState() == GhostPenState.OUT) {
            board.nextFrame();
        }

        assertEquals(pinky.getGhostPenState(), GhostPenState.GET_IN);
        assertEquals(pinky.getGhostState(), GhostState.DEAD);
        assertEquals(pinky.getX(), board.outPenXPosition());
        assertEquals(pinky.getY(), board.outPenYPosition());
        // Now pinky is entering the pen
        // At some point it should be in pen
        while (pinky.getGhostPenState() == GhostPenState.GET_IN) {
            board.nextFrame();
        }
        assertEquals(pinky.getX(), board.penGhostXPosition(GhostType.PINKY));
        assertEquals(pinky.getY(), board.penGhostYPosition(GhostType.PINKY));
        // AS Pinky gets out immediatly, it might be in IN or GET_OUT state
        // After one more frame, it should be alive and getting out
        board.nextFrame();
        assertEquals(pinky.getGhostPenState(), GhostPenState.GET_OUT);
        assertEquals(pinky.getGhostState(), GhostState.SCATTER);
        while (pinky.getGhostPenState() == GhostPenState.GET_OUT) {
            board.nextFrame();
        }
        assertEquals(pinky.getGhostPenState(), GhostPenState.OUT);
        assertEquals(pinky.getDirection(), pinky.getOutOfPenDirection());
        assertEquals(pinky.getX(), board.outPenXPosition());
        assertEquals(pinky.getY(), board.outPenYPosition());
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
        Ghost pinky = board.getGhost(GhostType.PINKY);
        pinky.setGhostState(GhostState.SCATTER);
        board.startActors();
        // Pinky is in the pen and is put in GET_OUT mode
        while (pinky.getGhostPenState() != GhostPenState.GET_OUT) {
            board.nextFrame();
        }
        pinky.setOutOfPenDirection(Direction.RIGHT);
        while (pinky.getGhostPenState() != GhostPenState.OUT) {
            board.nextFrame();
        }
        assertEquals(pinky.getDirection(), Direction.RIGHT);
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
        Ghost pinky = board.getGhost(GhostType.PINKY);
        assertEquals(pinky.getBoard(), board);
        assertEquals(pinky.getGhostType(), GhostType.PINKY);
        assertEquals(pinky.getX(), 112);
        assertEquals(pinky.getY(), 139);
        assertEquals(pinky.getGhostState(), GhostState.SCATTER);
        assertEquals(pinky.getGhostPenState(), GhostPenState.IN);
    }

    @Test
    public void testInitializeAfterNewLevel() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        Maze maze = board.getMaze();
        Configurations.blockGhosts(maze);
        Configurations.setGhostOut(board.getGhost(GhostType.PINKY));
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
        Ghost pinky = board.getGhost(GhostType.PINKY);
        assertEquals(pinky.getBoard(), board);
        assertEquals(pinky.getGhostType(), GhostType.PINKY);
        assertEquals(pinky.getX(), 112);
        assertEquals(pinky.getY(), 139);
        assertEquals(pinky.getGhostState(), GhostState.SCATTER);
        assertEquals(pinky.getGhostPenState(), GhostPenState.IN);
    }

    @Test
    public void testPinkyInitialSpeed() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.initialize();
        // Pinky starts inside the ghost pen it should go at tunnel speed
        int level = board.getLevel();
        System.out.println(STR."Level is \{level}");
        assertEquals(board.getGhost(GhostType.PINKY).getSpeed(), board.getTunnelGhostSpeed());
        board.initializeNewLevel(4);
        assertEquals(board.getGhost(GhostType.PINKY).getSpeed(), board.getTunnelGhostSpeed());
    }

    @Test
    public void testPinkyOutThenSlow() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Maze maze = board.getMaze();
        Configurations.blockGhosts(maze);
        Ghost pinky = board.getGhost(GhostType.PINKY);
        pinky.setGhostState(GhostState.SCATTER);
        // We change the NT just left outside of the pen into an SL
        maze.setTile(14,12, Tile.SL);
        board.startActors();
        TilePosition nt = new TilePosition(14,12);
        while (pinky.getGhostPenState() != GhostPenState.OUT) {
            board.nextFrame();
        }
        assertEquals(pinky.getSpeed(), board.getLevelGhostSpeed());
        while (!pinky.getCurrentTile().equals(nt)) {
            board.nextFrame();
        }
        // When it arrives on the tile pinky should go slow
        assertEquals(pinky.getSpeed(), board.getTunnelGhostSpeed());
    }

    @Test
    public void testPinkyOutThenSlowLevel4() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        board.initializeNewLevel(4);
        Maze maze = board.getMaze();
        Configurations.blockGhosts(maze);
        Ghost pinky = board.getGhost(GhostType.PINKY);
        pinky.setGhostState(GhostState.SCATTER);
        // We change the NT just left outside of the pen into an SL
        maze.setTile(14,12, Tile.SL);
        board.startActors();
        TilePosition nt = new TilePosition(14,12);
        while (pinky.getGhostPenState() != GhostPenState.OUT) {
            board.nextFrame();
        }
        assertEquals(pinky.getSpeed(), board.getLevelGhostSpeed());
        while (!pinky.getCurrentTile().equals(nt)) {
            board.nextFrame();
        }
        // When it arrives on the tile pinky should go slow
        assertEquals(pinky.getSpeed(), board.getTunnelGhostSpeed());
    }

    @Test
    public void testHighSpeed() throws PacManException {
        Board board = Board.createBoard(GameType.CLASSIC);
        board.disableStateTime();
        board.initialize();
        Maze maze = board.getMaze();
        Configurations.blockGhosts(board.getMaze());
        Ghost pinky = board.getGhost(GhostType.PINKY);
        Configurations.setGhostOut(pinky);
        pinky.setGhostState(GhostState.SCATTER);
        pinky.setSpeed(1.5);
        board.startActors();
        while (pinky.getDirection() == Direction.LEFT) {
            board.nextFrame();
        }
        // pinky changes direction, it should be at the center of the tile
        assertEquals(pinky.getX()%Maze.TILE_WIDTH, Maze.TITLE_CENTER_X);
        assertEquals(pinky.getY()%Maze.TILE_HEIGHT, Maze.TITLE_CENTER_Y);
        while (pinky.getDirection() == Direction.DOWN) {
            board.nextFrame();
        }
        // pinky changes direction, it should be at the center of the tile
        assertEquals(pinky.getX()%Maze.TILE_WIDTH, Maze.TITLE_CENTER_X);
        assertEquals(pinky.getY()%Maze.TILE_HEIGHT, Maze.TITLE_CENTER_Y);
        while (pinky.getDirection() == Direction.RIGHT) {
            board.nextFrame();
        }
        // pinky changes direction, it should be at the center of the tile
        assertEquals(pinky.getX()%Maze.TILE_WIDTH, Maze.TITLE_CENTER_X);
        assertEquals(pinky.getY()%Maze.TILE_HEIGHT, Maze.TITLE_CENTER_Y);
        while (pinky.getDirection() == Direction.UP) {
            board.nextFrame();
        }
        // pinky changes direction, it should be at the center of the tile
        assertEquals(pinky.getX()%Maze.TILE_WIDTH, Maze.TITLE_CENTER_X);
        assertEquals(pinky.getY()%Maze.TILE_HEIGHT, Maze.TITLE_CENTER_Y);
    }
}
