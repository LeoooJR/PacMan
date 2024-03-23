package fr.upsaclay.bibs.pacman.test.step2;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;
import fr.upsaclay.bibs.pacman.model.maze.TilePosition;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class MazeTest {

    /** Step 1 tests that should still work (unchanged) **/

    @Test
    public void testEmptyMaze() {
        /** this only test that the creation goes without errors **/
        Maze maze = Maze.emptyMaze(10,10);
    }

    @Test
    public void testWidthHeight() {
        Maze maze = Maze.emptyMaze(10,15);
        assertEquals(maze.getWidth(), 10);
        assertEquals(maze.getHeight(), 15);
    }

    @Test
    public void testGetPixelWidthHeight() {
        Maze maze = Maze.emptyMaze(10,15);
        assertEquals(maze.getPixelWidth(), Maze.TILE_WIDTH * maze.getWidth());
        assertEquals(maze.getPixelHeight(), Maze.TILE_HEIGHT * maze.getHeight());
    }

    @Test
    public void testSetGetTile() {
        Maze maze = Maze.emptyMaze(10,15);
        /** by default, all tile of epty maze should be empty **/
        assertEquals(maze.getTile(0,0), Tile.EE);
        /* set / get using line and col */
        maze.setTile(3,4, Tile.WB);
        assertEquals(maze.getTile(3,4), Tile.WB);
        /* set / get using TilePosition */
        TilePosition pos = new TilePosition(3,4);
        maze.setTile(pos, Tile.WL);
        assertEquals(maze.getTile(pos), Tile.WL);
    }

    @Test
    public void testGetNeighbourTilePosition() {
        Maze maze = Maze.emptyMaze(10,15);

        /** Testing directions inside the grid **/
        assertEquals(maze.getNeighbourTilePosition(0,0, Direction.RIGHT), new TilePosition(0,1));
        assertEquals(maze.getNeighbourTilePosition(new TilePosition(0,0), Direction.RIGHT), new TilePosition(0,1));
        assertEquals(maze.getNeighbourTilePosition(0,0, Direction.DOWN), new TilePosition(1,0));
        assertEquals(maze.getNeighbourTilePosition(new TilePosition(0,0), Direction.DOWN), new TilePosition(1,0));
        assertEquals(maze.getNeighbourTilePosition(1,1, Direction.UP), new TilePosition(0,1));
        assertEquals(maze.getNeighbourTilePosition(new TilePosition(1,1), Direction.UP), new TilePosition(0,1));
        assertEquals(maze.getNeighbourTilePosition(1,1, Direction.LEFT), new TilePosition(1,0));
        assertEquals(maze.getNeighbourTilePosition(new TilePosition(1,1), Direction.LEFT), new TilePosition(1,0));
        /** testing with circularity  when you get out of the grid **/
        assertEquals(maze.getNeighbourTilePosition(0,0, Direction.LEFT), new TilePosition(0,9));
        assertEquals(maze.getNeighbourTilePosition(0,0, Direction.UP), new TilePosition(14,0));
        assertEquals(maze.getNeighbourTilePosition(0,9, Direction.RIGHT), new TilePosition(0,0));
        assertEquals(maze.getNeighbourTilePosition(14,0, Direction.DOWN), new TilePosition(0,0));
    }

    @Test
    public void testGetNeighbourTile() {
        Maze maze = Maze.emptyMaze(10,15);
        maze.setTile(0,0, Tile.SD);
        maze.setTile(0, 1, Tile.WR);
        maze.setTile(0, 9, Tile.WL);
        maze.setTile(1, 0, Tile.WB);
        maze.setTile(14,0, Tile.WT);
        /** Testing directions inside the grid **/
        assertEquals(maze.getNeighbourTile(0,0, Direction.RIGHT), Tile.WR);
        assertEquals(maze.getNeighbourTile(new TilePosition(0,0), Direction.RIGHT), Tile.WR);
        assertEquals(maze.getNeighbourTile(0,0, Direction.DOWN), Tile.WB);
        assertEquals(maze.getNeighbourTile(new TilePosition(0,0), Direction.DOWN), Tile.WB);
        assertEquals(maze.getNeighbourTile(1,1, Direction.UP), Tile.WR);
        assertEquals(maze.getNeighbourTile(new TilePosition(1,1), Direction.UP), Tile.WR);
        assertEquals(maze.getNeighbourTile(1,1, Direction.LEFT), Tile.WB);
        assertEquals(maze.getNeighbourTile(new TilePosition(1,1), Direction.LEFT), Tile.WB);
        /** testing with circularity  when you get out of the grid **/
        assertEquals(maze.getNeighbourTile(0,0, Direction.LEFT), Tile.WL);
        assertEquals(maze.getNeighbourTile(0,0, Direction.UP), Tile.WT);
        assertEquals(maze.getNeighbourTile(0,9, Direction.RIGHT), Tile.SD);
        assertEquals(maze.getNeighbourTile(14,0, Direction.DOWN), Tile.SD);
    }

    @Test
    public void testGetTilePosition() {
        Maze maze = Maze.emptyMaze(10,15);
        assertEquals(maze.getTilePosition(0,0), new TilePosition(0,0));
        assertEquals(maze.getTilePosition(1,1), new TilePosition(0,0));
        assertEquals(maze.getTilePosition(3*Maze.TILE_WIDTH + 2, 2*Maze.TILE_HEIGHT), new TilePosition(2,3));
        assertEquals(maze.getTilePosition(10*Maze.TILE_WIDTH - 1, 15*Maze.TILE_HEIGHT - 1), new TilePosition(14,9));
    }

    @Test
    public void testLoadFromFile1() throws FileNotFoundException {
        Maze testMaze = Maze.loadFromFile("resources/test.txt");
        assertEquals(testMaze.getWidth(), 9);
        assertEquals(testMaze.getHeight(), 17);
        assertEquals(testMaze.getTile(0,0), Tile.EE);
        assertEquals(testMaze.getTile(2,3), Tile.WT);
        assertEquals(testMaze.getTile(2,4), Tile.WT);
        assertEquals(testMaze.getTile(2,5), Tile.WT);
        assertEquals(testMaze.getTile(3,3), Tile.SD);
        assertEquals(testMaze.getTile(3,4), Tile.SD);
        assertEquals(testMaze.getTile(3,6), Tile.WR);
        assertEquals(testMaze.getTile(4,2), Tile.C7);
        assertEquals(testMaze.getTile(4,4), Tile.BD);
        assertEquals(testMaze.getTile(4,6), Tile.C8);
        assertTrue(testMaze.getTile(3,4).hasDot());
        assertFalse(testMaze.getTile(3,4).hasBigDot());
        assertTrue(testMaze.getTile(4,4).hasDot());
        assertTrue(testMaze.getTile(4,4).hasBigDot());
    }

    @Test
    public void testLoadFromFile2() throws FileNotFoundException {
        Maze maze = Maze.loadFromFile("resources/maze.txt");
        assertEquals(maze.getWidth(), 28);
        assertEquals(maze.getHeight(), 36);
        assertEquals(maze.getTile(0,0), Tile.EE);
        assertEquals(maze.getTile(3,0), Tile.D1);
        for(int j =0; j < 28; j++) {
            assertTrue(maze.getTile(3,j).isWall());
        }
        assertTrue(maze.getTile(4,0).isWall());
        assertTrue(maze.getTile(4,27).isWall());
        assertTrue(maze.getTile(4,13).isWall());
        assertTrue(maze.getTile(4,14).isWall());
        for(int j =1; j < 13; j++) {
            assertFalse(maze.getTile(4,j).isWall());
            assertTrue(maze.getTile(4,j).hasDot());
        }
        for(int j =15; j < 27; j++) {
            assertFalse(maze.getTile(4,j).isWall());
            assertTrue(maze.getTile(4,j).hasDot());
        }
        assertEquals(maze.getTile(15,13), Tile.GD);
        assertEquals(maze.getTile(14,13), Tile.EE);
        assertFalse(maze.getTile(14,13).hasDot());
        assertFalse(maze.getTile(14,12).hasDot());
        assertFalse(maze.getTile(14,12).isWall());
        assertTrue(maze.getTile(26,12).hasDot());
        assertFalse(maze.getTile(26,12).isWall());
        assertEquals(maze.getTile(6,1), Tile.BD);
        assertTrue(maze.getTile(6,1).hasDot());
        assertTrue(maze.getTile(6,1).hasBigDot());
    }

    /** New step 2 tests **/

    @Test
    public void testSetTileWithoutDot() {
        Maze maze = Maze.emptyMaze(10,10);
        assertEquals(maze.getNumberOfDots(), 0);
        maze.setTile(0,0, Tile.WR);
        assertEquals(maze.getNumberOfDots(), 0);
    }

    @Test
    public void testSetTileWithDot() {
        Maze maze = Maze.emptyMaze(10,10);
        assertEquals(maze.getNumberOfDots(), 0);
        maze.setTile(0,0, Tile.SD);
        assertEquals(maze.getNumberOfDots(), 1);
        maze.setTile(0,0, Tile.SD);
        assertEquals(maze.getNumberOfDots(), 1);
        maze.setTile(0,1, Tile.BD);
        assertEquals(maze.getNumberOfDots(), 2);
        maze.setTile(0,1, Tile.SD);
        assertEquals(maze.getNumberOfDots(), 2);
        maze.setTile(0,2, Tile.ND);
        assertEquals(maze.getNumberOfDots(), 3);
        maze.setTile(0,2, Tile.BD);
        assertEquals(maze.getNumberOfDots(), 3);
    }

    @Test
    public void testSetTileWithDotTilePosition() {
        Maze maze = Maze.emptyMaze(10,10);
        assertEquals(maze.getNumberOfDots(), 0);
        maze.setTile(new TilePosition(0,0), Tile.SD);
        assertEquals(maze.getNumberOfDots(), 1);
        maze.setTile(new TilePosition(0,0), Tile.SD);
        assertEquals(maze.getNumberOfDots(), 1);
        maze.setTile(new TilePosition(0,1), Tile.BD);
        assertEquals(maze.getNumberOfDots(), 2);
        maze.setTile(new TilePosition(0,1), Tile.SD);
        assertEquals(maze.getNumberOfDots(), 2);
        maze.setTile(new TilePosition(0,2), Tile.ND);
        assertEquals(maze.getNumberOfDots(), 3);
        maze.setTile(new TilePosition(0,2), Tile.BD);
        assertEquals(maze.getNumberOfDots(), 3);
    }

    @Test
    public void testRemoveTileWithDot() {
        Maze maze = Maze.emptyMaze(10,10);
        maze.setTile(0,0, Tile.SD);
        maze.setTile(0,1, Tile.BD);
        maze.setTile(0,2, Tile.ND);
        assertEquals(maze.getNumberOfDots(), 3);
        maze.setTile(0,0, Tile.EE);
        assertEquals(maze.getNumberOfDots(), 2);
        maze.setTile(0,1, Tile.EE);
        assertEquals(maze.getNumberOfDots(), 1);
        maze.setTile(0,2, Tile.EE);
        assertEquals(maze.getNumberOfDots(), 0);
        maze.setTile(0,3, Tile.EE);
        assertEquals(maze.getNumberOfDots(), 0);
    }

    @Test
    public void testRemoveTileWithDotTilePosition() {
        Maze maze = Maze.emptyMaze(10,10);
        maze.setTile(0,0, Tile.SD);
        maze.setTile(0,1, Tile.BD);
        maze.setTile(0,2, Tile.ND);
        assertEquals(maze.getNumberOfDots(), 3);
        maze.setTile(new TilePosition(0,0), Tile.EE);
        assertEquals(maze.getNumberOfDots(), 2);
        maze.setTile(new TilePosition(0,1), Tile.EE);
        assertEquals(maze.getNumberOfDots(), 1);
        maze.setTile(new TilePosition(0,2), Tile.EE);
        assertEquals(maze.getNumberOfDots(), 0);
        maze.setTile(new TilePosition(0,3), Tile.EE);
        assertEquals(maze.getNumberOfDots(), 0);
    }

    @Test
    public void testLoadFromFileTestNbDots() throws FileNotFoundException {
        Maze testMaze = Maze.loadFromFile("resources/test.txt");
        assertEquals(testMaze.getNumberOfDots(), 18);
    }

    @Test
    public void testLoadFromFileClassicNbDots() throws FileNotFoundException {
        Maze testMaze = Maze.loadFromFile("resources/maze.txt");
        assertEquals(testMaze.getNumberOfDots(), 244);
    }
}
