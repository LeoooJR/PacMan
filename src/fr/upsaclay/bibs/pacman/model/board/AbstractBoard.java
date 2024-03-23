package fr.upsaclay.bibs.pacman.model.board;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.*;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.PacManMaze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;

import java.io.FileNotFoundException;
import java.util.List;

public class AbstractBoard implements Board{

    private final GameType game;

    private BoardState gameState;

    private Maze maze;

    private PacMan pacman;

    private int score = 0;

    AbstractBoard(GameType game){
        this.game = game;
        this.gameState = BoardState.INITIAL;
    }
    @Override
    public GameType getGameType() {
        return game;
    }

    @Override
    public void initialize() throws PacManException {
        throw new UnsupportedOperationException("Ooops something went wrong!");
    }

    @Override
    public void startActors() {
        pacman.start();
        gameState = BoardState.STARTED;
    }

    @Override
    public Maze getMaze() {
        return maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public Actor getPacMan() {
        return pacman;
    }

    public void setPacman(PacMan agent) { pacman = agent;}

    @Override
    public void nextFrame() {
        pacman.nextFrame();
        switch(maze.getTile(pacman.getCurrentTile())){
            case SD, ND:
                score += 10;
                pacman.reseatStopTime();
                maze.setTile(pacman.getCurrentTile(), Tile.EE);
                break;
            case BD:
                score += 50;
                pacman.reseatStopTime();
                maze.setTile(pacman.getCurrentTile(), Tile.EE);
                break;
        }
        if(maze.getNumberOfDots() == 0){
            gameState = BoardState.LEVEL_OVER;
        }
    }

    //Step 2

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public BoardState getBoardState() {
        return gameState;
    }

    @Override
    public Ghost getGhost(GhostType ghostType) {
        return null;
    }

    @Override
    public List<Ghost> getGhosts() {
        return null;
    }

    //Step 3

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public void initializeNewLevel(int level) throws PacManException {

    }

    @Override
    public void setNumberOfLives(int nbLives) {

    }

    @Override
    public int getNumberOfLives() {
        return 0;
    }

    @Override
    public void initializeNewLife() {

    }

    @Override
    public boolean hasGhost(GhostType ghostType) {
        return false;
    }

    @Override
    public void disableGhost(GhostType ghostType) {

    }

    @Override
    public void disableStateTime() {

    }

    @Override
    public Direction getRandomDirection() {
        return null;
    }

    @Override
    public TilePosition penEntry() {
        return null;
    }

    @Override
    public int minYPen() {
        return 0;
    }

    @Override
    public int maxYPen() {
        return 0;
    }

    @Override
    public int penGhostXPosition(GhostType type) {
        return 0;
    }

    @Override
    public int penGhostYPosition(GhostType type) {
        return 0;
    }

    @Override
    public int outPenXPosition() {
        return 0;
    }

    @Override
    public int outPenYPosition() {
        return 0;
    }

    @Override
    public Counter noDotCounter() {
        return null;
    }

    @Override
    public Counter specialDotCounter() {
        return null;
    }

    @Override
    public double getLevelPacManSpeed() {
        return 0;
    }

    @Override
    public double getFrightPacManSpeed() {
        return 0;
    }

    @Override
    public double getLevelGhostSpeed() {
        return 0;
    }

    @Override
    public double getTunnelGhostSpeed() {
        return 0;
    }

    @Override
    public double getFrightGhostSpeed() {
        return 0;
    }

    @Override
    public double getDeadGhostSpeed() {
        return 0;
    }

    @Override
    public void setExtraLifeScore(int score) {

    }

    @Override
    public int getExtraLifeScore() {
        return 0;
    }

    @Override
    public Bonus getCurrentBonus() {
        return null;
    }

    @Override
    public BonusType getLevelBonusType(int level) {
        return null;
    }

    @Override
    public void setBonusOnBoard() {

    }

    @Override
    public double getElroyGhostSpeed(int elroyNumber) {
        return 0;
    }

    @Override
    public int getElroyDotValue(int elroyNumber) {
        return 0;
    }

    @Override
    public List<BoardEvent> getCurrentEvents() {
        return null;
    }
}
