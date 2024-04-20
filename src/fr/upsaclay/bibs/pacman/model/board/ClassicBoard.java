package fr.upsaclay.bibs.pacman.model.board;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.actors.Blinky;
import fr.upsaclay.bibs.pacman.model.actors.Ghost;
import fr.upsaclay.bibs.pacman.model.actors.PacMan;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.PacManMaze;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ClassicBoard extends AbstractBoard{

    ClassicBoard(){
        super(GameType.CLASSIC);
    }

    @Override
    public void initialize() throws PacManException {
        //Load the maze
        try {
            this.setMaze(Maze.loadFromFile("resources/maze.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        super.initialize();
    }

    @Override
    public void initializeNewLevel(int level) throws PacManException {
        initialize();
        super.initializeNewLevel(level);
    }
}
