package fr.upsaclay.bibs.pacman.test.step1;

import static org.junit.jupiter.api.Assertions.*;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.control.Controller;
import fr.upsaclay.bibs.pacman.control.ForbiddenActionException;
import fr.upsaclay.bibs.pacman.control.GameAction;
import fr.upsaclay.bibs.pacman.control.InterfaceMode;
import org.junit.jupiter.api.Test;

public class SimpleControllerTest {

    /**
     * Test that the simple controller can be created without errors
     */
    @Test
    public void testGetController() {
        Controller simple = Controller.getController(InterfaceMode.SIMPLE);
        assertNotNull(simple);
    }

    /**
     * Test that the simple controller can be initialized after setting the
     * game type without errors
     * (depending on developer choice, the game type might not be necessary at
     * this step)
     * @throws PacManException if the initialization fails
     */
    @Test
    public void testInitialize() throws PacManException {
        Controller simple = Controller.getController(InterfaceMode.SIMPLE);
        simple.setGameType(GameType.TEST);
        simple.initialize();
    }

    /**
     * Test that the simple controller can be initialized with a new game
     * @throws PacManException if the initialization fails
     */
    @Test
    public void testInitializeNewGame() throws PacManException {
        Controller simple = Controller.getController(InterfaceMode.SIMPLE);
        simple.setGameType(GameType.TEST);
        simple.initializeNewGame();
        // The board should not be null
        assertNotNull(simple.getBoard());
    }

    /**
     * Test that we can ask the simple controller to start the game after
     * initialization
     * (edit this test if your controller requires extra / different steps
     * of initilaztion before starting)
     * @throws PacManException if initialization fails
     */
    @Test
    public void testStartGame() throws PacManException {
        Controller simple = Controller.getController(InterfaceMode.SIMPLE);
        simple.setGameType(GameType.TEST);
        simple.initialize();
        // in my implementation, initizalize calls initializeNewGame by default
        // simple.initializeNewGame();
        simple.receiveAction(GameAction.START);
    }

    /**
     * Test that Forbidden action exceptions are thrown if actions are required before
     * starting the game
     * @throws PacManException
     */
    @Test
    public void testExceptionActionsBeforeStarting() throws PacManException {
        Controller simple = Controller.getController(InterfaceMode.SIMPLE);
        simple.setGameType(GameType.TEST);
        simple.initialize(); // in my implementation, initizalize calls initializeNewGame by default
        assertThrows(ForbiddenActionException.class, () -> simple.receiveAction(GameAction.UP));
        assertThrows(ForbiddenActionException.class, () -> simple.receiveAction(GameAction.DOWN));
        assertThrows(ForbiddenActionException.class, () -> simple.receiveAction(GameAction.RESUME));
        assertThrows(ForbiddenActionException.class, () -> simple.receiveAction(GameAction.LEFT));
        assertThrows(ForbiddenActionException.class, () -> simple.receiveAction(GameAction.RIGHT));
        assertThrows(ForbiddenActionException.class, () -> simple.receiveAction(GameAction.PAUSE));
        //assertThrows(ForbiddenActionException.class, () -> simple.receiveAction(GameAction.NEXT_FRAME));
    }

    /**
     * Test that game action can be asked after the game is started
     * Note: we do not test that the action is actually performed on the model
     * @throws PacManException if initialization fails
     */
    @Test
    public void testGameActions() throws PacManException {
        Controller simple = Controller.getController(InterfaceMode.SIMPLE);
        simple.setGameType(GameType.TEST);
        simple.initialize(); // in my implementation, initizalize calls initializeNewGame by default
        simple.receiveAction(GameAction.START);
        simple.receiveAction(GameAction.NEXT_FRAME);
        simple.receiveAction(GameAction.UP);
        simple.receiveAction(GameAction.NEXT_FRAME);
        simple.receiveAction(GameAction.LEFT);
        simple.receiveAction(GameAction.NEXT_FRAME);
        simple.receiveAction(GameAction.DOWN);
        simple.receiveAction(GameAction.NEXT_FRAME);
        simple.receiveAction(GameAction.RIGHT);
        simple.receiveAction(GameAction.NEXT_FRAME);
        assertThrows(ForbiddenActionException.class, () -> simple.receiveAction(GameAction.START));
    }

    /**
     * Test that we can pause and resume the game and that proper exception are raised
     * if wrong action is sent
     * @throws PacManException if initialization fails
     */
    @Test
    public void testPauseResumeAction() throws PacManException {
        Controller simple = Controller.getController(InterfaceMode.SIMPLE);
        simple.setGameType(GameType.TEST);
        simple.initialize(); // in my implementation, initizalize calls initializeNewGame by default
        simple.receiveAction(GameAction.START);
        assertThrows(ForbiddenActionException.class, () -> simple.receiveAction(GameAction.RESUME));
        simple.receiveAction(GameAction.PAUSE);
        assertThrows(ForbiddenActionException.class, () -> simple.receiveAction(GameAction.PAUSE));
        assertThrows(ForbiddenActionException.class, () -> simple.receiveAction(GameAction.UP));
        assertThrows(ForbiddenActionException.class, () -> simple.receiveAction(GameAction.DOWN));
        assertThrows(ForbiddenActionException.class, () -> simple.receiveAction(GameAction.LEFT));
        assertThrows(ForbiddenActionException.class, () -> simple.receiveAction(GameAction.RIGHT));
        //assertThrows(ForbiddenActionException.class, () -> simple.receiveAction(GameAction.NEXT_FRAME));
        assertThrows(ForbiddenActionException.class, () -> simple.receiveAction(GameAction.START));
        simple.receiveAction(GameAction.RESUME);
        assertThrows(ForbiddenActionException.class, () -> simple.receiveAction(GameAction.RESUME));
    }
}
