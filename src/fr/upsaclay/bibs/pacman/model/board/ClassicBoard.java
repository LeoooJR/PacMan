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
            this.setMaze(Maze.loadFromFile("/Users/leojourdain/Documents/Java/Projet/Projet_Pacman/resources/maze.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //Load PacMan
        setPacman(new PacMan(this));
        //Load Ghosts
        List<Ghost> ghostList = new ArrayList<>();
        //Blinky
        ghostList.add(new Blinky(this));
        setGhosts(ghostList);
        //Add last Pinky
        //Add last Inky
        //Add last Clyde
        //startActors();
    }
}
