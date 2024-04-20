package fr.upsaclay.bibs.pacman.control;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.GhostType;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.BoardState;
import fr.upsaclay.bibs.pacman.view.PacManGameView;
import fr.upsaclay.bibs.pacman.view.PacManLayout;

public class SimpleController implements Controller {

    private Board board;

    private PacManGameView view;

    @Override
    public void initialize() throws PacManException {
        view = new PacManGameView("PacMan", 720, 1000);
        view.setController(this);
//        board
        view.setBoard(board);
        view.initialize();
        view.setLayout(PacManLayout.INIT);
    }

    @Override
    public void initializeNewGame() throws PacManException {
        //Creation du plateau

    }

    @Override
    public GameType getGameType() {
        return board.getGameType();
    }

    @Override
    public void setGameType(GameType gameType) {
        board = Board.createBoard(gameType);
    }

   @Override
public void receiveAction(GameAction action) throws PacManException {
    if (isGameOn()) {
        handleGameOnActions(action);
    } else if (isGamePaused()) {
        handleGamePausedActions(action);
    } else if (isGameInit()) {
        handleGameInitActions(action);
    } else {
        throw new ForbiddenActionException(action);
    }
    view.update();
}

private boolean isGameOn() {
    return view.getViewLayout() == PacManLayout.GAME_ON;
}

private boolean isGamePaused() {
    return view.getViewLayout() == PacManLayout.PAUSE;
}

private boolean isGameInit() {
    return view.getViewLayout() == PacManLayout.INIT;
}

private void handleGameOnActions(GameAction action) throws PacManException {
    switch (action) {
        case RIGHT, LEFT, UP, DOWN:
            System.out.println(STR."Event : \{Direction.valueOf(String.valueOf(action))}");
            board.getPacMan().setIntention(Direction.valueOf(String.valueOf(action)));
            break;
        case PAUSE:
            System.out.println("Pause");
            view.setLayout(PacManLayout.PAUSE);
            break;
        case NEXT_FRAME:
            board.nextFrame();
            switch (board.getBoardState()) {
                case LEVEL_OVER:
                    view.setLayout(PacManLayout.LEVEL_OVER);
                case LIFE_OVER:
                    view.setLayout(PacManLayout.LIFE_OVER);
            }
            break;
        default:
            throw new ForbiddenActionException(action);
    }
}

private void handleGamePausedActions(GameAction action) throws PacManException {
    if (action == GameAction.RESUME) {
        System.out.println("Resume");
        view.setLayout(PacManLayout.GAME_ON);
    } else {
        throw new ForbiddenActionException(action);
    }
}

private void handleGameInitActions(GameAction action) throws PacManException {
    if (action == GameAction.START) {
        System.out.println("Start");
        view.setLayout(PacManLayout.GAME_ON);
        board.initialize();
        board.setBoardState(BoardState.STARTED);
        view.setBoard(board);
        view.setMaze(board.getMaze());
        view.setPacMan(board.getPacMan());
        view.setGhosts(board.getGhosts());
    } else if (action == GameAction.NEW_GAME || action == GameAction.NEXT_LEVEL) {
        view.setLayout(PacManLayout.INIT);
    } else {
        throw new ForbiddenActionException(action);
    }
}
    @Override
    public Board getBoard() {
        return board;
    }
}
