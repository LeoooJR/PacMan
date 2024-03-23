package fr.upsaclay.bibs.pacman.view;

import fr.upsaclay.bibs.pacman.control.Controller;
import fr.upsaclay.bibs.pacman.control.GameAction;
import fr.upsaclay.bibs.pacman.model.board.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class PacManGameView extends JFrame implements PacManView {

    private Controller controller;

    private PacManLayout layout;

    private Board board;

    JPanel initPanel;
    StartKeyListener startListener;

    GamePanel gamePanel;

    PlayKeyListener playListener;

    JPanel pausePanel;

    Timer timer;

    public PacManGameView(String name, int width, int height){
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setFocusable(true);
        setTitle("PacMan");
        setSize(new Dimension(width,height));

        initPanel = new JPanel();
        initPanel.setLayout(new BorderLayout());

        timer = new Timer(17,null);
        layout = PacManLayout.INIT;

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
        add(initPanel,BorderLayout.CENTER);

        //Header
        JLabel header = new JLabel();
        header.setText("TOUCH TO START");
        header.setFont(new Font("Futura",Font.BOLD,33));
        header.setForeground(Color.YELLOW);
        initPanel.add(header, BorderLayout.CENTER);

        //Image Label GIF
        ImageIcon imgGif = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("giphy.gif")));
        JLabel imgLabel = new JLabel();
        imgLabel.setBounds(1000,600,720,280);
        imgLabel.setIcon(imgGif);
        initPanel.add(imgLabel,BorderLayout.SOUTH);

        //Header Image PacMan
        BufferedImage imgPacMan;
        try {
            imgPacMan = ImageIO.read(new File("/Users/leojourdain/Documents/Java/Projet/Projet_Pacman/src/fr/upsaclay/bibs/pacman/view/Pac-Man_Logo.svg.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image imgPacManResized = imgPacMan.getScaledInstance(720,300,Image.SCALE_DEFAULT);
        //ImageIcon imgPacMan = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("Pac-Man_Logo.svg.png")));
        ImageIcon iconPacMan = new ImageIcon(imgPacManResized);
        JLabel imgPacManLabel = new JLabel();
        imgPacManLabel.setIcon(iconPacMan);
        initPanel.add(imgPacManLabel,BorderLayout.NORTH);

        //Listener to launch the game
        startListener = new StartKeyListener(controller,this);

        //Game Panel
        gamePanel = new GamePanel();

        //Timer
        timer.addActionListener(new ButtonListener(controller, GameAction.NEXT_FRAME));

        //Listener to play game
        playListener = new PlayKeyListener(controller);

        //Pause Panel
        pausePanel= new JPanel();
        JLabel textLabel = new JLabel();
        textLabel.setText("Pause");
        pausePanel.add(textLabel);


        setLocationRelativeTo(null);
        setVisible(true);


    }

    @Override
    public void setLayout(PacManLayout layout) {
        switch (layout){
            case PacManLayout.INIT:
                this.layout = PacManLayout.INIT;
                initPanel.setVisible(true);
                gamePanel.setVisible(false);
                pausePanel.setVisible(false);
                addKeyListener(startListener);
                removeKeyListener(playListener);
                requestFocus();
                break;
            case GAME_ON:
                this.layout = PacManLayout.GAME_ON;
                initPanel.setVisible(false);
                gamePanel.setVisible(true);
                pausePanel.setVisible(false);
                addKeyListener(playListener);
                removeKeyListener(startListener);
                requestFocus();
                timer.start();
                break;
            case PAUSE:
                this.layout = PacManLayout.PAUSE;
                initPanel.setVisible(false);
                gamePanel.setVisible(false);
                pausePanel.setVisible(true);
                removeKeyListener(playListener);
                addKeyListener(startListener);
                requestFocus();
                timer.stop();
                break;
        }
    }

    public PacManLayout getViewLayout(){
        return this.layout;
    }

    @Override
    public void update() {
        repaint();
        requestFocus();
    }

    public static void main(String[] args){
        PacManView view = new PacManGameView("PacMan",720,1000);
        view.initialize();
    }
}
