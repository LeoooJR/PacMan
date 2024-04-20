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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AbstractBoard implements Board {

    private final GameType game;


    private BoardState gameState;

    private boolean isPlaying;

    private Maze maze;

    private PacMan pacman;

    private List<Ghost> ghosts;

    private List<GhostType> disabledGhosts;
    private GhostState ghostState;

    private GhostState ghostPreviousState;

    private int[] scatterTime;

    private int[] chaseTime;

    private int scatterCtr;

    private int chaseCtr;

    private int[] frightTime;

    private int frightTimer;

    private boolean disable;

    private int score = 0;

    private int level;


    AbstractBoard(GameType game) {
        this.game = game;
        this.gameState = BoardState.INITIAL;
        this.isPlaying = false;
        this.disabledGhosts = new ArrayList<>();

    }

    @Override
    public GameType getGameType() {
        return game;
    }

    @Override
    public void initialize() throws PacManException {
        if (!isPlaying) {
            level = 1;
            score = 0;
            scatterTime = new int[]{7 * 60, 7 * 60, 5 * 60, 5 * 60};
            chaseTime = new int[]{20 * 60, 20 * 60, 20 * 60, -1};
            frightTime = new int[]{6, 5, 4, 3, 2, 5, 2, 2, 1, 5, 2, 1, 1, 3, 1, 1, -1, 1, -1, -1, -1};
            //Load PacMan
            setPacman(new PacMan(this));
            ghosts = new ArrayList<>();

            //Blinky
            if (!disabledGhosts.contains(GhostType.BLINKY)) {
                ghosts.add(new Blinky(this));
            }
            //Pinky
            if (!disabledGhosts.contains(GhostType.PINKY)) {
                ghosts.add(new Pinky(this));
            }
//            //Inky
            if (!disabledGhosts.contains(GhostType.INKY)) {
                ghosts.add(new Inky(this));
            }
//            //Clyde
            if (!disabledGhosts.contains(GhostType.CLYDE)) {
                ghosts.add(new Clyde(this));
            }
            isPlaying = true;
        } else {
            initializeNewLife();
        }
        scatterCtr = 0;
        chaseCtr = 0;
        ghostState = GhostState.SCATTER;
    }

    @Override
    public void startActors() {
        pacman.start();
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

    public void setPacman(PacMan agent) {
        pacman = agent;
    }

    @Override
    public void nextFrame() {
        updatePacmanState();
        checkLevelCompletion();
        updateGhostsState();
        updateGhostTimers();
    }

    private void updatePacmanState() {
        pacman.nextFrame();
        Tile currentTile = maze.getTile(pacman.getCurrentTile());
        processPacmanTile(currentTile);
    }

    private void processPacmanTile(Tile tile) {
        switch (tile) {
            case SD, ND:
                increaseScore(10);
                pacman.setStopTime(1);
                maze.setTile(pacman.getCurrentTile(), tile == Tile.ND ? Tile.NT : Tile.EE);
                break;
            case BD:
                increaseScore(50);
                pacman.setStopTime(3);
                maze.setTile(pacman.getCurrentTile(), Tile.EE);
                triggerFrightenedMode();
                break;
        }
    }

    private void increaseScore(int points) {
        score += points;
    }

    private void triggerFrightenedMode() {
        ghostPreviousState = ghostState;
        ghostState = GhostState.FRIGHTENED;
        frightTimer = frightTime[level - 1];
    }

    private void checkLevelCompletion() {
        if (maze.getNumberOfDots() == 0) {
            gameState = BoardState.LEVEL_OVER;
        }
    }

    private void updateGhostsState() {



            for (Ghost ghost : ghosts) {
                if (ghostState != ghost.getGhostState() && !disable) {
                    ghost.setGhostState(ghostState);
                    ghost.reverseDirectionIntention();
                }
                updateGhost(ghost);
            }

    }

    private void updateGhost(Ghost ghost) {
        ghost.nextFrame();
        checkGhostCollision(ghost);
        adjustGhostSpeed(ghost);
    }

    private void checkGhostCollision(Ghost ghost) {
        if (ghost.getCurrentTile().equals(pacman.getCurrentTile())) {
            gameState = pacman.getLifePoint() > 0 ? BoardState.LIFE_OVER : BoardState.GAME_OVER;
        }
    }

    private void adjustGhostSpeed(Ghost ghost) {
        Tile ghostTile = maze.getTile(ghost.getCurrentTile());
        if (ghostTile == Tile.SL) {
            ghost.setSpeed(0.5);
        } else if (ghost.getSpeed() == 0.5) {
            ghost.setSpeed(1);
        }
    }

    private void updateGhostTimers() {
        if (!disable && ghostState != GhostState.FRIGHTENED) {
            updateModeTimers();
        } else {
            decrementFrightTimer();
        }
    }

    private void updateModeTimers() {
        if(!disable) {
        if (ghostState == GhostState.SCATTER && decrementTimer(scatterTime, scatterCtr)) {
            transitionToChaseMode();
        } else if (ghostState == GhostState.CHASE && decrementTimer(chaseTime, chaseCtr)) {
            maybeTransitionToScatterMode();
        }}
    }

    private boolean decrementTimer(int[] times, int index) {
        return --times[index] == 0;
    }

    private void transitionToChaseMode() {
        ghostState = GhostState.CHASE;
        scatterCtr++;
    }

    private void maybeTransitionToScatterMode() {
        if (scatterCtr < 3) {
            ghostState = GhostState.SCATTER;
        }
        chaseCtr++;
    }

    private void decrementFrightTimer() {
        frightTimer--;
        if (frightTimer == 0) {
            ghostState = ghostPreviousState;
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
    public void setBoardState(BoardState state) {
        this.gameState = state;
    }

    @Override
    public Ghost getGhost(GhostType ghostType) {
        for (Ghost ghost : ghosts) {
            if (ghost.getGhostType() == ghostType) {
                return ghost;
            }
        }
        return null;
    }

    @Override
    public List<Ghost> getGhosts() {
        return ghosts;
    }

    public void setGhosts(List<Ghost> ghostList) {
        this.ghosts = ghostList;
    }

    //Step 3

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void initializeNewLevel(int level) throws PacManException {
        this.level = level;
        this.scatterCtr = 0;
        switch (level) {
            case 2, 3, 4:
                scatterTime = new int[]{7 * 60, 7 * 60, 5 * 60, 1};
                chaseTime = new int[]{20 * 60, 20 * 60, 1033 * 60, -1};
                break;
            default:
                scatterTime = new int[]{5 * 60, 5 * 60, 5 * 60, 1};
                chaseTime = new int[]{20 * 60, 20 * 60, 1037 * 60, -1};
                break;
        }
    }

    @Override
    public int getNumberOfLives() {
        return pacman.getLifePoint();
    }

    @Override
    public void setNumberOfLives(int nbLives) {
    }

    @Override
    public void initializeNewLife() {
        pacman.initializePacMan();
        //initialize Ghosts
        ghosts.clear();
        //Blinky
        ghosts.add(new Blinky(this));
        //Add last Pinky
        //Add last Inky
        //Add last Clyde

    }

    @Override
    public boolean hasGhost(GhostType ghostType) {
        if (ghosts.isEmpty()) {
            return false;
        } else {
            for (Ghost ghost : ghosts) {
                if (ghost.getGhostType() == ghostType) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void disableGhost(GhostType ghostType) {
        disabledGhosts.add(ghostType);

    }

    @Override
    public void disableStateTime() {
        disable = true;
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
//        40% of the level speed
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
    public int getExtraLifeScore() {
        return 0;
    }

    @Override
    public void setExtraLifeScore(int score) {

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
