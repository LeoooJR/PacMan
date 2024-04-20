package fr.upsaclay.bibs.pacman.view;

import fr.upsaclay.bibs.pacman.model.Direction;
import fr.upsaclay.bibs.pacman.model.actors.Actor;
import fr.upsaclay.bibs.pacman.model.actors.Ghost;
import fr.upsaclay.bibs.pacman.model.actors.GhostType;
import fr.upsaclay.bibs.pacman.model.maze.Maze;
import fr.upsaclay.bibs.pacman.model.maze.PacManMaze;
import fr.upsaclay.bibs.pacman.model.maze.Tile;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePanel extends JPanel {
    private static final int DOT_SIZE = 3;
    private static final Color WALL_COLOR_GD = Color.PINK;
    private static final Color WALL_COLOR_OTHER = Color.BLUE;
    private static final Color NON_WALL_COLOR = Color.BLACK;
    private static final int PIXELS_PER_CELL = 24;
    private static final String TILE_PATH = "resources/tiles/";
    private Maze maze;
    private Actor pacman;
    private List<Ghost> ghostList;
    private Map<Tile, int[][]> tilePatternsCache = new HashMap<>();
    private Map<String, Image> imageCache = new HashMap<>();
    private Image pacmanImageUp = loadImage("resources/img/up.gif");
    private Image pacmanImageDown = loadImage("resources/img/down.gif");
    private Image pacmanImageLeft = loadImage("resources/img/left.gif");
    private Image pacmanImageRight = loadImage("resources/img/right.gif");
    private Image blinkyImage = loadImage("resources/img/blinky.png");
    private Image blinky_left = loadImage("resources/img/blinky_left.png");
    private Image blinky_right = loadImage("resources/img/blinky_right.png");
    private Image blinky_up = loadImage("resources/img/blinky_up.png");
    private Image blinky_down = loadImage("resources/img/blinky_down.png");
    private Image pinkyImage = loadImage("resources/img/pinky.png");
    private Image inkyImage = loadImage("resources/img/inky.png");
    private Image clydeImage = loadImage("resources/img/clyde.png");

   public GamePanel() {
    super();
    for (GhostType type : GhostType.values()) {
        Map<Direction, Image> imagesByDirection = new HashMap<>();
        for (Direction direction : Direction.values()) {
            String imagePath = STR."resources/img/\{type.name().toLowerCase()}_\{direction.name().toLowerCase()}.png";
            imagesByDirection.put(direction, loadImage(imagePath));
        }
        ghostImages.put(type, imagesByDirection);
    }
}
    private Map<GhostType, Map<Direction, Image>> ghostImages = new HashMap<>();
    public void setPacman(Actor agent) {
        this.pacman = agent;
    }

    public void setGhostList(List<Ghost> ghosts) {
        this.ghostList = ghosts;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    private Image loadImage(String imagePath) {
        Image image = imageCache.get(imagePath);
        if (image == null) {
            image = new ImageIcon(imagePath).getImage();
            imageCache.put(imagePath, image);
        }
        return image;
    }


    public void paintWall(Graphics graphics, Tile tile, int i, int j, int offsetX, int offsetY) {
        int[][] tilePattern = getTilePattern(tile);
        for (int row = 0; row < tilePattern.length; row++) {
            for (int col = 0; col < tilePattern[row].length; col++) {
                if (tilePattern[row][col] != 0) {
                    graphics.setColor(getTileColor(tile));
                    graphics.fillRect(offsetX + (j * PIXELS_PER_CELL) + (col * DOT_SIZE),
                            offsetY + (i * PIXELS_PER_CELL) + (row * DOT_SIZE),
                            DOT_SIZE, DOT_SIZE);
                }
            }
        }
    }

    private int[][] getTilePattern(Tile tile) {
        return tilePatternsCache.computeIfAbsent(tile, this::loadTilePattern);
    }

    private int[][] loadTilePattern(Tile tile) {
        String fileName = STR."\{TILE_PATH}\{tile.name()}.txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            int[][] pattern = new int[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(" ");
                pattern[i] = new int[parts.length];
                for (int j = 0; j < parts.length; j++) {
                    pattern[i][j] = Integer.parseInt(parts[j]);
                }
            }
            return pattern;
        } catch (IOException e) {
            System.err.println(STR."Failed to load tile pattern for \{tile.name()}: \{e.getMessage()}");
            return new int[0][0]; // Return an empty pattern in case of an error
        }
    }

    private Color getTileColor(Tile tile) {
        if (tile.isWall()) {
            return tile == Tile.GD ? WALL_COLOR_GD : WALL_COLOR_OTHER;
        } else {
            switch (tile) {
                case BD:
                    return Color.YELLOW; // color for bonus dot
                default:
                    return Color.WHITE;
            }
        }
    }

    public void paintGhost(Graphics graphics, int offset_x, int offset_y) {
        for (Ghost ghost : ghostList) {

            Image ghostImage = ghostImages.get(ghost.getGhostType()).get(ghost.getDirection());
            graphics.drawImage(ghostImage,
                    (offset_x + (ghost.getCurrentTile().getCol() * Maze.TILE_WIDTH) * 3),
                    (offset_y + ((ghost.getCurrentTile().getLine() * Maze.TILE_HEIGHT) * 3)),
                    PacManGameView.PIXELS_PER_CELLS,
                    PacManGameView.PIXELS_PER_CELLS,
                    null);
        }
    }

    public void paintPacMan(Graphics graphics, int offset_x, int offset_y) {
        Image pacmanImage;
        switch (pacman.getDirection()) {
            case UP:
                pacmanImage = pacmanImageUp;
                break;
            case DOWN:
                pacmanImage = pacmanImageDown;
                break;
            case LEFT:
                pacmanImage = pacmanImageLeft;
                break;
            case RIGHT:
                pacmanImage = pacmanImageRight;
                break;
            default:
                throw new IllegalStateException("Unexpected direction: " + pacman.getDirection());
        }
        graphics.drawImage(pacmanImage,
                (offset_x + (pacman.getCurrentTile().getCol() * Maze.TILE_WIDTH) * 3),
                (offset_y + ((pacman.getCurrentTile().getLine() * Maze.TILE_HEIGHT) * 3)),
                PacManGameView.PIXELS_PER_CELLS,
                PacManGameView.PIXELS_PER_CELLS,
                null);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        int offset_x = (PacManGameView.WIDTH - (PacManGameView.PIXELS_PER_CELLS * maze.getWidth())) / 2;
        int offset_y = (PacManGameView.HEIGHT - (PacManGameView.PIXELS_PER_CELLS * maze.getHeight())) / 2;
        if (maze != null) {
            for (int row = 0; row < maze.getHeight(); row++) {
                for (int col = 0; col < maze.getWidth(); col++) {
                    paintWall(graphics, maze.getTile(row, col), row, col, offset_x, offset_y);
                }
            }
            paintPacMan(graphics, offset_x, offset_y);
            paintGhost(graphics, offset_x, offset_y);
        }
    }
}
