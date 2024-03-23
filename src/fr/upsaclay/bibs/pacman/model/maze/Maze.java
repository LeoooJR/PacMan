package fr.upsaclay.bibs.pacman.model.maze;


import fr.upsaclay.bibs.pacman.model.Direction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The interface corresponding to the maze where PacMan evolves
 * It is made by a grid of tiles,
 * each tile corresponding to a certain number of pixels
 *
 * @author Viviane Pons
 */
public interface Maze {

    /* tile default width in pixels */
    int TILE_WIDTH = 8;

    /* tile default height in pixels */
    int TILE_HEIGHT = 8;

    /* The x position of the central pixel inside a tile */
    int TITLE_CENTER_X = 3;

    /* The y position of the central pixel inside a tile */
    int TITLE_CENTER_Y = 3;

    /**
     * Return the maze width
     * i.e. the number of tiles horizontally
     * @return the width
     */
    int getWidth();

    /**
     * Return the width in number of pixels
     * @return the number of horizontal pixels
     */
    int getPixelWidth();

    /**
     * Return the maze height
     * i.e. the number of tiles vertically
     * @return the number of vertical pixels
     */
    int getHeight();

    /**
     * Return the maze height in number of pixels
     * @return the pixel height
     */
    int getPixelHeight();

    /**
     * Get the tile at given position
     * @param line, the line number
     * @param col, the col number
     * @return the corresponding tile
     */
    Tile getTile(int line, int col);

    /**
     * Get the tile at a given position
     * @param pos, a tile position
     * @return the corresponding tile
     */
    Tile getTile(TilePosition pos);

    /**
     * Return the next tile from a position in
     * a given direction
     * @param line, the line number
     * @param col, the col number
     * @param dir, the direction
     * @return the corresponding tile
     */
    TilePosition getNeighbourTilePosition(int line, int col, Direction dir);

    /**
     * Return the next tile from a position in
     * a given direction
     * @param pos, a tile position
     * @param dir, a direction
     * @return the corresponding tile
     */
    TilePosition getNeighbourTilePosition(TilePosition pos, Direction dir);

    /**
     * Return the next tile from a position in
     * a given direction
     * @param line, the line number
     * @param col, the col number
     * @param dir, the direction
     * @return the corresponding tile
     */
    Tile getNeighbourTile(int line, int col, Direction dir);

    /**
     * Return the next tile from a position in
     * a given direction
     * @param pos, a tile position
     * @param dir, a direction
     * @return the corresponding tile
     */
    Tile getNeighbourTile(TilePosition pos, Direction dir);

    /**
     * Put a given tile at the position
     * @param line, the line number
     * @param col, the col number
     * @param tile, the tile
     */
    void setTile(int line, int col, Tile tile);

    /**
     * Put a given tile at the position
     * @param pos, the tile position
     * @param tile, the tile
     */
    void setTile(TilePosition pos, Tile tile);

    /**
     * Return the tile position of a given pixel
     * @param x, the x coordinate of the pixel
     * @param y, the y coordinate of the pixel
     * @return a tile position
     */
    TilePosition getTilePosition(int x, int y);

    /** Create an empty Maze
     * Create a maze with given dimension and only empty tiles
     * @param width the number of tiles horizontally
     * @param height the number of tiles vertically
     * @return an empty PacMan Maze
     */
    static Maze emptyMaze(int width, int height) {
		Tile[][] maze = new Tile[height][width];
        for(int row = 0; row < width; row++){
            for(int col = 0; col < height; col++){
                maze[col][row] = Tile.EE;
            }
        }
        System.out.println(Arrays.deepToString(maze));
        return new PacManMaze(maze,width,height);
    }

    /**
     * Load a Maze from a file
     * @param fileName the file name
     * @return a PacMan Maze
     */
    static Maze loadFromFile(String fileName) throws FileNotFoundException {
		try{
            File gameDataMaze = new File(fileName);
            Scanner scanner = new Scanner(gameDataMaze);
            String rawLineData; String[] rowData; boolean mazeData = true;
            int colPtr; int rowPtr = 0; int width = 0; int height = 0; Tile[][] maze = null;
            while(scanner.hasNextLine()){
                rawLineData = scanner.nextLine();
                rowData = rawLineData.split(" ");
                if(mazeData){
                    mazeData = false;
                    System.out.println(rawLineData);
                    System.out.println(Arrays.toString(rowData));
                    width = Integer.parseInt(rowData[0]);
                    height = Integer.parseInt(rowData[1]);
                    maze = new Tile[height][width];
                }
                else {
                    colPtr = 0;
                    for(String tile: rowData){
                        maze[rowPtr][colPtr++] = Tile.valueOf(tile);
                    }
                    rowPtr++;
                }
            }
            System.out.println(Arrays.deepToString(maze));
            return new PacManMaze(maze,width,height);
        } catch (FileNotFoundException e){
            throw new FileNotFoundException("Error in file path");
        }
    }

    // Step 2
    // The methods below won't be used / tested before step 2

    /**
     * Return the current number of dots on the maze (big and small)
     * @return the number of dots
     */
    int getNumberOfDots();
}
