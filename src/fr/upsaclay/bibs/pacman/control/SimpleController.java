package fr.upsaclay.bibs.pacman.control;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.GhostType;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.BoardState;
import fr.upsaclay.bibs.pacman.view.PacManGameView;
import fr.upsaclay.bibs.pacman.view.PacManLayout;

public class SimpleController implements Controller{

    private Board board;

    private PacManGameView view;

    @Override
    public void initialize() throws PacManException {
        view = new PacManGameView("PacMan",720,1000);
        view.setController(this);
        //view.setBoard(board);
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
                    System.out.println("Event : " + Direction.valueOf(String.valueOf(action)));
                    board.getPacMan().setIntention(Direction.valueOf(String.valueOf(action)));
                }
                else {
                    throw new ForbiddenActionException(action);
                }
                break;
            case START:
                if(view.getViewLayout() == PacManLayout.INIT){
                    view.setLayout(PacManLayout.GAME_ON);
                    board.initialize();
                    board.setBoardState(BoardState.STARTED);
                    view.setBoard(board);
                    view.setMaze(board.getMaze());
                    view.setPacMan(board.getPacMan());
                    view.setGhosts(board.getGhosts());
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
                if(view.getViewLayout() == PacManLayout.GAME_ON){
                board.nextFrame();
                switch (board.getBoardState()){
                    case LEVEL_OVER:
                        view.setLayout(PacManLayout.LEVEL_OVER);
                    case LIFE_OVER:
                        view.setLayout(PacManLayout.LIFE_OVER);
                }}
                else {
                    throw new ForbiddenActionException(action);
                }
                break;
            case NEW_GAME:
                view.setLayout(PacManLayout.INIT);
            case NEXT_LEVEL:
                view.setLayout(PacManLayout.INIT);
        }
        view.update();
    }

    @Override
    public Board getBoard() {
        return board;
    }
}
