package fr.upsaclay.bibs.pacman.model.board;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.actors.PacMan;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.PacManMaze;

import java.io.FileNotFoundException;

public class ClassicBoard extends AbstractBoard{

    ClassicBoard(){
        super(GameType.CLASSIC);
    }

    @Override
    public void initialize() throws PacManException {
        //Load the maze
        try {
            this.setMaze(Maze.loadFromFile("/Users/leojourdain/Documents/Java/Projet/Projet_Pacman/resources/maze.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //Load PacMan
        setPacman(new PacMan(this));
        //Start PacMan
        getPacMan().start();
    }
}
