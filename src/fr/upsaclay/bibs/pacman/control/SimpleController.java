package fr.upsaclay.bibs.pacman.control;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.view.PacManGameView;
import fr.upsaclay.bibs.pacman.view.PacManLayout;

public class SimpleController implements Controller{

    private Board board;

    private PacManGameView view;

    @Override
    public void initialize() throws PacManException {
        view = new PacManGameView("PacMan",720,1000);
        view.setController(this);
        view.initialize();
        view.setLayout(PacManLayout.INIT);
    }

    @Override
    public void initializeNewGame() throws PacManException {
        //Creation du plateau
    }

    @Override
    public void setGameType(GameType gameType) {
        board = Board.createBoard(gameType);
    }

    @Override
    public GameType getGameType() {
        return board.getGameType();
    }

    @Override
    public void receiveAction(GameAction action) throws PacManException {
        switch(action){
            case RIGHT, LEFT, UP, DOWN:
                if(view.getViewLayout() == PacManLayout.GAME_ON){
                    getBoard().getPacMan().setIntention(Direction.valueOf(String.valueOf(action)));
                }
                else {
                    throw new ForbiddenActionException(action);
                }
                break;
            case START:
                if(view.getViewLayout() == PacManLayout.INIT){
                    view.setLayout(PacManLayout.GAME_ON);
                    board.initialize();
                }
                else {
                    throw new ForbiddenActionException(action);
                }
                break;
            case PAUSE:
                if(view.getViewLayout() == PacManLayout.GAME_ON){
                    view.setLayout(PacManLayout.PAUSE);
                }
                else {
                    throw new ForbiddenActionException(action);
                }
                break;
            case RESUME:
                if(view.getViewLayout() == PacManLayout.PAUSE) {
                    view.setLayout(PacManLayout.GAME_ON);
                }
                else {
                    throw new ForbiddenActionException(action);
                }
                break;
            case NEXT_FRAME:
                board.getPacMan().nextFrame();
        }
    }

    @Override
    public Board getBoard() {
        return board;
    }
}