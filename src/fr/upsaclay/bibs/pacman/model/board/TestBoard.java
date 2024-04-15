package fr.upsaclay.bibs.pacman.model.board;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.PacManException;
import fr.upsaclay.bibs.pacman.model.actors.PacMan;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.PacManMaze;

import java.io.FileNotFoundException;

public class TestBoard extends AbstractBoard{

    TestBoard(){
        super(GameType.TEST);
    }

    @Override
    public void initialize() throws PacManException{
        //Load the maze
        try {
            setMaze(Maze.loadFromFile("resources/test.txt"));
//            System.out.println("Initialisation");
        }
        catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
        //Load PacMan
        setPacman(new PacMan(this));
        //startActors();
    }

    public static void main(String[] args) throws PacManException {
        Board test = Board.createBoard(GameType.TEST);
        test.initialize();
//        System.out.println(test.getPacMan().getCurrentTile());
    }
}
