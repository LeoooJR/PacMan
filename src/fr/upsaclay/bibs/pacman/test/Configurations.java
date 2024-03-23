package fr.upsaclay.bibs.pacman.test;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.Actor;
import fr.upsaclay.bibs.pacman.model.actors.Ghost;
import fr.upsaclay.bibs.pacman.model.actors.GhostPenState;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;

public final class Configurations {

    private Configurations() {};

    public static void blockGhosts(Maze maze) {
        maze.setTile(13,12, Tile.DT);
        maze.setTile(13, 15, Tile.DT);
        maze.setTile(17, 8, Tile.DL);
        maze.setTile(17, 19, Tile.DR);
        maze.setTile(21, 9, Tile.DB);
        maze.setTile(21, 18, Tile.DB);
    }

    public static void setGhostOut(Ghost ghost) {
        ghost.setPosition(112, 115);
        ghost.setGhostPenState(GhostPenState.OUT);
        ghost.setDirection(Direction.LEFT);
    }

    public static void setGhostIn(Ghost ghost) {
        ghost.setPosition(112, 139);
        ghost.setGhostPenState(GhostPenState.IN);
        ghost.setDirection(Direction.DOWN);
    }

    public static void smallSquareEatingGhost(Board board) {
        Configurations.blockGhosts(board.getMaze());
        for(Ghost ghost : board.getGhosts()) {
            if(ghost.getGhostPenState() != GhostPenState.OUT) {
                Configurations.setGhostOut(ghost);
            }
        }
        Actor pacman = board.getPacMan();
        pacman.setPosition(112,163);
        board.getMaze().setTile(20, 12, Tile.BD);
    }

    public static void smallSquareWithPacman(Board board) {
        Configurations.blockGhosts(board.getMaze());
        for(Ghost ghost : board.getGhosts()) {
            if(ghost.getGhostPenState() != GhostPenState.OUT) {
                Configurations.setGhostOut(ghost);
            }
        }
        Actor pacman = board.getPacMan();
        pacman.setPosition(112,163);
    }

    public static void fewDotsElroy(Maze maze) {
        Configurations.blockGhosts(maze);
        for(int i = 0; i < maze.getHeight(); i++) {
            for(int j = 0; j < maze.getWidth(); j++) {
                if(i != 26 && j != 6 && maze.getTile(i,j).hasDot()) {
                    maze.setTile(i,j,maze.getTile(i,j).clearDot());
                }
            }
        }
    }
}
