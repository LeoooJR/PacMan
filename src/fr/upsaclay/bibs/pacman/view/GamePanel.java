package fr.upsaclay.bibs.pacman.view;

import fr.upsaclay.bibs.pacman.model.actors.Actor;
import fr.upsaclay.bibs.pacman.model.actors.GhostType;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.PacManMaze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {

    private Maze maze;

    private Actor pacman;

    private Actor blinky;

    public GamePanel(){
        super();
    }

    public void setPacman(Actor agent){
        this.pacman = agent;
    }

    public void setBlinky(Actor agent) {this.blinky = agent;}

    public void setMaze(Maze maze){
        this.maze = maze;
    }

    public void paintWall(Graphics graphics, Tile tile, int i, int j, int offset_x, int offset_y) {
        List<Color> colors = new ArrayList<>();
        colors.add(Color.BLACK); colors.addLast(Color.BLUE); colors.addLast(Color.pink);
        Color[][] tileColor;
        try {
            tileColor = Maze.loadFromFile("/Users/leojourdain/Documents/Java/Projet/Projet_Pacman/resources/tiles/" + tile.toString() + ".txt",colors);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for(int row = 0; row < Maze.TILE_HEIGHT; row++){
            for(int col = 0; col < Maze.TILE_WIDTH; col++){
                graphics.setColor(tileColor[row][col]);
            }
        }
        if(tile.isWall()){
            graphics.setColor(Color.BLUE);
        }
        else {
            graphics.setColor(Color.BLACK);
        }
        graphics.fillRect(offset_x + (j * PacManGameView.PIXELS_PER_CELLS), offset_y + (i * PacManGameView.PIXELS_PER_CELLS), PacManGameView.PIXELS_PER_CELLS, PacManGameView.PIXELS_PER_CELLS);
    }

    public void paintGhost(Graphics graphics, GhostType type, int offset_x, int offset_y){
        switch (type){
            case BLINKY:
                graphics.setColor(Color.RED);
                graphics.fillRect((offset_x + (blinky.getCurrentTile().getCol() * Maze.TILE_WIDTH)*3),offset_y + ((blinky.getCurrentTile().getLine() * Maze.TILE_HEIGHT)*3),PacManGameView.PIXELS_PER_CELLS, PacManGameView.PIXELS_PER_CELLS);
        }
    }

    public void paintPacMan(Graphics graphics, int offset_x, int offset_y){
        graphics.setColor(Color.YELLOW);
        graphics.fillRect((offset_x + (pacman.getCurrentTile().getCol() * Maze.TILE_WIDTH)*3),offset_y + ((pacman.getCurrentTile().getLine() * Maze.TILE_HEIGHT)*3),PacManGameView.PIXELS_PER_CELLS, PacManGameView.PIXELS_PER_CELLS);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        int offset_x = (PacManGameView.WIDTH - (PacManGameView.PIXELS_PER_CELLS * maze.getWidth()))/2;
        int offset_y = (PacManGameView.HEIGHT - (PacManGameView.PIXELS_PER_CELLS * maze.getHeight()))/2;
        if(maze != null){
            for(int row = 0; row < maze.getHeight(); row++){
                for(int col = 0; col < maze.getWidth(); col++){
                    paintWall(graphics,maze.getTile(row,col),row,col,offset_x,offset_y);
                }
            }
            paintPacMan(graphics, offset_x, offset_y);
            paintGhost(graphics,GhostType.BLINKY,offset_x,offset_y);
        }
    }
}
