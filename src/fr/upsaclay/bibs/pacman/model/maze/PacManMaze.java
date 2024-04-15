package fr.upsaclay.bibs.pacman.model.maze;

import fr.upsaclay.bibs.pacman.model.Direction;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacManMaze implements Maze{

    private Tile[][] maze;

    private int width;

    private int height;

    public PacManMaze(Tile[][] maze, int width, int height) {
        this.maze = maze;
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width){
        this.width = width;
    }

    @Override
    public int getPixelWidth() {
        return (width * Maze.TILE_WIDTH);
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height){
        this.height = height;
    }

    @Override
    public int getPixelHeight() {
        return (height * Maze.TILE_HEIGHT);
    }

    @Override
    public Tile getTile(int line, int col) {
        return maze[line][col];
    }

    @Override
    public Tile getTile(TilePosition pos) {
        return maze[pos.getLine()][pos.getCol()];
    }

    @Override
    public TilePosition getNeighbourTilePosition(int line, int col, Direction dir) {
        switch (dir){
            case Direction.UP, Direction.DOWN:
                if(line + dir.getDy() < 0){
                    return new TilePosition(height-1, col);
                }
                else if(line + dir.getDy() >= height){
                    return new TilePosition(0, col);
                }
                else return new TilePosition(line + dir.getDy(), col);
            case Direction.RIGHT, Direction.LEFT:
                if(col + dir.getDx() < 0){
                    return new TilePosition(line,width-1);
                }
                else if(col + dir.getDx() >= width){
                    return new TilePosition(line, 0);
                }
                else return new TilePosition(line, col + dir.getDx());
            default: return new TilePosition(line, col);
        }
    }

    @Override
    public TilePosition getNeighbourTilePosition(TilePosition pos, Direction dir) {
        switch (dir){
            case Direction.UP, Direction.DOWN:
                if(pos.getLine() + dir.getDy() < 0){
                    return new TilePosition(height-1, pos.getCol());
                }
                else if(pos.getLine() + dir.getDy() >= height){
                    return new TilePosition(0, pos.getCol());
                }
                else return new TilePosition(pos.getLine() + dir.getDy(), pos.getCol());
            case Direction.RIGHT, Direction.LEFT:
                if(pos.getCol() + dir.getDx() < 0){
                    return new TilePosition(pos.getLine(),width-1);
                }
                else if(pos.getCol() + dir.getDx() >= width){
                    return new TilePosition(pos.getLine(), 0);
                }
                else return new TilePosition(pos.getLine(), pos.getCol() + dir.getDx());
            default: return new TilePosition(pos.getLine(), pos.getCol());
        }
    }

    @Override
    public Tile getNeighbourTile(int line, int col, Direction dir) {
        switch (dir){
            case Direction.UP, Direction.DOWN:
                if(line + dir.getDy() < 0){
                    return maze[height-1][col];
                }
                else if(line + dir.getDy() >= height){
                    return maze[0][col];
                }
                else return maze[line + dir.getDy()][col];
            case Direction.RIGHT, Direction.LEFT:
                if(col + dir.getDx() < 0){
                    return maze[line][width-1];
                }
                else if(col + dir.getDx() >= width){
                    return maze[line][0];
                }
                else return maze[line][col + dir.getDx()];
            default: return maze[line][col];
        }
    }

    @Override
    public Tile getNeighbourTile(TilePosition pos, Direction dir) {
        switch (dir){
            case Direction.UP, Direction.DOWN:
                if(pos.getLine() + dir.getDy() < 0){
                    return maze[height-1][pos.getCol()];
                }
                else if(pos.getLine() + dir.getDy() >= height){
                    return maze[0][pos.getCol()];
                }
                else {return maze[pos.getLine() + dir.getDy()][pos.getCol()];}
            case Direction.RIGHT, Direction.LEFT:
                if(pos.getCol() + dir.getDx() < 0){
                    return maze[pos.getLine()][width-1];
                }
                else if(pos.getCol() + dir.getDx() >= width){
                    return maze[pos.getLine()][0];
                }
                else {return maze[pos.getLine()][pos.getCol() + dir.getDx()];}
            default: return maze[pos.getLine()][pos.getCol()];
        }
    }

    @Override
    public void setTile(int line, int col, Tile tile) {
        maze[line][col] = tile;
    }

    @Override
    public void setTile(TilePosition pos, Tile tile) {
        maze[pos.getLine()][pos.getCol()] = tile;
    }

    @Override
    public TilePosition getTilePosition(int x, int y) {
        //int col = Math.ceilDiv(x,Maze.TILE_WIDTH);
        //int line = Math.ceilDiv(y,Maze.TILE_HEIGHT);
        int col = x / Maze.TILE_WIDTH;
        int line = y / Maze.TILE_HEIGHT;
        //System.out.println("X " + x + " Y " + y);
        //System.out.println("Ligne " + line + " Colonne " + col);
        // Subtract the values by 1 to work with 0 based indexing
        return new TilePosition(line, col);
    }

    @Override
    public int getNumberOfDots() {
        int numberOfDots = 0;
        for(Tile[] tiles: maze){
            for(Tile tile: tiles){
                numberOfDots += tile.hasDot() ? 1 : 0;
            }
        }
        return numberOfDots;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Maze test = Maze.emptyMaze(10,15);
        List<Color> colors = new ArrayList<>();
        colors.add(Color.BLACK); colors.addLast(Color.BLUE); colors.addLast(Color.pink);
        Color[][] test2 = Maze.loadFromFile("/Users/leojourdain/Documents/Java/Projet/Projet_Pacman/resources/tiles/BD.txt",colors);
//        System.out.println(Arrays.deepToString(test2));

    }
}
