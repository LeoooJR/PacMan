package fr.upsaclay.bibs.pacman.view;

import fr.upsaclay.bibs.pacman.GameType;
import fr.upsaclay.bibs.pacman.control.Controller;
import fr.upsaclay.bibs.pacman.control.GameAction;
import fr.upsaclay.bibs.pacman.model.actors.Actor;
import fr.upsaclay.bibs.pacman.model.actors.Ghost;
import fr.upsaclay.bibs.pacman.model.board.Board;
import fr.upsaclay.bibs.pacman.model.board.BoardState;
import fr.upsaclay.bibs.pacman.model.maze.Maze;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class PacManGameView extends JFrame implements PacManView {

    public static final int PIXELS_PER_CELLS = 24;

    public static final int WIDTH = 720;

    public static final int HEIGHT = 1000;
    JPanel initPanel;
    StartKeyListener startListener;
    GamePanel gamePanel;
    JPanel gameHeaderPanel;
    PlayKeyListener playListener;
    PausePanel pausePanel;
    JPanel levelOverPanel;
    JPanel lifeOverPanel;
    Timer timer;
    JLabel score;
    private Controller controller;
    private PacManLayout layout;
    private Board board;
    private CardLayout cardLayout;

    private JPanel cards;

    public PacManGameView(String name, int width, int height) {
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setFocusable(true);
        setTitle("PacMan");
        setSize(new Dimension(width, height));


        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        add(cards);

        initPanel = new JPanel();
        initPanel.setLayout(new BorderLayout());

        timer = new Timer(17, null);
        layout = PacManLayout.INIT;
        cards.add(initPanel, "INIT");
    }

    public static void main(String[] args) {
        PacManView view = new PacManGameView("PacMan", 720, 1000);
        view.initialize();
    }

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void initialize() {
        //Initiation Panel
        initPanel.setBackground(Color.BLACK);

        //Header
        JLabel header = new JLabel();
        header.setText("TOUCH TO START");
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setFont(new Font("Futura", Font.BOLD, 50));
        header.setForeground(Color.ORANGE);
        initPanel.add(header, BorderLayout.CENTER);


        //Image Label GIF
        ImageIcon imgGif = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("giphy720.gif")));
        JLabel imgLabel = new JLabel();
        imgLabel.setBounds(0, 600, 1000, 280);
        imgLabel.setIcon(imgGif);
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        initPanel.add(imgLabel, BorderLayout.SOUTH);

        //Header Image PacMan
        BufferedImage imgPacMan;
        try {
            imgPacMan = ImageIO.read(new File("src/fr/upsaclay/bibs/pacman/view/Pac-Man_Logo.svg.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image imgPacManResized = imgPacMan.getScaledInstance(720, 300, Image.SCALE_DEFAULT);
        //ImageIcon imgPacMan = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("Pac-Man_Logo.svg.png")));
        ImageIcon iconPacMan = new ImageIcon(imgPacManResized);
        JLabel imgPacManLabel = new JLabel();
        imgPacManLabel.setIcon(iconPacMan);
        initPanel.add(imgPacManLabel, BorderLayout.NORTH);

        //Listener to launch the game
        startListener = new StartKeyListener(controller, this);

        //Game Panel
        gamePanel = new GamePanel();
        gamePanel.setBackground(Color.BLACK);

        cards.add(gamePanel, "GAME");

        //Header Game Panel
        gameHeaderPanel = new JPanel();
        gameHeaderPanel.setLayout(new BoxLayout(gameHeaderPanel, BoxLayout.Y_AXIS));
        gameHeaderPanel.setBackground(Color.BLACK);
        gameHeaderPanel.setPreferredSize(new Dimension(720, 95));

        JLabel gameHeader = new JLabel();
        gameHeader.setText("HIGH SCORE");
        gameHeader.setFont(new Font("Futura", Font.BOLD, 33));
        gameHeader.setForeground(Color.WHITE);
        gameHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameHeaderPanel.add(gameHeader);

        gamePanel.add(gameHeaderPanel, BorderLayout.NORTH);

        score = new JLabel();
        score.setText("0");
        score.setFont(new Font("Futura", Font.BOLD, 33));
        score.setForeground(Color.WHITE);
        score.setBackground(Color.BLACK);
        score.setAlignmentX(Component.CENTER_ALIGNMENT);

//        JLabel st = new JLabel("State : ");
//        st.setFont(new Font("Futura", Font.BOLD, 33));
//        st.setForeground(Color.WHITE);
//        st.setBackground(Color.BLACK);
//        st.setAlignmentX(Component.RIGHT_ALIGNMENT);
//        gameHeaderPanel.add(st);

        //Timer
        timer.addActionListener(new ButtonListener(controller, GameAction.NEXT_FRAME));

        //Listener to play game
        playListener = new PlayKeyListener(controller);

        //Pause Panel
        pausePanel = new PausePanel();
        cards.add(pausePanel, "PAUSE");


        //Level Over Panel
        levelOverPanel = new JPanel();
        levelOverPanel.setBackground(Color.BLACK);

        JLabel winLevelMessage = new JLabel();
        winLevelMessage.setText("You win this level !");
        winLevelMessage.setFont(new Font("Futura", Font.BOLD, 33));
        levelOverPanel.add(winLevelMessage, BorderLayout.CENTER);

        cards.add(levelOverPanel, "LEVEL_OVER");

        //Life Over Panel
        lifeOverPanel = new JPanel();
        lifeOverPanel.setBackground(Color.BLACK);

        JLabel looseLevelMessage = new JLabel();
        looseLevelMessage.setText("You loose !");
        looseLevelMessage.setFont(new Font("Futura", Font.BOLD, 33));
        lifeOverPanel.add(looseLevelMessage);


        BufferedImage imgGameOver;
        try {
            imgGameOver = ImageIO.read(new File("resources/img/gameover.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image imgGameOverResized = imgGameOver.getScaledInstance(720, 300, Image.SCALE_DEFAULT);
        //ImageIcon imgPacMan = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("Pac-Man_Logo.svg.png")));
        ImageIcon iconGameOver = new ImageIcon(imgGameOverResized);
        JLabel imgGameOverLabel = new JLabel();
        imgGameOverLabel.setIcon(iconGameOver);
        lifeOverPanel.add(imgGameOverLabel);
        setLocationRelativeTo(null);
        setVisible(true);
        cards.add(lifeOverPanel, "LIFE_OVER");
    }

    @Override
    public void setLayout(PacManLayout layout) {
        switch (layout) {
            case INIT:
                System.out.println("INIT");
                removeKeyListener(playListener);
                addKeyListener(startListener);
                requestFocus();
                cardLayout.show(cards, "INIT");
                break;
            case GAME_ON:

                this.layout = PacManLayout.GAME_ON;
                cardLayout.show(cards, "GAME");
                gameHeaderPanel.add(score);
                requestFocus();
                timer.start();
                removeKeyListener(startListener);
                addKeyListener(playListener);
                break;
            case PAUSE:
                this.layout = PacManLayout.PAUSE;
                cardLayout.show(cards, "PAUSE");
                timer.stop();
                requestFocus();
                removeKeyListener(playListener);
                addKeyListener(startListener);
                break;
            case LEVEL_OVER:
                cardLayout.show(cards, "LEVEL_OVER");
                break;
            case LIFE_OVER:
                cardLayout.show(cards, "LIFE_OVER");
                timer.stop();
                removeKeyListener(playListener);
                removeKeyListener(startListener);
                break;
        }

    }

    public PacManLayout getViewLayout() {
        return this.layout;
    }

    public void setMaze(Maze maze) {
        maze = board.getMaze();
        gamePanel.setMaze(maze);
    }

    public void setPacMan(Actor agent) {
        gamePanel.setPacman(agent);
    }

    public void setGhosts(java.util.List<Ghost> ghosts) {
        gamePanel.setGhostList(ghosts);
    }

    @Override
    public void update() {
        repaint();
        score.setText(String.valueOf(board.getScore()));
        BoardState state = board.getBoardState();
        GameType type = board.getGameType();

        requestFocus();
    }
}
